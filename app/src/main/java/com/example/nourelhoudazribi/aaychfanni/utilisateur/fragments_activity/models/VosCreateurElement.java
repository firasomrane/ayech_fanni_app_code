package com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.models;

/**
 * Created by ASUS on 07/12/2017.
 */

public class VosCreateurElement {

    private String creator_image_path;
    private String creator_name;
    private String creator_id;

    public VosCreateurElement(String creator_image_path, String creator_name ,String creator_id) {
        this.creator_image_path = creator_image_path;
        this.creator_name = creator_name;
        this.creator_id = creator_id;
    }

    public VosCreateurElement() {
    }




    public String getCreator_image_path() {
        return creator_image_path;
    }

    public void setCreator_image_path(String creator_image_path) {
        this.creator_image_path = creator_image_path;
    }

    public String getCreator_name() {
        return creator_name;
    }

    public void setCreator_name(String creator_name) {
        this.creator_name = creator_name;
    }

    public String getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(String creator_id) {
        this.creator_id = creator_id;
    }

    @Override
    public String toString() {
        return "VosCreateurElement{" +
                "creator_image_path='" + creator_image_path + '\'' +
                ", creator_name='" + creator_name + '\'' +
                ", creator_id='" + creator_id + '\'' +
                '}';
    }
}
