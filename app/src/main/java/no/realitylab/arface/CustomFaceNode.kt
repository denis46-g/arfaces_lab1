package no.realitylab.arface

import android.content.Context
import android.widget.ImageView
import com.google.ar.core.AugmentedFace
import com.google.ar.sceneform.FrameTime
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ViewRenderable
import com.google.ar.sceneform.ux.AugmentedFaceNode
import kotlinx.android.synthetic.main.activity_hats.button_next_hat

class CustomFaceNode(augmentedFace: AugmentedFace?,
                 val context: Context
): AugmentedFaceNode(augmentedFace) {

    private var eyeNodeLeft: Node? = null
    private var eyeNodeRight: Node? = null
    private var mustacheNode: Node? = null
    private var clownNoseNode: Node? = null
    //private var hatNode: Node? = null
    private var borodaNode: Node? = null
    private var hairNode: Node? = null

    companion object {
        enum class FaceRegion {
            LEFT_EYE,
            RIGHT_EYE,
            MUSTACHE,
            CLOWN_NOSE,
            //HAT
            BORODA,
            HAIR
        }
    }

    override fun onActivate() {
        super.onActivate()

        if(!clown_nose && !boroda_and_hair){
            eyeNodeLeft = Node()
            eyeNodeLeft?.setParent(this)

            eyeNodeRight = Node()
            eyeNodeRight?.setParent(this)

            mustacheNode = Node()
            mustacheNode?.setParent(this)

            ViewRenderable.builder()
                .setView(context, R.layout.element_layout)
                .build()
                .thenAccept { uiRenderable: ViewRenderable ->
                    uiRenderable.isShadowCaster = false
                    uiRenderable.isShadowReceiver = false
                    eyeNodeLeft?.renderable = uiRenderable
                    eyeNodeRight?.renderable = uiRenderable
                }
                .exceptionally { throwable: Throwable? ->
                    throw AssertionError(
                        "Could not create ui element",
                        throwable
                    )
                }

            ViewRenderable.builder()
                .setView(context, R.layout.element_layout)
                .build()
                .thenAccept { uiRenderable: ViewRenderable ->
                    uiRenderable.isShadowCaster = false
                    uiRenderable.isShadowReceiver = false
                    mustacheNode?.renderable = uiRenderable
                    uiRenderable.view.findViewById<ImageView>(R.id.element_image).setImageResource(R.drawable.mustache)
                }
                .exceptionally { throwable: Throwable? ->
                    throw AssertionError(
                        "Could not create ui element",
                        throwable
                    )
                }
        }
        else if (!boroda_and_hair){
            clownNoseNode = Node()
            clownNoseNode?.setParent(this)

            ViewRenderable.builder()
                .setView(context, R.layout.element_layout)
                .build()
                .thenAccept { uiRenderable: ViewRenderable ->
                    uiRenderable.isShadowCaster = false
                    uiRenderable.isShadowReceiver = false
                    clownNoseNode?.renderable = uiRenderable
                    uiRenderable.view.findViewById<ImageView>(R.id.element_image).setImageResource(R.drawable.clown_nose)
                }
                .exceptionally { throwable: Throwable? ->
                    throw AssertionError(
                        "Could not create ui element",
                        throwable
                    )
                }
        }
        else if(!clown_nose){
            borodaNode = Node()
            borodaNode?.setParent(this)

            hairNode = Node()
            hairNode?.setParent(this)

            ViewRenderable.builder()
                .setView(context, R.layout.element_layout)
                .build()
                .thenAccept { uiRenderable: ViewRenderable ->
                    uiRenderable.isShadowCaster = false
                    uiRenderable.isShadowReceiver = false
                    borodaNode?.renderable = uiRenderable
                    uiRenderable.view.findViewById<ImageView>(R.id.element_image).setImageResource(R.drawable.boroda)
                }
                .exceptionally { throwable: Throwable? ->
                    throw AssertionError(
                        "Could not create ui element",
                        throwable
                    )
                }
            ViewRenderable.builder()
                .setView(context, R.layout.element_layout)
                .build()
                .thenAccept { uiRenderable: ViewRenderable ->
                    uiRenderable.isShadowCaster = false
                    uiRenderable.isShadowReceiver = false
                    hairNode?.renderable = uiRenderable
                    uiRenderable.view.findViewById<ImageView>(R.id.element_image).setImageResource(R.drawable.hair)
                }
                .exceptionally { throwable: Throwable? ->
                    throw AssertionError(
                        "Could not create ui element",
                        throwable
                    )
                }

        }

    }

    private fun getRegionPose(region: FaceRegion) : Vector3? {
        val buffer = augmentedFace?.meshVertices
        if (buffer != null) {
            return when (region) {
                FaceRegion.LEFT_EYE ->
                    Vector3(buffer.get(374 * 3),buffer.get(374 * 3 + 1),  buffer.get(374 * 3 + 2))
                FaceRegion.RIGHT_EYE ->
                    Vector3(buffer.get(145 * 3),buffer.get(145 * 3 + 1),  buffer.get(145 * 3 + 2))
                FaceRegion.MUSTACHE ->
                    Vector3(buffer.get(11 * 3),
                        buffer.get(11 * 3 + 1),
                        buffer.get(11 * 3 + 2))
                FaceRegion.CLOWN_NOSE ->
                    Vector3(buffer.get(15 * 2),
                        buffer.get(15 * 2 + 1),
                        buffer.get(15 * 2 + 2))
                /*FaceRegion.HAT ->
                    Vector3(buffer.get(15 * 2),
                        buffer.get(15 * 2 + 1),
                        buffer.get(15 * 2 + 2))*/
                FaceRegion.BORODA ->
                    Vector3(buffer.get(15 * 2),
                        buffer.get(15 * 2 + 1),
                        buffer.get(15 * 2 + 2))
                FaceRegion.HAIR ->
                    Vector3(buffer.get(15 * 2),
                        buffer.get(15 * 2 + 1),
                        buffer.get(15 * 2 + 2))
            }
        }
        return null
    }

    override fun onUpdate(frameTime: FrameTime?) {
        super.onUpdate(frameTime)
        augmentedFace?.let {face ->
            getRegionPose(FaceRegion.LEFT_EYE)?.let {
                eyeNodeLeft?.localPosition = Vector3(it.x, it.y - 0.035f, it.z + 0.015f)
                eyeNodeLeft?.localScale = Vector3(0.055f, 0.055f, 0.055f)
                eyeNodeLeft?.localRotation = Quaternion.axisAngle(Vector3(0.0f, 0.0f, 1.0f), -10f)
            }

            getRegionPose(FaceRegion.RIGHT_EYE)?.let {
                eyeNodeRight?.localPosition = Vector3(it.x, it.y - 0.035f, it.z + 0.015f)
                eyeNodeRight?.localScale = Vector3(0.055f, 0.055f, 0.055f)
                eyeNodeRight?.localRotation = Quaternion.axisAngle(Vector3(0.0f, 0.0f, 1.0f), 10f)
            }

            getRegionPose(FaceRegion.MUSTACHE)?.let {
                mustacheNode?.localPosition = Vector3(it.x, it.y - 0.035f, it.z + 0.015f)
                mustacheNode?.localScale = Vector3(0.07f, 0.07f, 0.07f)
            }

            getRegionPose(FaceRegion.CLOWN_NOSE)?.let {
                clownNoseNode?.localPosition = Vector3(it.x, it.y - 0.14f, it.z + 0.045f)
                clownNoseNode?.localScale = Vector3(0.04f, 0.04f, 0.04f)
            }

            /*getRegionPose(FaceRegion.HAT)?.let {
                hatNode?.localPosition = Vector3(it.x, it.y - 0.07f, it.z + 0.045f)
                hatNode?.localScale = Vector3(0.09f, 0.09f, 0.09f)
            }*/

            getRegionPose(FaceRegion.BORODA)?.let {
                borodaNode?.localPosition = Vector3(it.x, it.y - 0.28f, it.z + 0.045f)
                borodaNode?.localScale = Vector3(0.15f, 0.15f, 0.15f)
            }

            getRegionPose(FaceRegion.HAIR)?.let {
                hairNode?.localPosition = Vector3(it.x, it.y - 0.08f, it.z + 0.045f)
                hairNode?.localScale = Vector3(0.19f, 0.19f, 0.19f)
            }
        }
    }
}