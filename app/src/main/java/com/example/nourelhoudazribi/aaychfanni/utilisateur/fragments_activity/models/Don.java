package com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.models;

/**
 * Created by ASUS on 06/12/2017.
 */

public class Don {

    private String creator_user_id;
    private String simple_user_id;
    private String date_created;
    private long trasaction_sum;
    private String type_don;

    public Don(String creator_user_id, String simple_user_id, String date_created, long trasaction_sum, String type_don) {
        this.creator_user_id = creator_user_id;
        this.simple_user_id = simple_user_id;
        this.date_created = date_created;
        this.trasaction_sum = trasaction_sum;
        this.type_don = type_don;
    }

    public Don() {
    }

    public String getCreator_user_id() {
        return creator_user_id;
    }

    public void setCreator_user_id(String creator_user_id) {
        this.creator_user_id = creator_user_id;
    }

    public String getSimple_user_id() {
        return simple_user_id;
    }

    public void setSimple_user_id(String simple_user_id) {
        this.simple_user_id = simple_user_id;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public long getTrasaction_sum() {
        return trasaction_sum;
    }

    public void setTrasaction_sum(long trasaction_sum) {
        this.trasaction_sum = trasaction_sum;
    }

    public String getType_don() {
        return type_don;
    }

    public void setType_don(String type_don) {
        this.type_don = type_don;
    }

    @Override
    public String toString() {
        return "Don{" +
                "creator_user_id='" + creator_user_id + '\'' +
                ", simple_user_id='" + simple_user_id + '\'' +
                ", date_created='" + date_created + '\'' +
                ", trasaction_sum=" + trasaction_sum +
                ", type_don=" + type_don +
                '}';
    }
}


