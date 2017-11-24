package com.example.android.ayech_fanni_copie.utilisateur.fragments_activity.accueil;

import java.util.Date;

/**
 * Created by ASUS on 23/11/2017.
 */

public class Publication {
    private String nomCreateur ,titreDuPoste ,Description ,publicationURL;
    private Date date;
    private int nombreCommentaire ,nombreJaime ;
    private int mImageResourceId = NO_IMAGE_PROVIDED;

    /** Constant value that represents no image was provided for this Publication */
    private static final int NO_IMAGE_PROVIDED = -1;

    public Publication(String nomCreateur, String titreDuPoste, String description, String publicationURL, Date date, int nombreCommentaire, int nombreJaime, int mImageResourceId) {
        this.nomCreateur = nomCreateur;
        this.titreDuPoste = titreDuPoste;
        Description = description;
        this.publicationURL = publicationURL;
        this.date = date;
        this.nombreCommentaire = nombreCommentaire;
        this.nombreJaime = nombreJaime;
        this.mImageResourceId = mImageResourceId;
    }

    public Publication(String nomCreateur, String titreDuPoste, String description, String publicationURL, Date date, int nombreCommentaire, int nombreJaime) {
        this.nomCreateur = nomCreateur;
        this.titreDuPoste = titreDuPoste;
        Description = description;
        this.publicationURL = publicationURL;
        this.date = date;
        this.nombreCommentaire = nombreCommentaire;
        this.nombreJaime = nombreJaime;
    }

    public Publication(String nomCreateur, String titreDuPoste, String description, Date date) {
        this.nomCreateur = nomCreateur;
        this.titreDuPoste = titreDuPoste;
        Description = description;
        this.date = date;
    }

    ///Getters and Setters

    public void setNomCreateur(String nomCreateur) {
        this.nomCreateur = nomCreateur;
    }

    public void setTitreDuPoste(String titreDuPoste) {
        this.titreDuPoste = titreDuPoste;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setPublicationURL(String publicationURL) {
        this.publicationURL = publicationURL;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setNombreCommentaire(int nombreCommentaire) {
        this.nombreCommentaire = nombreCommentaire;
    }

    public void setNombreJaime(int nombreJaime) {
        this.nombreJaime = nombreJaime;
    }

    public void setmImageResourceId(int mImageResourceId) {
        this.mImageResourceId = mImageResourceId;
    }

    public String getNomCreateur() {
        return nomCreateur;
    }

    public String getTitreDuPoste() {
        return titreDuPoste;
    }

    public String getDescription() {
        return Description;
    }

    public String getPublicationURL() {
        return publicationURL;
    }

    public Date getDate() {
        return date;
    }

    public int getNombreCommentaire() {
        return nombreCommentaire;
    }

    public int getNombreJaime() {
        return nombreJaime;
    }

    public int getmImageResourceId() {
        return mImageResourceId;
    }

    public static int getNoImageProvided() {
        return NO_IMAGE_PROVIDED;
    }

    @Override
    public String toString() {
        return "Publication{" +
                "nomCreateur='" + nomCreateur + '\'' +
                ", titreDuPoste='" + titreDuPoste + '\'' +
                ", Description='" + Description + '\'' +
                ", publicationURL='" + publicationURL + '\'' +
                ", date=" + date +
                ", nombreCommentaire=" + nombreCommentaire +
                ", nombreJaime=" + nombreJaime +
                ", mImageResourceId=" + mImageResourceId +
                '}';
    }
}
