package com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.messages;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nourelhoudazribi.aaychfanni.R;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.Utils.FirebaseMethods;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.models.UserAccountSettings;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.models.VosCreateurElement;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ASUS on 09/12/2017.
 */

public class MessagesFriendsListAdapter  extends ArrayAdapter<VosCreateurElement> {

    public interface OnLoadMoreItemsListener{
        void onLoadMoreItems();
    }
    OnLoadMoreItemsListener mOnLoadMoreItemsListener;

    private static final String TAG = "MessagesFriendsListAdap";

    private LayoutInflater mInflater;
    private int mLayoutResource;
    private Context mContext;
    private DatabaseReference mReference;
    private String currentUsername = "";
    private FirebaseMethods mFirebaseMethods;
    public UserAccountSettings settings;

    public MessagesFriendsListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<VosCreateurElement> objects) {
        super(context, resource, objects);
        Log.d(TAG, "MessagesFriendsListAdapter: created");
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayoutResource = resource;
        this.mContext = context;
        mReference = FirebaseDatabase.getInstance().getReference();
    }

    static class ViewHolder{
        CircleImageView mprofileImage;
        String creator_user_id;
        ImageView messengerIcon;


        TextView username;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final ViewHolder holder;

        if(convertView == null){
            Log.d(TAG, "getView: convertView == null");
            convertView = mInflater.inflate(mLayoutResource, parent, false);
            holder = new ViewHolder();

            holder.username = (TextView) convertView.findViewById(R.id.username);
            holder.mprofileImage = (CircleImageView) convertView.findViewById(R.id.profile_photo);
            holder.creator_user_id = getItem(position).getCreator_id();
            holder.messengerIcon = (ImageView) convertView.findViewById(R.id.messenger_icon);


            convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Log.d(TAG, "getView: 5démtMessages");


        holder.username.setText(getItem(position).getCreator_name());


        //set the profile image
        final ImageLoader imageLoader = ImageLoader.getInstance();

        final String image_path = getItem(position).getCreator_image_path();

        imageLoader.displayImage(image_path, holder.mprofileImage);


        holder.mprofileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MessagesChatRoom.class);
                intent.putExtra(mContext.getString(R.string.user_id), holder.creator_user_id);
               // intent.putExtra(mContext.getString(R.string.profile_photo),image_path);
                ((Activity)mContext).finish();
                mContext.startActivity(intent);
            }
        });

        holder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MessagesChatRoom.class);
                intent.putExtra(mContext.getString(R.string.user_id), holder.creator_user_id);
               // intent.putExtra(mContext.getString(R.string.profile_photo),image_path);
                ((Activity)mContext).finish();
                mContext.startActivity(intent);
            }
        });
        holder.messengerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MessagesChatRoom.class);
                intent.putExtra(mContext.getString(R.string.user_id), holder.creator_user_id);
               // intent.putExtra(mContext.getString(R.string.profile_photo),image_path);
                ((Activity)mContext).finish();
                mContext.startActivity(intent);
            }
        });

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



}
