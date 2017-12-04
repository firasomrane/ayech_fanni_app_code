package com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.explorer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nourelhoudazribi.aaychfanni.R;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.Utils.FirebaseMethods;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.accueil.MainfeedListAdapter;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.fragments_activity.theEssentialActivity;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.models.Comment;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.models.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ASUS on 04/12/2017.
 */

public class ExploreCategoryActivity extends AppCompatActivity implements
        MainfeedListAdapter.OnLoadMoreItemsListener {

    @Override
    public void onLoadMoreItems() {

        displayMorePosts();

    }


    private static final String TAG = "ExploreCategoryActivity";

    //widgets
    private TextView categoryTextView;
    private ListView mListView;
    private ImageView backArrow;

    //add Firebase Database stuff
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private  String userID;
    private FirebaseMethods mFirebaseMethods;

    //vars
    public String category;
    private ArrayList<Post> mPosts;
    private ArrayList<Post> mPaginatedPosts;
    private ArrayList<String> mCategories;
    private MainfeedListAdapter mAdapter;
    private int mResults;
    public String lastone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explore_category_activity_layout);
        mListView = (ListView) findViewById(R.id.listView);
        mCategories = new ArrayList<>();
        mPosts = new ArrayList<>();

        //set the user
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        if(mAuth.getCurrentUser() != null){
            userID = mAuth.getCurrentUser().getUid();
        }
        mFirebaseMethods = new FirebaseMethods(ExploreCategoryActivity.this);

        categoryTextView = (TextView) findViewById(R.id.category_name) ;
        backArrow = (ImageView) findViewById(R.id.m_icon) ;
        getIntentBundles();

        setupFirebaseAuth();


    }

    private void getIntentBundles(){
        Intent intent = getIntent();
        category = "";
        category = intent.getStringExtra(getString(R.string.categorie)).toString();
        Log.d(TAG, "getIntentBundles: category  " + category);
        categoryTextView.setText(category);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExploreCategoryActivity.this , theEssentialActivity.class);
                finish();
                startActivity(intent);
            }
        });
    }


    ///get the posts that have the chosen category
    private void getCategoryPosts(){
        Log.d(TAG, "getCategoryPosts: activated for category  " +category);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        mCategories = new ArrayList<>();
        mPosts = new ArrayList<>();
        mCategories.add(category);
        lastone = "lastone";
        mCategories.add(lastone);
        //Log.d(TAG, "getCategoryPosts: mCategories is "+ mCategories);

        for(int i = 0; i < mCategories.size(); i++){

            final int count = i;
            //Log.d(TAG, "getCategoryPosts: count is "+ count);

            Query query = reference
                    .child(getString(R.string.dbname_posts))
                    .orderByChild(getString(R.string.categorie))
                    .equalTo(mCategories.get(i));
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){

                        Post post = new Post();
                        Map<String, Object> objectMap = (HashMap<String, Object>) singleSnapshot.getValue();
                        Log.d(TAG, "onDataChange: the hash map for explore" +objectMap);

                        //if the following is deferent from the lastone who is zzzzzzzzzzzzzzzzzz
                        if( count < mCategories.size()-1){
                            Log.d(TAG, "onDataChange: count <mCategories.size()-1  et count =  " + count);
                            post.setTitle(objectMap.get(getString(R.string.field_title)).toString());
                            post.setPost_url(objectMap.get("post_url").toString());
                            post.setDescription(objectMap.get(getString(R.string.field_description)).toString());
                            post.setPhoto_id(objectMap.get(getString(R.string.field_photo_id)).toString());
                            post.setUser_id(objectMap.get(getString(R.string.field_user_id)).toString());
                            post.setCategorie(objectMap.get("categorie").toString());
                            post.setDate_created(objectMap.get(getString(R.string.field_date_created)).toString());
                            post.setShare_type(objectMap.get("share_type").toString());
                            post.setImage_path(objectMap.get(getString(R.string.field_image_path)).toString());

                            ArrayList<Comment> comments = new ArrayList<Comment>();
                                /*for (DataSnapshot dSnapshot : singleSnapshot
                                        .child(getString(R.string.field_comments)).getChildren()){
                                    Comment comment = new Comment();
                                    comment.setUser_id(dSnapshot.getValue(Comment.class).getUser_id());
                                    comment.setComment(dSnapshot.getValue(Comment.class).getComment());
                                    comment.setDate_created(dSnapshot.getValue(Comment.class).getDate_created());
                                    comments.add(comment);
                                }*/

                            post.setComments(comments);
                            mPosts.add(post);
                            Log.d(TAG, "onDataChange: the mPosts for explore" +mPosts);

                        }
                    }


                    if(count == mCategories.size() -1 ){
                        //display our photos
                        Log.d(TAG, "onDataChange: mCategories.size - 1  == count " + (mCategories.size()-1));
                        Log.d(TAG, "onDataChange:mFollowing.size the posts table" + mPosts);
                        displayPosts();
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }




    private void displayPosts(){
        mPaginatedPosts = new ArrayList<>();
        if(mPosts != null){
            try{
                Collections.sort(mPosts, new Comparator<Post>() {
                    @Override
                    public int compare(Post o1, Post o2) {
                        return o2.getDate_created().compareTo(o1.getDate_created());
                    }
                });

                int iterations = mPosts.size();

                if(iterations > 10){
                    iterations = 10;
                }

                mResults = 10;
                for(int i = 0; i < iterations; i++){
                    mPaginatedPosts.add(mPosts.get(i));
                }
                Log.d(TAG, "onDataChange:mFollowing.size the mPaginatedPhotos     " + mPaginatedPosts);

                mAdapter = new MainfeedListAdapter(ExploreCategoryActivity.this, R.layout.accueil_element, mPaginatedPosts);
                mListView.setAdapter(mAdapter);

            }catch (NullPointerException e){
                Log.e(TAG, "displayPhotos: NullPointerException: " + e.getMessage() );
            }catch (IndexOutOfBoundsException e){
                Log.e(TAG, "displayPhotos: IndexOutOfBoundsException: " + e.getMessage() );
            }
        }

    }




    public void displayMorePosts(){
        Log.d(TAG, "displayMorePhotos: displaying more photos");

        try{

            if(mPosts.size() > mResults && mPosts.size() > 0){

                int iterations;
                if(mPosts.size() > (mResults + 10)){
                    Log.d(TAG, "displayMorePhotos: there are greater than 10 more photos");
                    iterations = 10;
                }else{
                    Log.d(TAG, "displayMorePhotos: there is less than 10 more photos");
                    iterations = mPosts.size() - mResults;
                }

                //add the new photos to the paginated results
                for(int i = mResults; i < mResults + iterations; i++){
                    mPaginatedPosts.add(mPosts.get(i));
                }
                mResults = mResults + iterations;
                mAdapter.notifyDataSetChanged();
            }
        }catch (NullPointerException e){
            Log.e(TAG, "displayPhotos: NullPointerException: " + e.getMessage() );
        }catch (IndexOutOfBoundsException e){
            Log.e(TAG, "displayPhotos: IndexOutOfBoundsException: " + e.getMessage() );
        }
    }





    /**************************Firebase****************/

    /**
     * Setup the firebase auth object
     */
    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                //check if the user is logged in

                if (user != null) {
                    // User is signed in
                    getCategoryPosts();
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    //toastMessage("Successfully signed in with: " + user.getEmail());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    // toastMessage("Successfully signed out.");
                }
                // ...
            }
        };
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

    private void toastMessage(String message){
        Toast.makeText(ExploreCategoryActivity.this,message,Toast.LENGTH_SHORT).show();
    }
}
