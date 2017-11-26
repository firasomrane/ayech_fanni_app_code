package com.example.nourelhoudazribi.aaychfanni.utilisateur.fragments_activity.accueil;

/**
 * Created by ASUS on 23/11/2017.
 */

public class Publication {
    private String nomCreateur ,titreDuPoste , description,publicationURL;
    private String date;
    private int nombreCommentaire =10 ,nombreJaime = 100 ;
    private int imagePublication = NO_IMAGE_PROVIDED;
    private int imageCreateur = NO_IMAGE_PROVIDED;

    /** Constant value that represents no image was provided for this Publication */
    private static final int NO_IMAGE_PROVIDED = -1;

    public Publication(String nomCreateur, String titreDuPoste, String description, String publicationURL, String date, int nombreCommentaire, int nombreJaime, int imagePublication, int imageCreateur) {
        this.nomCreateur = nomCreateur;
        this.titreDuPoste = titreDuPoste;
        this.description = description;
        this.publicationURL = publicationURL;
        this.date = date;
        this.nombreCommentaire = nombreCommentaire;
        this.nombreJaime = nombreJaime;
        this.imagePublication = imagePublication;
        this.imageCreateur = imageCreateur;
    }

    public Publication(String nomCreateur, String titreDuPoste, String description, String publicationURL, String date, int nombreCommentaire, int nombreJaime, int imageCreateur) {
        this.nomCreateur = nomCreateur;
        this.titreDuPoste = titreDuPoste;
        this.description = description;
        this.publicationURL = publicationURL;
        this.date = date;
        this.nombreCommentaire = nombreCommentaire;
        this.nombreJaime = nombreJaime;
        this.imageCreateur = imageCreateur;
    }

    public Publication(String nomCreateur, String titreDuPoste, String description,String publicationURL, String date, int imagePublication, int imageCreateur) {
        this.nomCreateur = nomCreateur;
        this.titreDuPoste = titreDuPoste;
        this.description = description;
        this.date = date;
        this.publicationURL = publicationURL;
        this.imagePublication = imagePublication;
        this.imageCreateur = imageCreateur;
    }

    //without postImage
    public Publication(String nomCreateur, String titreDuPoste, String description,String publicationURL, String date, int imageCreateur) {
        this.nomCreateur = nomCreateur;
        this.titreDuPoste = titreDuPoste;
        this.description = description;
        this.date = date;
        this.publicationURL = publicationURL;
        this.imageCreateur = imageCreateur;
    }

    ///Getters and Setters

    public void setNomCreateur(String nomCreateur) {
        this.nomCreateur = nomCreateur;
    }

    public void setTitreDuPoste(String titreDuPoste) {
        this.titreDuPoste = titreDuPoste;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPublicationURL(String publicationURL) {
        this.publicationURL = publicationURL;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setNombreCommentaire(int nombreCommentaire) {
        this.nombreCommentaire = nombreCommentaire;
    }

    public void setNombreJaime(int nombreJaime) {
        this.nombreJaime = nombreJaime;
    }

    public void setImagePublication(int imagePublication) {
        this.imagePublication = imagePublication;
    }

    public void setImageCreateur(int imageCreateur) {
        this.imageCreateur = imageCreateur;
    }

    public String getNomCreateur() {
        return nomCreateur;
    }

    public String getTitreDuPoste() {
        return titreDuPoste;
    }

    public String getDescription() {
        return description;
    }

    public String getPublicationURL() {
        return publicationURL;
    }

    public String getDate() {
        return date;
    }

    public int getNombreCommentaire() {
        return nombreCommentaire;
    }

    public int getNombreJaime() {
        return nombreJaime;
    }

    public int getImagePublication() {
        return imagePublication;
    }

    public int getImageCreateur() {
        return imageCreateur;
    }

    public boolean hasImage() {
        return imagePublication != NO_IMAGE_PROVIDED;
    }

    @Override
    public String toString() {
        return "Publication{" +
                "nomCreateur='" + nomCreateur + '\'' +
                ", titreDuPoste='" + titreDuPoste + '\'' +
                ", description='" + description + '\'' +
                ", publicationURL='" + publicationURL + '\'' +
                ", date=" + date +
                ", nombreCommentaire=" + nombreCommentaire +
                ", nombreJaime=" + nombreJaime +
                ", imagePublication=" + imagePublication +
                ", imagePublication=" + imageCreateur+'}';
    }
}
