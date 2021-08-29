package edu.nmhu.bssd5250.sb_bssd5250_hwk22

import android.content.res.Resources
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import android.view.ViewGroup




class MainActivity : AppCompatActivity() {
    private lateinit var linearLayout: LinearLayoutCompat
    private var score = 0
    private var tally = 0
    //private lateinit var relativeLayout: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create a ConstraintLayout in which to add the ImageView
        val redlinearLayout = LinearLayoutCompat(this).apply {
            setBackgroundColor(Color.GRAY)
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                0,
            0.2f
            )
            // Add the ImageView to the layout.
            addView(makeButton("red"))  //add the red image
        }

        // Create a ConstraintLayout in which to add the ImageView
        val bluelinearLayout = LinearLayoutCompat(this).apply {
            setBackgroundColor(Color.DKGRAY)
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                0,
                0.2f
            )
            // Add the ImageView to the layout.
            addView(makeButton("blue"))  //add the blue image
        }

        //Not textViewCompat, eventhough it exists
        // TextViewCompat is a helper class for TextView unlike LinearLayoutCompat
        //var scoreNum = tally
        val score = TextView(this).apply {
            text = tally.toString()
            val metrics: DisplayMetrics = Resources.getSystem().displayMetrics
            val pixels = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48f, metrics)
            textSize = pixels
            textAlignment = View.TEXT_ALIGNMENT_CENTER
            setTextColor(Color.WHITE)
            setBackgroundColor(Color.BLUE)
            layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT)
            (layoutParams as RelativeLayout.LayoutParams).addRule(RelativeLayout.CENTER_IN_PARENT)
        }

        // Create a ConstraintLayout in which to add the ImageView
        val relativeLayout = RelativeLayout(this).apply {
            setBackgroundColor(Color.BLUE)
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                0,  //no height because vertical layout will
                0.6f)   //change the height for you based on hte weight
            addView(score)
        }


        // Create a ConstraintLayout in which to add the ImageView
        linearLayout = LinearLayoutCompat(this).apply {
            setBackgroundColor(Color.BLUE)
            orientation = LinearLayoutCompat.VERTICAL
            layoutParams = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.MATCH_PARENT
            )
            weightSum = 1.0f
            // Add the ImageView to the layout.
            addView(redlinearLayout)
            addView(bluelinearLayout)
            addView(relativeLayout)
        }

        // Set the layout as the content view.
        setContentView(linearLayout)
    }

    private fun makeButton(color: String): ImageButton {
        val button = if (color == "red") {
            //this whole thing  below is one constructor
            ImageButton(this).apply {
                setImageResource(R.drawable.red)
                background = null
                contentDescription = "Red Dot Image" //resources.getString(R.string.my_image_desc
                setOnClickListener {
                    (it.parent as? LinearLayoutCompat)?.addView(makeButton("blue"))
                    tally += 1
                    (it.parent as? RelativeLayout)?.addView(score)
                    (it.parent as? LinearLayoutCompat)?.addView(relativeLayout)
                    Log.i("**** TALLY *****", "add tally = "+tally)
                }
                // set the ImageView bounds to match the Drawable's dimensions
                adjustViewBounds = true
                layoutParams = LinearLayoutCompat.LayoutParams(
                    0,
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                    0.1f
                )
            }
        } else { //must be blue
            //this whole thing below is one constructor call technically
            ImageButton(this).apply {
                setImageResource(R.drawable.blue)
                background = null
                contentDescription = "Blue Dot Image" //resources.getString(R.string.my_image_desc
                setOnClickListener {
                    (it.parent as LinearLayoutCompat).removeView(it)
                    Log.i("**** ID *****", "id = "+it.getId())
                    Log.i("***** Parent Id *****","parent = "+it.parent)
                    if (it.getId() == -1) {    // ignore removal of eriginal blue Dot
                        tally -= 1
                        (it.parent as? RelativeLayout)?.addView(score)
                        (it.parent as? LinearLayoutCompat)?.addView(relativeLayout)
                    }
                    (it.parent as? RelativeLayout)?.addView(score)
                    Log.i("**** TALLY *****", "remove tally = "+tally)
                }
                // set the ImageView bounds to match the Drawable's dimensions
                adjustViewBounds = true
                layoutParams = LinearLayoutCompat.LayoutParams(
                    0,
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                    0.1f
                )
            }
        }
        return button
    }// end fun makeButton(color:String):ImageButton
}

private fun RelativeLayout?.addView(score: Int) {

}

