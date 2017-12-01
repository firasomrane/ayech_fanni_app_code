package com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.models;

import org.w3c.dom.Comment;

import java.util.List;

/**
 * Created by ASUS on 01/12/2017.
 */

public class Post {
    private String post_url;
    private String date_created;
    private String image_path;
    private String photo_id;
    private String user_id;
    private String title;
    private String description;
    private String share_type;
    private List<Like> likes;
    private List<Comment> comments;
    private String categorie;

    public Post(String post_url, String date_created, String image_path, String photo_id, String user_id, String title, String description, List<Like> likes, List<Comment> comments ,String share_type ,String categorie) {
        this.post_url = post_url;
        this.date_created = date_created;
        this.image_path = image_path;
        this.photo_id = photo_id;
        this.user_id = user_id;
        this.title = title;
        this.description = description;
        this.likes = likes;
        this.comments = comments;
        this.share_type =share_type;
        this.categorie = categorie;
    }



    public Post() {
    }

    public Post(String post_url, String date_created, String image_path, String photo_id, String user_id, String title, String description , String share_type , String categorie) {
        this.post_url = post_url;
        this.date_created = date_created;
        this.image_path = image_path;
        this.photo_id = photo_id;
        this.user_id = user_id;
        this.title = title;
        this.description = description;
        this.share_type =share_type;
        this.categorie = categorie;
    }

    public String getPost_url() {
        return post_url;
    }

    public void setPost_url(String post_url) {
        this.post_url = post_url;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(String photo_id) {
        this.photo_id = photo_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getShare_type() {
        return share_type;
    }

    public void setShare_type(String share_type) {
        this.share_type = share_type;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    @Override
    public String toString() {
        return "Post{" +
                "post_url='" + post_url + '\'' +
                ", date_created='" + date_created + '\'' +
                ", image_path='" + image_path + '\'' +
                ", photo_id='" + photo_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", share_type='" + share_type + '\'' +
                ", categorie='" + categorie + '\'' +
                '}';
    }
}


