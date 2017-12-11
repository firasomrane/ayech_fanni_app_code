package com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.messages;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.nourelhoudazribi.aaychfanni.R;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.Utils.FirebaseMethods;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.models.Message;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.models.UserSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by ASUS on 09/12/2017.
 */

public class MessagesChatRoom extends AppCompatActivity {
    private static final String TAG = "MessagesChatRoom";


    //vars
    private UserSettings mUserSettings;
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;

    private ListView mMessageListView;
    private MessageAdapter mMessageAdapter;
    private ProgressBar mProgressBar;
    private EditText mMessageEditText;
    private ImageView mSendButton;
    private TextView friendName;
    private ImageView backArrow;

    private UserSettings mCreatorUserSettings,mCurrentUserSettings;
    private String mUsername;
    public Boolean stop;
    public String creator_user_id;


    public  ArrayList<String> keyList;

    private ArrayList<Message> mfriendlyMessages ;

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods mFirebaseMethods;
    private String userID;
    private ChildEventListener mChildEventListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: chatRoomCreated");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messages_chat_room_layout);

        //set the user
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        if (mAuth.getCurrentUser() != null) {
            userID = mAuth.getCurrentUser().getUid();
        }
        mFirebaseMethods = new FirebaseMethods(MessagesChatRoom.this);

        setupFirebaseAuth();

        getIncommingIntents();

        setTheWidgets();


        //set the views
    }

    private void getIncommingIntents(){
        Log.d(TAG, "getIncommingIntents: created");

        Intent intent = getIntent();
        creator_user_id = intent.getStringExtra(getString(R.string.user_id));
        getProfileWidgets();
    }

    private void setTheWidgets(){

            Log.d(TAG, "setTheWidgets: created");

            // Initialize references to views
            mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
            mMessageListView = (ListView) findViewById(R.id.messageListView);
            mMessageEditText = (EditText) findViewById(R.id.messageEditText);
            mSendButton = (ImageView) findViewById(R.id.sendButton);
            friendName = (TextView) findViewById(R.id.friend_name);

             backArrow = (ImageView) findViewById(R.id.m_icon) ;

             backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MessagesChatRoom.this,Messages.class);
                finish();
                startActivity(intent);
            }
        });



           /* // Initialize message ListView and its adapter
            List<Message> friendlyMessages = new ArrayList<>();
            mMessageAdapter = new MessageAdapter(this, R.layout.messages_list_item, friendlyMessages);
            mMessageListView.setAdapter(mMessageAdapter);
*/

            // Initialize progress bar
            mProgressBar.setVisibility(ProgressBar.INVISIBLE);


            // Enable Send button when there's text to send
            mMessageEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence.toString().trim().length() > 0) {
                        mSendButton.setEnabled(true);
                        mSendButton.setVisibility(View.VISIBLE);
                    } else {
                        mSendButton.setEnabled(false);
                        mSendButton.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });


            mMessageEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});


            // Send button sends a message and clears the EditText
            mSendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: Send messages on click

                    String messageText = mMessageEditText.getText().toString();
                    String profile_path = mCurrentUserSettings.getSettings().getProfile_photo();



                    Message friendlyMessage = new Message(profile_path , messageText, userID, creator_user_id);
                    // Clear input box
                    mMessageEditText.setText("");

                    String newMessageKey = myRef.child(getString(R.string.dbname_messages))
                            .child(userID)
                            .child(creator_user_id)
                            .push().getKey();

                    myRef.child(getString(R.string.dbname_messages))
                            .child(userID)
                            .child(creator_user_id)
                            .child(newMessageKey)
                            .setValue(friendlyMessage);
                    myRef.child(getString(R.string.dbname_messages))
                            .child(creator_user_id)
                            .child(userID)
                            .child(newMessageKey)
                            .setValue(friendlyMessage);
                }
            });


    }

    public void getMessages(){
        Log.d(TAG, "getMessages: created");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();


            keyList = new ArrayList<>();
            keyList.add(creator_user_id);
            keyList.add(creator_user_id);

            mfriendlyMessages = new ArrayList<>();

        for(int i = 0; i < keyList.size(); i++) {

            final int count = i;

            Query query = reference.child(getString(R.string.dbname_messages))
                    .child(userID)
                    .child(keyList.get(i));
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    friendName.setText(mCreatorUserSettings.getUser().getUsername());

                    for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                        Log.d(TAG, "onDataChange: found message: " + singleSnapshot.getValue(Message.class).toString());

                        //mFollowing.add(singleSnapshot.child(getString(R.string.field_user_id)).getValue().toString());
                        if(count <keyList.size()-1){
                            mfriendlyMessages.add(singleSnapshot.getValue(Message.class));
                        }


                    }

                    //get the photos
                    if(count == keyList.size() -1){
                        Log.d(TAG, "onDataChange: count = "+count);
                        displayMessages();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        /*// stop = false;
        // keyList = new ArrayList<>();
        mfriendlyMessages = new ArrayList<>();

        myRef.child(getString(R.string.dbname_messages))
                .child(userID)
                .child(creator_user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d(TAG, "onDataChange: datasnapshot is " +dataSnapshot);
                friendName.setText(mCreatorUserSettings.getUser().getUsername());
                Message friendlyMessage = dataSnapshot.getValue(Message.class);
                mfriendlyMessages.add(friendlyMessage);
            }

            displayMessages();


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/



        /*mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                friendName.setText(mCreatorUserSettings.getUser().getUsername());
                String nextkey = dataSnapshot.getKey();
                Log.d(TAG, "onChildAdded: nextkey is "+nextkey);

                if(!keyList.isEmpty()){
                    if(nextkey.equals(keyList.get(0))){
                        stop = true;
                    }
                }

                if(!stop){
                    keyList.add(nextkey);
                    Message friendlyMessage = dataSnapshot.getValue(Message.class);
                    Log.d(TAG, "onChildAdded: friendly message = " +friendlyMessage);
                    mMessageAdapter.add(friendlyMessage);
                }


            }

            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            public void onCancelled(DatabaseError databaseError) {
            }
        };*/

        /*myRef.child(getString(R.string.dbname_messages))
                .child(userID)
                .child(creator_user_id).addChildEventListener(mChildEventListener);

       */

    }





    private void displayMessages(){


        if(mfriendlyMessages != null){
            try{

                Log.d(TAG, "displayMessages: mfriendlyMessages ="+mfriendlyMessages);
                mMessageAdapter = new MessageAdapter(MessagesChatRoom.this, R.layout.messages_list_item, mfriendlyMessages,userID);
                mMessageListView.setAdapter(mMessageAdapter);

            }catch (NullPointerException e){
                Log.e(TAG, "displayPhotos: NullPointerException: " + e.getMessage() );
            }catch (IndexOutOfBoundsException e){
                Log.e(TAG, "displayPhotos: IndexOutOfBoundsException: " + e.getMessage() );
            }
        }

    }





    private void getProfileWidgets(){
        Log.d(TAG, "setCreatorProfileWidgets: created");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //retrieve creator  informations from the database
                mCreatorUserSettings = mFirebaseMethods.getCreatorSettings(dataSnapshot ,creator_user_id);
                mCurrentUserSettings = mFirebaseMethods.getCreatorSettings(dataSnapshot ,userID);

                setTheWidgets();
                //retrieve images for the user in question

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }






     /*
    ------------------------------------ Firebase ---------------------------------------------
     */

    /**
     * Setup the firebase auth object
     */
    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();


                if (user != null) {
                    userID =user.getUid();
                    getMessages();
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //retrieve user information from the database


                //retrieve images for the user in question

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
