package com.markvtls.diploma

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import cn.easyar.Engine
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.ar.core.ArCoreApk
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.markvtls.diploma.databinding.ActivityMainBinding
import com.markvtls.diploma.presentation.fragments.TicketsViewModel
import com.markvtls.diploma.presentation.fragments.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract(),
    ) { res ->
        this.onSignInResult(res)
    }

    private val userViewModel: UserViewModel by viewModels()
    private val ticketsViewModel: TicketsViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)



        setContentView(binding.root)

        val availability = ArCoreApk.getInstance().checkAvailability(this)
        println("AR is supported: ${availability.isSupported}")
        if (!availability.isSupported) {
            when (ArCoreApk.getInstance().requestInstall(this, false)) {
                ArCoreApk.InstallStatus.INSTALLED -> println("Do some work")
                ArCoreApk.InstallStatus.INSTALL_REQUESTED ->  println("Just wait for user actions")
            }
        }
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) -> {
                println("Do some job")
            }
            else -> {
                requestPermissions(
                    arrayOf(android.Manifest.permission.CAMERA),
                    1888
                )
            }
        }



        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
                ?: return

        lifecycleScope.launch {
            userViewModel.userEmail.collect { email ->
                userViewModel.userPhone.collect { phone ->
                    println(phone)
                    println(email)
                }

            }
        }

        val navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.bottom_nav)
            .setupWithNavController(navController)


        val user = Firebase.auth.currentUser
        if (user != null) {
            if (user?.email != null) ticketsViewModel.loadUserTickets(user.email) else if (user?.phoneNumber != null) ticketsViewModel.loadUserTickets(user.phoneNumber)
        } else {
            signInLauncher.launch(signInIntent)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode== 1888) {
            if (grantResults.first() == PackageManager.PERMISSION_GRANTED) {
                println("Do some work with camera")
            }
        }
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            val user = FirebaseAuth.getInstance().currentUser
            userViewModel.saveUserPhone(user?.phoneNumber)
            userViewModel.saveUserEmail(user?.email)

            if (user?.email != null) ticketsViewModel.loadUserTickets(user.email) else if (user?.phoneNumber != null) ticketsViewModel.loadUserTickets(user.phoneNumber)
        } else {
            signInLauncher.launch(signInIntent)

            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }


    fun emailLink() {
        // [START auth_fui_email_link]
        val actionCodeSettings = ActionCodeSettings.newBuilder()
            .setAndroidPackageName( // yourPackageName=
                "...", // installIfNotAvailable=
                true, // minimumVersion=
                null,
            )
            .setHandleCodeInApp(true) // This must be set to true
            .setUrl("https://google.com") // This URL needs to be whitelisted
            .build()

        val providers = listOf(
            AuthUI.IdpConfig.EmailBuilder()
                .enableEmailLinkSignIn()
                .setActionCodeSettings(actionCodeSettings)
                .build(),
        )
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()
        signInLauncher.launch(signInIntent)
        // [END auth_fui_email_link]
    }


    fun catchEmailLink() {
        val providers: List<AuthUI.IdpConfig> = emptyList()

        // [START auth_fui_email_link_catch]
        if (AuthUI.canHandleIntent(intent)) {
            val extras = intent.extras ?: return
            val link = extras.getString("email_link_sign_in")
            if (link != null) {
                val signInIntent = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setEmailLink(link)
                    .setAvailableProviders(providers)
                    .build()
                signInLauncher.launch(signInIntent)
            }
        }
        // [END auth_fui_email_link_catch]
    }

    companion object {

        private val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build()
        )

        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()

    }

}


