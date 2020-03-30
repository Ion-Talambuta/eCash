package com.example.cheatapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class ProfileActivity extends FragmentActivity {


    //Google declaration
    public static final String GOOGLE_ACCOUNT = "google_account";
    private TextView profileName, profileEmail;
    TextView signOut;
    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_profile );

        profileName = findViewById ( R.id.profile_text );
        profileEmail = findViewById ( R.id.profile_email );
        signOut = findViewById ( R.id.sign_out );
        setDataOnView ();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder ( GoogleSignInOptions.DEFAULT_SIGN_IN )
                .requestEmail ()
                .build ();
        googleSignInClient = GoogleSignIn.getClient ( this, gso );

        signOut.setOnClickListener ( v -> googleSignInClient.signOut ().addOnCompleteListener ( task -> {
            Intent intent = new Intent ( ProfileActivity.this, MainActivity.class );
            intent.addFlags ( Intent.FLAG_ACTIVITY_CLEAR_TOP );
            startActivity ( intent );
        } ) );

    }

    private void setDataOnView () {
        GoogleSignInAccount googleSignInAccount = getIntent ().getParcelableExtra ( GOOGLE_ACCOUNT );
        assert googleSignInAccount != null;
        profileName.setText ( googleSignInAccount.getDisplayName () );
        profileEmail.setText ( googleSignInAccount.getEmail () );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return super.onCreateOptionsMenu ( menu );
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        TextView headerView = findViewById(R.id.header);
        switch(id){
            case R.id.action_settings :
                headerView.setText ( "Setting" );
                return true;
            case R.id.action_information:
                headerView.setText("Information");
                return true;
            case R.id.action_about:
                headerView.setText("About Us");
                return true;
            case R.id.action_help:
                headerView.setText("Help");
                return true;
            case R.id.action_logout:
                headerView.setText("Log Out");
                return true;
        }
        return super.onOptionsItemSelected ( item );
    }
}

