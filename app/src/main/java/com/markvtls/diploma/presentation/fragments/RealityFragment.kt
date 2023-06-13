import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.ar.core.Config
import com.google.ar.core.Session
import com.markvtls.diploma.databinding.FragmentRealityBinding

class RealityFragment : Fragment() {

    private var _binding: FragmentRealityBinding? = null
    private val binding get() = _binding!!
    lateinit var session: Session

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val config = Config(session)
        session = Session(requireActivity())
        session.configure(config)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRealityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val surface = binding.surface
        surface.setRenderer(MainRenderer())
        surface.requestRender()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        session.close()
    }
}

