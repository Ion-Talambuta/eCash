package com.example.cheatapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button button;
    ImageButton imageButton_1;
    ImageButton imagebutton_2;
    ImageButton imagebutton_3;
    ImageButton imagebutton_4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addListenerOnButton();
    }


    public void addListenerOnButton() {

        button = findViewById ( R.id.button_enter );
        imageButton_1= findViewById ( R.id.imagebutton_1 );
        imagebutton_2= findViewById ( R.id.imagebutton_2 );
        imagebutton_3= findViewById ( R.id.imagebutton_3 );
        imagebutton_4= findViewById ( R.id.imagebutton_4 );

        button.setOnClickListener (
                new View.OnClickListener () {
                    @Override
                    public void onClick(View v) {
                        Intent Change = new Intent ( "com.example.cheatapp.LoginActivity" );
                        startActivity ( Change );
                    }
                } );

        imageButton_1.setOnClickListener ( new View.OnClickListener (){
            @Override
            public void onClick(View arg0){
                Intent Change = new Intent ( "com.example.cheatapp.ContactusActivity" );
                startActivity ( Change );
            }
        } );

        imagebutton_2.setOnClickListener ( new View.OnClickListener (){
            @Override
            public void onClick(View arg0){
                Intent Change = new Intent ( "com.example.cheatapp.ExchangeActivity" );
                startActivity ( Change );
            }
        } );

        imagebutton_3.setOnClickListener ( new View.OnClickListener (){
            @Override
            public void onClick(View arg0){
                Toast.makeText ( MainActivity.this, "ImageButton Info is Clicked!", Toast.LENGTH_SHORT )
                        .show ();
            }
        } );

        imagebutton_4.setOnClickListener ( new View.OnClickListener (){
            @Override
            public void onClick(View arg0){
                Toast.makeText ( MainActivity.this, "ImageButton Backup is Clicked!", Toast.LENGTH_SHORT )
                        .show ();
            }
        } );
    }





}
