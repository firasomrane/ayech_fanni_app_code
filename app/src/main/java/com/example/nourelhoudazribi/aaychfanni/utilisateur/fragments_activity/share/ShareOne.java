package com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.share;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nourelhoudazribi.aaychfanni.R;

/**
 * Created by ASUS on 29/11/2017.
 */

public class ShareOne extends AppCompatActivity {

    private TextView suivant;
    private RelativeLayout cameraRelativeLayout ,phoneImageRelativeLayout ,UrlRelativeLayout;
    private EditText postTitle, postDescription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_one_activity_layout);

        //set the layout widgets
        suivant = (TextView) findViewById(R.id.suivant);
        cameraRelativeLayout = (RelativeLayout) findViewById(R.id.camera_relative_layout);
        phoneImageRelativeLayout = (RelativeLayout) findViewById(R.id.phone_image_relative_layout);
        UrlRelativeLayout = (RelativeLayout) findViewById(R.id.url_relative_layout);
        postTitle = (EditText) findViewById(R.id.post_title);
        postDescription =(EditText) findViewById(R.id.post_description);

        //when clicking on suivant
        suivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShareOne.this , ShareTwo.class);
                startActivity(intent);
            }
        });

        UrlRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog();
            }
        });
    }

    private void customDialog() {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ShareOne.this);
        View mView = getLayoutInflater().inflate(R.layout.url_custom_dialog, null);
        final EditText urlEditText = (EditText) mView.findViewById(R.id.url_edit_text);
        TextView UrlOk = (TextView) mView.findViewById(R.id.ok);
        TextView UrlFermer = (TextView) mView.findViewById(R.id.fermer);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        UrlOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!urlEditText.getText().toString().isEmpty()){
                    Toast.makeText(ShareOne.this,"successful",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }else{
                    Toast.makeText(ShareOne.this, "fill the field", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
