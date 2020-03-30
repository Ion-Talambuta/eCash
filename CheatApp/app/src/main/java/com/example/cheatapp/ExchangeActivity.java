package com.example.cheatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ExchangeActivity extends AppCompatActivity {

     Button Buton1;
     Button Button2;
     Button Button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_exchange );
    }

    public void Change(View view){
        Fragment fragment= null;
        switch (view.getId ()){
            case R.id.Buton1:
                fragment= new OnlineFragment ();
                break;
            case R.id.Buton2:
                fragment= new DobanziFragment ();
                break;
            case R.id.Buton3:
                fragment = new BNRFragment ();
                break;
        }

        FragmentManager fm= getSupportFragmentManager ();
        FragmentTransaction ft = fm.beginTransaction ();
        assert fragment != null;
        ft.replace(R.id.online, fragment);
        ft.commit ();
    }
}
