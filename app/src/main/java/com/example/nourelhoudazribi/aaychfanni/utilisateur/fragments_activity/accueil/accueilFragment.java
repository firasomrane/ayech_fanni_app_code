package com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.accueil;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.nourelhoudazribi.aaychfanni.MainActivity;
import com.example.nourelhoudazribi.aaychfanni.R;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.Utils.FirebaseMethods;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.models.Comment;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.models.Post;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.models.User;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.models.UserSettings;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.share.ShareOne;
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
 * Created by ASUS on 13/11/2017.
 */

public class accueilFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "accueilFragment";
    private Button betterExperience;
    private RelativeLayout relativeLayout,createPost;
    private ListView lv;

    //add Firebase Database stuff
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private  String userID;
    private FirebaseMethods mFirebaseMethods;

    //vars
    private ArrayList<Post> mPhotos;
    private ArrayList<Post> mPaginatedPhotos;
    private ArrayList<String> mFollowing;
    private ListView mListView;
    private MainfeedListAdapter mAdapter;
    private int mResults;
    public Integer loadedPosts;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: created");
        View rootView=inflater.inflate(R.layout.accueil_fragment,container,false);

        mListView = (ListView) rootView.findViewById(R.id.listView);
        mFollowing = new ArrayList<>();
        mPhotos = new ArrayList<>();



        //set the user
        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        if(mAuth.getCurrentUser() != null){
            userID = mAuth.getCurrentUser().getUid();
        }
        mFirebaseMethods = new FirebaseMethods(getActivity());

        //set the clickListener when the user is not logged in

        betterExperience =(Button) rootView.findViewById(R.id.better_experience_button);
        betterExperience.setOnClickListener(this);

        //check if the user is logged in or not to choose which layout to show
        setupFirebaseAuth(rootView);

        return rootView;
    }

    private void getFollowing(){
        Log.d(TAG, "getFollowing: searching for following");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        mFollowing = new ArrayList<>();
        Query query = reference
                .child(getString(R.string.dbname_following))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    Log.d(TAG, "onDataChange: found user: " +singleSnapshot.child("user_id").getValue().toString());

                    //mFollowing.add(singleSnapshot.child(getString(R.string.field_user_id)).getValue().toString());
                   mFollowing.add(singleSnapshot.child("user_id").getValue().toString());

                }
                mFollowing.add(FirebaseAuth.getInstance().getCurrentUser().getUid());
                mFollowing.add("zzzzzzzzzzzzzzzzzzzzzzzz");

                //get the photos
                getPhotos();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getPhotos(){
        Log.d(TAG, "getPhotos: getting photos");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        mPhotos = new ArrayList<>();
        for(int i = 0; i < mFollowing.size(); i++){

            final int count = i;

            Query query = reference
                    .child(getString(R.string.dbname_user_posts))
                    .child(mFollowing.get(i))
                    .orderByChild(getString(R.string.field_user_id))
                    .equalTo(mFollowing.get(i));
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){

                        Post post = new Post();
                        Map<String, Object> objectMap = (HashMap<String, Object>) singleSnapshot.getValue();
                        Log.d(TAG, "onDataChange: the hash map" +objectMap);

                        //if the following is deferent from the lastone who is zzzzzzzzzzzzzzzzzz
                        if ((count < mFollowing.size()-1) && (!objectMap.isEmpty()) ) {
                            //Log.d(TAG, "onDataChange: count <mFollowing.size()-1  et count =  " +
                            //count);

                            if(objectMap.get(getString(R.string.field_title)) != null){
                                post.setTitle(objectMap.get(getString(R.string.field_title)).toString());
                            }


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
                            mPhotos.add(post);

                        }
                    }


                    if(count == mFollowing.size() -1 ){
                        //display our photos
                        Log.d(TAG, "onDataChange: mFollowing.size - 1  == count " + (mFollowing.size()-1));
                        Log.d(TAG, "onDataChange:mFollowing.size the posts table" + mPhotos);
                        displayPhotos();
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void displayPhotos(){
        mPaginatedPhotos = new ArrayList<>();
        if(mPhotos != null){
            try{
                Collections.sort(mPhotos, new Comparator<Post>() {
                    @Override
                    public int compare(Post o1, Post o2) {
                        return o2.getDate_created().compareTo(o1.getDate_created());
                    }
                });

                int iterations = mPhotos.size();

                if(iterations > 20){
                    iterations = 20;
                }

                mResults = 20;
                for(int i = 0; i < iterations; i++){
                    mPaginatedPhotos.add(mPhotos.get(i));
                }
               Log.d(TAG, "onDataChange:mFollowing.size the mPaginatedPhotos     " + mPaginatedPhotos);

                mAdapter = new MainfeedListAdapter(getActivity(), R.layout.accueil_element, mPaginatedPhotos,"one");
                mListView.setAdapter(mAdapter);

            }catch (NullPointerException e){
                Log.e(TAG, "displayPhotos: NullPointerException: " + e.getMessage() );
            }catch (IndexOutOfBoundsException e){
                Log.e(TAG, "displayPhotos: IndexOutOfBoundsException: " + e.getMessage() );
            }
        }

    }

    public void displayMorePhotos(){
        Log.d(TAG, "displayMorePhotos: displaying more photos");

        try{

            if(mPhotos.size() > mResults && mPhotos.size() > 0){

                int iterations;
                if(mPhotos.size() > (mResults + 20)){
                    Log.d(TAG, "displayMorePhotos: there are greater than 20 more photos");
                    iterations = 20;
                }else{
                    Log.d(TAG, "displayMorePhotos: there is less than 20 more photos");
                    iterations = mPhotos.size() - mResults;
                }

                //add the new photos to the paginated results
                for(int i = mResults; i < mResults + iterations; i++){
                    mPaginatedPhotos.add(mPhotos.get(i));
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


    @Override
    public void onClick(View v) {
        // handling onClick Events
        switch (v.getId()) {

            case R.id.better_experience_button:
                // code for button when user clicks devenir createur.
                Intent intent = new Intent(getActivity(), MainActivity.class);
                getActivity().finish();
                startActivity(intent);

                break;

            default:
                break;
        }
    }


    /*private ArrayList<Publication> getAccueilElements() {
        ArrayList<Publication> AccueilElements=new ArrayList<>();
        Publication newPost=new Publication("Firas Omrane","New post title", "This is the description for the post", "Youtube.com", "24/11/2017", R.drawable.youtube_image,R.drawable.user_photo);
        AccueilElements.add(newPost);
        newPost=new Publication("Firas Omrane","New post title", "This is the description for the post", "Youtube.com", "24/11/2017",R.drawable.user_photo);
        AccueilElements.add(newPost);
        newPost=new Publication("Firas Omrane","New post title", "This is the description for the post", "Youtube.com", "24/11/2017" , R.drawable.youtube_image,R.drawable.user_photo);
        AccueilElements.add(newPost);
        newPost=new Publication("Firas Omrane","New post title", "This is the description for the post", "Youtube.com", "24/11/2017" ,  R.drawable.youtube_image,R.drawable.user_photo);
        AccueilElements.add(newPost);
        return AccueilElements;

    }*/


    @Override
    public String toString() {
        return "accueil";
    }

    /**
     * Created by ASUS on 14/11/2017.
     */


    /************************FIREBASE**********************/
    /**
     * checks to see if the @param 'user' is logged in
     * @param user
     */
    private void checkCurrentUser(FirebaseUser user, final android.view.View rootView){
        Log.d(TAG, "checkCurrentUser: checking if user is logged in.");

        relativeLayout = (RelativeLayout) rootView.findViewById(R.id.subscribe_for_a_better_experience) ;
        /*lv= (ListView) rootView.findViewById(R.id.accueil_fragment_list);*/

        if(user == null){
            //this is used to change the activity if the user is not logged in
            /*Intent intent = new Intent(this.getActivity(), MainActivity.class);
            startActivity(intent);*/

            //If the user is not logged in we will choose to set the visibility in the layout

            relativeLayout.setVisibility(View.VISIBLE);
            mListView.setVisibility(View.GONE);
        }
        else{
            relativeLayout.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);

            //if the user is logged in fetch his profile informations
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    //checks if he hese creator and show remaining parts
                    setCreatorWidgets(mFirebaseMethods.getUserSettings(dataSnapshot),rootView);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    //set the apperance if the user is creator
    private void setCreatorWidgets(UserSettings userSettings , android.view.View rootView){
        Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + userSettings.toString());
        Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + userSettings.getUser().getEst_createur());

        User user = userSettings.getUser();
        //if the user is creator set the visivility of the remaining layout
        if(user.getEst_createur()){
            createPost =(RelativeLayout) rootView.findViewById(R.id.create_post);
            createPost.setVisibility(View.VISIBLE);

            createPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity() , ShareOne.class);
                    startActivity(intent);
                }
            });

        }

    }



    /*public void onCommentThreadSelected(Post photo, String callingActivity){
        Log.d(TAG, "onCommentThreadSelected: selected a coemment thread");

        ViewCommentsFragment fragment  = new ViewCommentsFragment();
        Bundle args = new Bundle();
        args.putParcelable(getString(R.string.photo), photo);
        args.putString(getString(R.string.home_activity), getString(R.string.home_activity));
        fragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(getString(R.string.view_comments_fragment));
        transaction.commit();

    }*/


    /**
     * Setup the firebase auth object
     */
    private void setupFirebaseAuth(android.view.View rootView){
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        final View rootViewOne = rootView ;
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                //check if the user is logged in
                checkCurrentUser(user,rootViewOne);

                if (user != null) {
                    // User is signed in
                    getFollowing();
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
        Toast.makeText(this.getActivity(),message,Toast.LENGTH_SHORT).show();
    }


}
