package com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.explorer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nourelhoudazribi.aaychfanni.R;
import com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.fragments_activity.elementExplore;
import java.util.ArrayList;

import static com.example.nourelhoudazribi.aaychfanni.R.id.nameTxt;

/**
 * Created by ASUS on 13/11/2017.
 */

public class CustomAdapter extends BaseAdapter {
    Context c;
    ArrayList<elementExplore> elementExplores;
    LayoutInflater inflater;
    public CustomAdapter(Context c, ArrayList<elementExplore> elementExplores) {
        this.c = c;
        this.elementExplores = elementExplores;
    }
    @Override
    public int getCount() {
        return elementExplores.size();
    }
    @Override
    public Object getItem(int position) {
        return elementExplores.get(position);
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
            convertView=inflater.inflate(R.layout.model,parent,false);
        }
        TextView nameText= (TextView) convertView.findViewById(nameTxt);
        final String name=elementExplores.get(position).getName();
        nameText.setText(name);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c,name,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(c,ExploreCategoryActivity.class);
                intent.putExtra(c.getString(R.string.categorie), name);
                c.startActivity(intent);
            }
        });
        return convertView;
    }
}