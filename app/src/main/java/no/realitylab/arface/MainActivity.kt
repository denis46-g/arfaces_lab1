package no.realitylab.arface

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

var clown_nose = false
//var hats = false
var boroda_and_hair = false

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        button_makeup.setOnClickListener {
            startActivity(Intent(this, MakeupActivity::class.java))
        }

        button_glasses.setOnClickListener {
            startActivity(Intent(this, GlassesActivity::class.java))
        }

        button_regions.setOnClickListener {
            startActivity(Intent(this, FaceRegionsActivity::class.java))
        }

        button_face_landmarks.setOnClickListener {
            clown_nose = false
            //hats = false
            boroda_and_hair = false
            startActivity(Intent(this, FaceLandmarksActivity::class.java))
        }

        button_clown_nose.setOnClickListener{
            clown_nose = true
            //hats = false
            boroda_and_hair = false
            startActivity(Intent(this, ClownNoseActivity::class.java))
        }

        /*button_hats.setOnClickListener{
            //hats = true
            //clown_nose = false
            startActivity(Intent(this, HatsActivity::class.java))
        }*/

        button_boroda_and_hair.setOnClickListener{
            clown_nose = false
            //hats = false
            boroda_and_hair = true
            startActivity(Intent(this, BorodaAndHairActivity::class.java))
        }

    }
}
