package com.example.cheatapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.github.library.bubbleview.BubbleTextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

public class MainActivity extends AppCompatActivity {

    public static int SIGN_IN_CODE = 1;
    private RelativeLayout activity_main;
    private EmojiconEditText emojiconEditText;
    private ImageView emojiButton, submitButton;
    private EmojIconActions emojIconActions;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult ( requestCode, resultCode, data );
        if(requestCode == SIGN_IN_CODE){
            if(resultCode == RESULT_OK){
                Snackbar.make (activity_main, "Sunteti Inregistrat!", Snackbar.LENGTH_LONG).show ();
                displayAllMessages();
            } else{
                Snackbar.make (activity_main, "Nu Sunteti Inregistrat!", Snackbar.LENGTH_LONG).show ();
                finish ();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        activity_main = findViewById (R.id.activity_main);
        submitButton = findViewById ( R.id.submit_button );
        emojiButton = findViewById ( R.id.emoji_button );
        emojiconEditText = findViewById ( R.id.textField );
        emojIconActions = new EmojIconActions ( getApplicationContext (), activity_main, emojiconEditText, emojiButton );
        emojIconActions.ShowEmojIcon ();

        submitButton.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance ().getReference ().push ().setValue ( new Message (
                    FirebaseAuth.getInstance ().getCurrentUser ().getEmail (),
                        emojiconEditText.getText ().toString ()
                ) );
                emojiconEditText.setText ( "" );
            }
        } );

        //Utilizatorul inca nu este inregistrat
        if(FirebaseAuth.getInstance ().getCurrentUser () == null)
            startActivityForResult ( AuthUI.getInstance ().createSignInIntentBuilder ().build (), SIGN_IN_CODE );
        else {
            Snackbar.make ( activity_main, "Sunteti Inregistrat!", Snackbar.LENGTH_LONG ).show ();
            displayAllMessages ();
        }
    }

    private void displayAllMessages() {
        ListView listOfMessages = findViewById ( R.id.list_of_messages );
        FirebaseListAdapter<Message> adapter = new FirebaseListAdapter<Message> (
                this, Message.class, R.layout.list_item,
                FirebaseDatabase.getInstance ().getReference () ) {
            @Override
            protected void populateView(View v, Message model, int position) {
                TextView mess_user, mess_time;
                BubbleTextView mess_text;

                mess_user = v.findViewById ( R.id.message_user );
                mess_time = v.findViewById ( R.id.message_time );
                mess_text = v.findViewById ( R.id.message_text );

                mess_user.setText ( model.getUserName () );
                mess_text.setText ( model.getTextMessage () );
                mess_time.setText ( DateFormat.format ( "dd-mm-yyyy HH:mm:ss", model.getMessageTime () ) );
            }
        };

        listOfMessages.setAdapter ( adapter );

    }
}
