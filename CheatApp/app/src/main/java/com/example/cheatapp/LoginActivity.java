package com.example.cheatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.cheatapp.Models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;


public class LoginActivity extends AppCompatActivity {

    TextView btnSignIn;
    TextView btnSocial;
    TextView btnRegister;
    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;

    ConstraintLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_login );
        addListenerOnButton ();

        btnSignIn = findViewById ( R.id.btnSignIn );
        btnRegister = findViewById ( R.id.btnRegister );
        btnSocial = findViewById ( R.id.button_new_account );
        root = findViewById ( R.id.root_element );

        auth=FirebaseAuth.getInstance ();
        db = FirebaseDatabase.getInstance ();
        users = db.getReference ("Users");

        btnRegister.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                showRegisterWindow();
            }
        } );

        btnSignIn.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                showSignInWindow();
            }
        } );
    }

    private void showSignInWindow(){
        AlertDialog.Builder dialog = new AlertDialog.Builder ( this );
        dialog.setTitle ( "Sign in");
        dialog.setMessage ( "Enter all the data to connect!" );

        LayoutInflater inflater = LayoutInflater.from ( this );
        View sign_in_window = inflater.inflate ( R.layout.sign_in_window, null );
        dialog.setView ( sign_in_window );

        final MaterialEditText email = sign_in_window.findViewById ( R.id.emailField );
        final MaterialEditText pass = sign_in_window.findViewById ( R.id.passlField );

        dialog.setNegativeButton ( "Cancel", new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss ();
            }
        } );

        dialog.setPositiveButton ( "Enter", new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                if (!TextUtils.isEmpty ( email.getText ().toString () )) {
                    if (pass.getText ().toString ().length () < 5) {
                        Snackbar.make ( root, "Enter the password, larger than 5 characters", Snackbar.LENGTH_SHORT ).show ();
                        return;
                    }

                    auth.signInWithEmailAndPassword ( email.getText ().toString (), pass.getText ().toString () )
                            .addOnSuccessListener ( new OnSuccessListener<AuthResult> () {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    startActivity ( new Intent ( LoginActivity.this, ProfileActivity.class ) );
                                    finish ();
                                }
                            } )
                            .addOnFailureListener ( new OnFailureListener () {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Snackbar.make ( root, "Error on authorization!" + e.getMessage (), Snackbar.LENGTH_SHORT ).show ();
                                }
                            } );
                } else {
                    Snackbar.make ( root, "Enter your email adress", Snackbar.LENGTH_SHORT ).show ();
                    return;
                }

            }
        } );

        dialog.show ();
    }

    private void showRegisterWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder ( this );
        dialog.setTitle ( "Sign up");
        dialog.setMessage ( "Enter all data for registration!" );

        LayoutInflater inflater = LayoutInflater.from ( this );
        View register_window = inflater.inflate ( R.layout.register_window, null );
        dialog.setView ( register_window );

        final MaterialEditText email = register_window.findViewById ( R.id.emailField );
        final MaterialEditText pass = register_window.findViewById ( R.id.passlField );
        final MaterialEditText name = register_window.findViewById ( R.id.nameField );
        final MaterialEditText phone = register_window.findViewById ( R.id.phoneField );

        dialog.setNegativeButton ( "Cancel", new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss ();
            }
        } );

        dialog.setPositiveButton ( "Register", new DialogInterface.OnClickListener () {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                if(TextUtils.isEmpty ( email.getText ().toString () )){
                    Snackbar.make ( root, "Enter your email adress", Snackbar.LENGTH_SHORT).show ();
                    return;
                }

                if(TextUtils.isEmpty ( name.getText ().toString () )){
                    Snackbar.make ( root, "Enter your name", Snackbar.LENGTH_SHORT).show ();
                    return;
                }

                if(TextUtils.isEmpty ( phone.getText ().toString () )){
                    Snackbar.make ( root, "Enter your phone", Snackbar.LENGTH_SHORT).show ();
                    return;
                }

                if(pass.getText ().toString ().length ()<5){
                    Snackbar.make ( root, "Enter the password, larger than 5 characters", Snackbar.LENGTH_SHORT).show ();
                    return;
                }

                //Register  user
                auth.createUserWithEmailAndPassword ( email.getText ().toString (), pass.getText ().toString () )
                        .addOnSuccessListener ( new OnSuccessListener<AuthResult> () {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                User user = new User();
                                user.setEmail ( email.getText ().toString () );
                                user.setName ( name.getText ().toString () );
                                user.setPass ( pass.getText ().toString () );
                                user.setPhone ( phone.getText ().toString () );

                                users.child(FirebaseAuth.getInstance ().getCurrentUser ().getUid ())
                                        .setValue ( user )
                                        .addOnSuccessListener ( new OnSuccessListener<Void> () {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Snackbar.make(root, "Added user!", Snackbar.LENGTH_SHORT).show ();

                                            }
                                        } );
                            }
                        } ).addOnFailureListener ( new OnFailureListener () {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make ( root, "Error on registration! This email adress exist" +e.getMessage (),
                                Snackbar.LENGTH_SHORT ).show ();
                    }
                } );
            }
        } );

        dialog.show ();
    }

    public void addListenerOnButton(){
        TextView button_new = findViewById ( R.id.button_new_account );

        button_new.setOnClickListener (
                v -> {
                    Intent Change = new Intent ( "com.example.cheatapp.NewAccActivity" );
                    startActivity ( Change );
                } );

    }

}
