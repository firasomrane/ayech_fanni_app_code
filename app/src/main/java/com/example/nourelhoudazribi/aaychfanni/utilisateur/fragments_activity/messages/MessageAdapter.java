package com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.messages;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.nourelhoudazribi.aaychfanni.R;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.Utils.FirebaseMethods;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.models.Message;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.models.UserAccountSettings;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ASUS on 09/12/2017.
 */

public class MessageAdapter extends ArrayAdapter<Message> {


    private static final String TAG = "MainfeedListAdapter";

    private LayoutInflater mInflater;
    private int mLayoutResource;
    private Context mContext;
    private DatabaseReference mReference;
    private String currentUsername = "";
    private FirebaseMethods mFirebaseMethods;
    public UserAccountSettings settings;


    private String userID;

    public MessageAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Message> objects, String userID) {
        super(context, resource, objects);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayoutResource = resource;
        this.mContext = context;
        mReference = FirebaseDatabase.getInstance().getReference();
        this.userID = userID;
    }

static class ViewHolder{
    CircleImageView mprofileImage;
    String messageText;

    TextView messageTextView;

}

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        final ViewHolder holder;

        if(convertView == null){
            Log.d(TAG, "getView: convertView == null");
            convertView = mInflater.inflate(mLayoutResource, parent, false);
            holder = new ViewHolder();

            holder.messageTextView = (TextView) convertView.findViewById(R.id.messageTextView);
            holder.mprofileImage = (CircleImageView) convertView.findViewById(R.id.profile_photo);
            holder.messageText = getItem(position).getMessage_text();

            convertView.setTag(holder);

        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.messageTextView.setText(holder.messageText);

        //set the profile image
        final ImageLoader imageLoader = ImageLoader.getInstance();

        final String image_path = getItem(position).getProfile_image();

        imageLoader.displayImage(image_path, holder.mprofileImage);


        return convertView;
    }




}
