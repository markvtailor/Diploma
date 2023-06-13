package com.markvtls.diploma_aug.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.ar.core.Anchor
import com.markvtls.diploma_aug.databinding.FragmentRealityBinding
import com.markvtls.diploma_aug.presentation.Images
import io.github.sceneview.ar.ArSceneView
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.PlacementMode
import io.github.sceneview.math.Position
import io.github.sceneview.math.Rotation


class RealityFragment : Fragment() {

    private var _binding: FragmentRealityBinding? = null
    private val binding get() = _binding!!
    lateinit var modelNode: ArModelNode

    private val imagesViewModel: ImagesViewModel by viewModels({requireActivity()})


    lateinit var sceneView: ArSceneView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRealityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sceneView = binding.sceneView

        val location =
            when(imagesViewModel.lastImageUri) {
                Images.FIRST.uri -> Images.FIRST.model
                Images.SECOND.uri -> Images.SECOND.model
                Images.THIRD.uri -> Images.THIRD.model
                Images.FOURTH.uri -> Images.FOURTH.model
                Images.FIFTH.uri -> Images.FIFTH.model
                else -> Images.FIRST.model
            }

        val scaleUnits =
            when(imagesViewModel.lastImageSize) {
                "Small" -> 0.25F
                "Average" -> 0.50F
                "Large" -> 0.75F
                else -> 0.25F
            }

        modelNode = ArModelNode(
            modelGlbFileLocation = location,
            scaleToUnits = scaleUnits
        ).apply {
            placementMode = PlacementMode.PLANE_VERTICAL
            rotation = Rotation(100.0f, 0.0f, 90.0f)


        }
        modelNode.followHitPosition = true
        sceneView.apply {
            planeRenderer.isVisible = true
            onArSessionFailed = {
                modelNode.centerModel(origin = Position(x = 0.0f, y = 0.0f, z = 0.0f))
                modelNode.scaleModel(units = 3.0f)
                sceneView.addChild(modelNode)
            }
            onTapAr = { hitResult, _ ->
                anchorOrMove(hitResult.createAnchor())
            }
        }


        sceneView.addChild(modelNode)
    }


    private fun anchorOrMove(anchor: Anchor) {
        if (!sceneView.children.contains(modelNode)) {
            sceneView.addChild(modelNode)
        }
        modelNode.anchor = anchor
    }

}