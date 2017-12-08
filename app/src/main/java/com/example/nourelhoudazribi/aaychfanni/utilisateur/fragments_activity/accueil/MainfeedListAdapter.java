package com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.accueil;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nourelhoudazribi.aaychfanni.R;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.Utils.FirebaseMethods;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.Utils.Heart;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.compte.profileActivity;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.models.Post;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.models.User;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.models.UserAccountSettings;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ASUS on 02/12/2017.
 */

public class MainfeedListAdapter extends ArrayAdapter<Post> {

    public interface OnLoadMoreItemsListener{
        void onLoadMoreItems();
    }
    OnLoadMoreItemsListener mOnLoadMoreItemsListener;




    private static final String TAG = "MainfeedListAdapter";

    private LayoutInflater mInflater;
    private int mLayoutResource;
    private Context mContext;
    private DatabaseReference mReference;
    private String currentUsername = "";
    private FirebaseMethods mFirebaseMethods;
    public UserAccountSettings settings;
    private String incommingFragment;

    public MainfeedListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Post> objects,String commingFragment) {
        super(context, resource, objects);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayoutResource = resource;
        this.mContext = context;
        mReference = FirebaseDatabase.getInstance().getReference();
        incommingFragment = commingFragment;
    }

    static class ViewHolder{
        CircleImageView mprofileImage;
        String likesString;
        TextView username, timeDetla, caption, likes, comments,description,post_url;
        ImageView image;
        ImageView heartRed, heartWhite, comment,share;



        User user  = new User();
        UserAccountSettings userAccountSettings = new UserAccountSettings();
        StringBuilder users;
        String mLikesString;
        boolean likeByCurrentUser;
        Heart heart;
        GestureDetector detector;
        Post post;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final ViewHolder holder;

        if(convertView == null){
            convertView = mInflater.inflate(mLayoutResource, parent, false);
            holder = new ViewHolder();

            holder.username = (TextView) convertView.findViewById(R.id.post_nom_createur);
            holder.image = (ImageView) convertView.findViewById(R.id.post_image);
            holder.heartRed = (ImageView) convertView.findViewById(R.id.image_heart_red);
            holder.heartWhite = (ImageView) convertView.findViewById(R.id.image_heart);
            holder.comment = (ImageView) convertView.findViewById(R.id.comment);
            holder.share = (ImageView) convertView.findViewById(R.id.share_icon) ;
            holder.likes = (TextView) convertView.findViewById(R.id.post_likes_number);
            holder.comments = (TextView) convertView.findViewById(R.id.post_comments_number);
            holder.caption = (TextView) convertView.findViewById(R.id.post_title);
            holder.description = (TextView) convertView.findViewById(R.id.post_description) ;
            holder.timeDetla = (TextView) convertView.findViewById(R.id.post_time);
            holder.mprofileImage = (CircleImageView) convertView.findViewById(R.id.profile_photo);
            holder.post_url = (TextView) convertView.findViewById(R.id.post_website);
            holder.heart = new Heart(holder.heartWhite, holder.heartRed);
            holder.post = getItem(position);
            //holder.detector = new GestureDetector(mContext, new GestureListener(holder));
            holder.users = new StringBuilder();

            convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
        }

            Log.d(TAG, "onDataChange:mFollowing.size the item number " +position + "   is  " + getItem(position));



        //get the current users username (need for checking likes string)
        //getCurrentUsername();

        //set the caption and the description
        holder.caption.setText(getItem(position).getTitle());
        holder.description.setText(getItem(position).getDescription());
        holder.post_url.setText(getItem(position).getPost_url());

        //get likes string
        //getLikesString(holder);

        //set the comment
        //List<Comment> comments = getItem(position).getComments();
        Random r = new Random();
        int i1 = r.nextInt(10) ;
        Random r2 = new Random();
        int i2 = r2.nextInt(10)+10;
        //holder.comments.setText(comments.size());
        holder.comments.setText(String.valueOf(i1));
        holder.likes.setText(String.valueOf(i2));

        //set the time it was posted
        String timestampDifference = getTimestampDifference(getItem(position));
        if(!timestampDifference.equals("0")){
            holder.timeDetla.setText("IL Y A "+timestampDifference + " JOURS");
        }else{
            holder.timeDetla.setText("AUJOURD'HUI");
        }


        //set the profile image
        final ImageLoader imageLoader = ImageLoader.getInstance();

        final String image_path = getItem(position).getImage_path();
        if(image_path.equals("")){
           /* Log.d(TAG, "onDataChange:mFollowing.size the item that have immage page empty is" +
                    " " +position + "   and image path is     " + getItem(position).getImage_path());*/
            holder.image.setVisibility(View.GONE);
        }
        else
        {
            holder.image.setVisibility(View.VISIBLE);
            imageLoader.displayImage(getItem(position).getImage_path(), holder.image);
        }

        ///get the user

        Log.d(TAG, "onDataChange:mFollowing.size the user for the post number  "+position+"   is  " +
                getItem(position).getUser_id());

        //get the user object
        holder.username.setText("");
        Query userQuery = mReference
                .child(mContext.getString(R.string.dbname_users))
                .orderByChild(mContext.getString(R.string.field_user_id))
                .equalTo(getItem(position).getUser_id());
        userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    Log.d(TAG, "onDataChange: found user: " +
                            singleSnapshot.getValue(User.class));

                    holder.user = singleSnapshot.getValue(User.class);

                    holder.username.setText(holder.user.getUsername());
                    holder.username.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d(TAG, "onClick: navigating to profile of: " +
                                    holder.user.getUsername());

                            Intent intent = new Intent(mContext, profileActivity.class);
                            intent.putExtra(mContext.getString(R.string.calling_activity),
                                    incommingFragment);
                            intent.putExtra(mContext.getString(R.string.user_id), holder.user.getUser_id());
                            mContext.startActivity(intent);
                        }
                    });
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        holder.mprofileImage.setVisibility(View.INVISIBLE);

        mReference.child(mContext.getString(R.string.dbname_user_account_settings))
                .child(getItem(position).getUser_id())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds: dataSnapshot.getChildren()){

                            if(ds.getKey().equals("profile_photo")){
                                settings = new UserAccountSettings();
                                settings.setProfile_photo(ds.getValue(String.class));

                                Log.d(TAG, "onDataChange: the settings are " + settings);

                                if(!settings.getProfile_photo().toString().equals("") ){
                                    holder.userAccountSettings = settings;
                                    Log.d(TAG, "onDataChange: holder.userAccountSettings  " + holder.userAccountSettings);

                                    imageLoader.displayImage(holder.userAccountSettings.getProfile_photo(),
                                            holder.mprofileImage);
                                    holder.mprofileImage.setVisibility(View.VISIBLE);
                                    holder.mprofileImage.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Log.d(TAG, "onClick: navigating to profile of from image: " +
                                                    holder.user.getUsername());

                                            Intent intent = new Intent(mContext, profileActivity.class);
                                            intent.putExtra(mContext.getString(R.string.calling_activity),
                                                    incommingFragment);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            intent.putExtra(mContext.getString(R.string.user_id), holder.user.getUser_id());
                                            mContext.startActivity(intent);
                                        }
                                    });
                                }
                                else{
                                    imageLoader.displayImage("",holder.mprofileImage);
                                    holder.mprofileImage.setVisibility(View.VISIBLE);
                                }
                            }





                    }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

       /* Query userQuery2 = mReference
                .child(mContext.getString(R.string.dbname_user_account_settings))
                .orderByChild(mContext.getString(R.string.field_user_id))
                .equalTo(getItem(position).getUser_id());
        userQuery2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){


                    settings = new UserAccountSettings();
                    settings = singleSnapshot.getValue(UserAccountSettings.class);

                    Log.d(TAG, "onDataChange: the settings are " + settings);
                    if(!settings.getCategorie().toString().equals("") ){
                        holder.userAccountSettings = settings;
                        Log.d(TAG, "onDataChange: holder.userAccountSettings  " + holder.userAccountSettings);

                        imageLoader.displayImage(holder.userAccountSettings.getProfile_photo(),
                                holder.mprofileImage);
                        holder.mprofileImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.d(TAG, "onClick: navigating to profile of from image: " +
                                        holder.user.getUsername());

                                Intent intent = new Intent(mContext, profileActivity.class);
                                intent.putExtra(mContext.getString(R.string.calling_activity),
                                        mContext.getString(R.string.home_activity));
                                intent.putExtra(mContext.getString(R.string.user_id), holder.user.getUser_id());
                                mContext.startActivity(intent);
                            }
                        });
                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/








        //Log.d(TAG, "getView: the user Id " +getItem(position).getUser_id() );

        //get the post image and username
        /*DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child(mContext.getString(R.string.dbname_user_account_settings))
                .orderByChild(mContext.getString(R.string.field_user_id))
                .equalTo(getItem(position).getUser_id());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){

                    // currentUsername = singleSnapshot.getValue(UserAccountSettings.class).getUsername();

                   *//* Log.d(TAG, "onDataChange:mFollowing.size found user username: "+getItem(position).getUser_id()
                            + singleSnapshot.getValue(UserAccountSettings.class).getUsername());*//*

                    //holder.username.setText(singleSnapshot.getValue(UserAccountSettings.class).getUsername());
                    holder.username.setText(holder.user.getUsername());
                    holder.username.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d(TAG, "onClick: navigating to profile of: " +
                                    holder.user.getUsername());

                            Intent intent = new Intent(mContext, profileActivity.class);
                            intent.putExtra(mContext.getString(R.string.calling_activity),
                                    mContext.getString(R.string.home_activity));
                            intent.putExtra(mContext.getString(R.string.user_id), holder.user.getUser_id());
                            mContext.startActivity(intent);
                        }
                    });

                    imageLoader.displayImage(singleSnapshot.getValue(UserAccountSettings.class).getProfile_photo(),
                            holder.mprofileImage);
                    holder.mprofileImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.d(TAG, "onClick: navigating to profile of: " +
                                    holder.user.getUsername());

                            Intent intent = new Intent(mContext, profileActivity.class);
                            intent.putExtra(mContext.getString(R.string.calling_activity),
                                    mContext.getString(R.string.home_activity));
                            intent.putExtra(mContext.getString(R.string.user_id), holder.user.getUser_id());
                            mContext.startActivity(intent);
                        }
                    });



                    //holder.settings = singleSnapshot.getValue(UserAccountSettings.class);
                    *//*holder.comment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ((HomeActivity)mContext).onCommentThreadSelected(getItem(position), holder.settings);

                            //another thing?
                        }
                    });*//*
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/



        /*if(reachedEndOfList(position)){
            loadMoreData();
        }*/

        if(reachedEndOfList(position)){
            loadMoreData();
        }

        return convertView;
    }


    private boolean reachedEndOfList(int position){
        return position == getCount() - 1;
    }

    private void loadMoreData(){

        try{
            mOnLoadMoreItemsListener = (OnLoadMoreItemsListener) getContext();
        }catch (ClassCastException e){
            Log.e(TAG, "loadMoreData: ClassCastException: " +e.getMessage() );
        }

        try{
            mOnLoadMoreItemsListener.onLoadMoreItems();
        }catch (NullPointerException e){
            Log.e(TAG, "loadMoreData: ClassCastException: " +e.getMessage() );
        }
    }


    /*public class GestureListener extends GestureDetector.SimpleOnGestureListener{

        ViewHolder mHolder;
        public GestureListener(ViewHolder holder) {
            mHolder = holder;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.d(TAG, "onDoubleTap: double tap detected.");

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
            Query query = reference
                    .child(mContext.getString(R.string.dbname_posts))
                    .child(mHolder.post.getPhoto_id())
                    .child(mContext.getString(R.string.field_likes));
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){

                        String keyID = singleSnapshot.getKey();

                        //case1: Then user already liked the photo
                        if(mHolder.likeByCurrentUser &&
                                singleSnapshot.getValue(Like.class).getUser_id()
                                        .equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){

                            mReference.child(mContext.getString(R.string.dbname_posts))
                                    .child(mHolder.post.getPhoto_id())
                                    .child(mContext.getString(R.string.field_likes))
                                    .child(keyID)
                                    .removeValue();
///
                            mReference.child(mContext.getString(R.string.dbname_user_posts))
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .child(mHolder.post.getPhoto_id())
                                    .child(mContext.getString(R.string.field_likes))
                                    .child(keyID)
                                    .removeValue();

                            mHolder.heart.toggleLike();
                        }
                        //case2: The user has not liked the photo
                        else if(!mHolder.likeByCurrentUser){
                            //add new like
                            addNewLike(mHolder);
                            break;
                        }
                    }
                    if(!dataSnapshot.exists()){
                        //add new like
                        addNewLike(mHolder);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            return true;
        }
    }*/


    /*private void addNewLike(final ViewHolder holder){
        Log.d(TAG, "addNewLike: adding new like");

        String newLikeID = mReference.push().getKey();
        Like like = new Like();
        like.setUser_id(FirebaseAuth.getInstance().getCurrentUser().getUid());

        mReference.child(mContext.getString(R.string.dbname_posts))
                .child(holder.post.getPhoto_id())
                .child(mContext.getString(R.string.field_likes))
                .child(newLikeID)
                .setValue(like);

        mReference.child(mContext.getString(R.string.dbname_user_posts))
                .child(holder.post.getUser_id())
                .child(holder.post.getPhoto_id())
                .child(mContext.getString(R.string.field_likes))
                .child(newLikeID)
                .setValue(like);

        holder.heart.toggleLike();
    }*/


    /*private void getCurrentUsername(){
        Log.d(TAG, "getCurrentUsername: retrieving user account settings");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child(mContext.getString(R.string.dbname_users))
                .orderByChild(mContext.getString(R.string.field_user_id))
                .equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    currentUsername = singleSnapshot.getValue(UserAccountSettings.class).getUsername();
                    Log.d(TAG, "onDataChange: getCurrentUsername" + currentUsername);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
*/


    /**
     * Returns a string representing the number of days ago the post was made
     * @return
     */
    private String getTimestampDifference(Post photo){
        Log.d(TAG, "getTimestampDifference: getting timestamp difference.");

        String difference = "";
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.FRANCE);
        sdf.setTimeZone(TimeZone.getTimeZone("Africa/Tunis"));//google 'android list of timezones'
        Date today = c.getTime();
        sdf.format(today);
        Date timestamp;
        final String photoTimestamp = photo.getDate_created();
        Log.d(TAG, "getTimestampDifference: " + photo.getDate_created());
        try{
            timestamp = sdf.parse(photoTimestamp);
            difference = String.valueOf(Math.round(((today.getTime() - timestamp.getTime()) / 1000 / 60 / 60 / 24 )));
        }catch (ParseException e){
            Log.e(TAG, "getTimestampDifference: ParseException: " + e.getMessage() );
            difference = "0";
        }
        return difference;
    }
}
