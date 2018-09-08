package com.example.user.dicegame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button b;
    ImageView dice;
     Random r;
     int a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b=findViewById(R.id.b);
        dice=findViewById(R.id.dice);
        r=new Random();

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

           a=r.nextInt(6) + 1;

           if (a==1){
               dice.setImageResource(R.drawable.dice1);
           }else if (a==2){
               dice.setImageResource(R.drawable.dice2);
           }else if (a==3){
               dice.setImageResource(R.drawable.dice3);
           }else if (a==4){
               dice.setImageResource(R.drawable.dice4);
           }else if (a==5){
               dice.setImageResource(R.drawable.dice5);
           }else if (a==6){
               dice.setImageResource(R.drawable.dice6);
           }

            }
        });
    }
}
