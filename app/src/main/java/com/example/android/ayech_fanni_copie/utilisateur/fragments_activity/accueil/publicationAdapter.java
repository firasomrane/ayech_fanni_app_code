package com.example.android.ayech_fanni_copie.utilisateur.fragments_activity.accueil;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.ayech_fanni_copie.R;

import java.util.ArrayList;

import static com.example.android.ayech_fanni_copie.R.id.post_description;
import static com.example.android.ayech_fanni_copie.R.id.post_image;
import static com.example.android.ayech_fanni_copie.R.id.post_nom_createur;
import static com.example.android.ayech_fanni_copie.R.id.post_time;
import static com.example.android.ayech_fanni_copie.R.id.post_title;
import static com.example.android.ayech_fanni_copie.R.id.post_website;
import static com.example.android.ayech_fanni_copie.R.id.profile_photo;

/**
 * Created by ASUS on 24/11/2017.
 */

public class publicationAdapter extends BaseAdapter {

    Context c;
    ArrayList<Publication> AccueilElements;
    LayoutInflater inflater;

    public publicationAdapter(Context c, ArrayList<Publication> AccueilElements) {
        this.c = c;
        this.AccueilElements = AccueilElements;
    }

    @Override
    public int getCount() {
        return AccueilElements.size();
    }

    @Override
    public Object getItem(int position) {
        return AccueilElements.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(inflater==null)
        {
            inflater= (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView==null)
        {
            convertView=inflater.inflate(R.layout.accueil_element,parent,false);
        }
        //set the ceator name
        TextView nomCreateur = (TextView) convertView.findViewById(post_nom_createur);
        final String CreatorName = AccueilElements.get(position).getNomCreateur();
        nomCreateur.setText(CreatorName);

        //set the postTitle
        TextView titrePublication = (TextView) convertView.findViewById(post_title);
        final String postTitle = AccueilElements.get(position).getTitreDuPoste();
        titrePublication.setText(postTitle);

        //set the post description
        TextView descrptionPublication = (TextView) convertView.findViewById(post_description);
        final String postDescription = AccueilElements.get(position).getDescription();
        descrptionPublication.setText(postDescription);

        //set the post URL
        TextView urlPublication = (TextView) convertView.findViewById(post_website);
        final String postUrl = AccueilElements.get(position).getPublicationURL();
        urlPublication.setText(postUrl);

        //set the post date
        TextView datePublication = (TextView) convertView.findViewById(post_time);
        final String postTime = AccueilElements.get(position).getDate();
        datePublication.setText(postTime);

        //set the post_image
        ImageView imagePublication = (ImageView) convertView.findViewById(post_image);
        Publication currentPublication = AccueilElements.get(position);
        if (currentPublication.hasImage()) {
            // If an image is available, display the provided image based on the resource ID
            imagePublication.setImageResource(currentPublication.getImagePublication());
            // Make sure the view is visible
            //imagePublication.setVisibility(View.VISIBLE);
        }
        else {
            // Otherwise hide the ImageView (set visibility to GONE)
            imagePublication.setVisibility(View.GONE);
        }
        //final int postImage = AccueilElements.get(position).getImagePublication();
       // imagePublication.setImageResource(postImage);

        //set the creator profile photo
        ImageView imageCreateur = (ImageView) convertView.findViewById(profile_photo);
        final int creatorImage = AccueilElements.get(position).getImageCreateur();
        imageCreateur.setImageResource(creatorImage);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c,CreatorName,Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }
}
