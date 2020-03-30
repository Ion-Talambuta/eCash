package com.example.primaaplicatie;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import static android.graphics.Color.*;

public class MainActivity extends AppCompatActivity {

    //declarare variabile globale
    private EditText pass;
    private Button btn1;
    private Button btn_alert;
    private Button act_change;
    private RatingBar rating;
    private TextView text_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addListenerOnButton();
    }

    private void addListenerOnButton() {
        pass = (EditText) findViewById(R.id.editText2);
        btn1 = (Button) findViewById(R.id.button);
        btn_alert = (Button) findViewById(R.id.alert);
        act_change=(Button)findViewById(R.id.act_change);

        btn1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btn1.setText("Done");
                        btn1.setBackgroundTintList(ColorStateList.valueOf(GRAY));
                        Toast.makeText(
                                MainActivity.this, pass.getText(),
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
        );

        act_change.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(".SecondActivity");
                        startActivity(intent);
                    }
                }
        );

        rating = (RatingBar)findViewById(R.id.ratingBar);
        text_show =(TextView)findViewById(R.id.textView);

        rating.setOnRatingBarChangeListener(
                new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        text_show.setText("Rating: " + String.valueOf(rating));
                    }
                }
        );

        btn_alert.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(MainActivity.this);
                        a_builder.setMessage("Dvs. sigur doriti sa inchideti aplicatia?");
                        a_builder.setCancelable(false);
                        a_builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        a_builder.setNegativeButton("Nu", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog alert = a_builder.create();
                        alert.setTitle("Close Application");
                        alert.show();
                    }
                }
        );
    }
}
