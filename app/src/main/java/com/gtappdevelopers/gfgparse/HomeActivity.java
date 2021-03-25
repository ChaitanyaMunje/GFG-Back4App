package com.gtappdevelopers.gfgparse;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.amulyakhare.textdrawable.TextDrawable;

public class HomeActivity extends AppCompatActivity {
    //creating a variable for image view.
    private ImageView tileIV, borderIV, circleIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //on below line we are initializing our image view
        tileIV = findViewById(R.id.idIVTile);
        borderIV = findViewById(R.id.idIVBorder);
        circleIV = findViewById(R.id.idIVCircle);

        //on below line we are creating a new text drawable
        TextDrawable tileImg = TextDrawable.builder()
                //begin config method is use to start the config.
                .beginConfig()
                //on below line we are setting width and height for our drawable.
                .width(130)  // width in px
                .height(130) // height in px
                //on below line we are ending the config.
                .endConfig()
                //as we are building a rectangle we are using a build rect method to create a new rectangle
                //and inside that we are passing the text as G and color for the drawable.
                .buildRect("G", getResources().getColor(R.color.purple_200));
        tileIV.setImageDrawable(tileImg);


        //below text drawable is for round rectangle
        TextDrawable roundRect = TextDrawable.builder().beginConfig()
                .width(130)  // width in px
                .height(130) // height in px
                .endConfig()
                //as we are building a rectangle with round corners we are calling a build round rect method
                //in that method we are passing the text, color and radius for our radius.
                .buildRoundRect("G", getResources().getColor(R.color.purple_200), 10); // radius in px
        borderIV.setImageDrawable(roundRect);


        //below text drawable is a circular.
        TextDrawable drawable2 = TextDrawable.builder().beginConfig()
                .width(130)  // width in px
                .height(130) // height in px
                .endConfig()
                //as we are building a circular drawable we are calling a build round method.
                //in that method we are passing our text and color.
                .buildRound("F", getResources().getColor(R.color.purple_200));
        circleIV.setImageDrawable(drawable2);

    }
}