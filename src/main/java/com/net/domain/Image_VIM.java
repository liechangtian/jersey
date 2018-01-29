package com.net.domain;

import javax.persistence.*;
import java.io.Serializable;

/***
 * Created on 2017/11/11 at 23:05.  
 ***/
@Entity
@Table(name = "image_vim")
@IdClass(IVPK.class)
public class Image_VIM implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final int ID_LENGTH = 20;
    private static final int DESC_LENGTH = 200;

    private String imageId;
    private String vimId;
    private String description;

    public Image_VIM() {
    }

    public Image_VIM(String imageId, String vimId) {
        this.imageId = imageId;
        this.vimId = vimId;
    }

    public Image_VIM(String imageId, String vimId, String description) {
        this.imageId = imageId;
        this.vimId = vimId;
        this.description = description;
    }


    @Id
    @Column(nullable = false, length = Image_VIM.ID_LENGTH, name = "IMAGEID")
    public String getImageId() {
        return imageId;
    }

    @Id
    @Column(nullable = false, length = Image_VIM.ID_LENGTH, name = "VIMID")
    public String getVimId() {
        return vimId;
    }

    @Column(length = Image_VIM.DESC_LENGTH, name = "DESCRIPRION")
    public String getDescription() {
        return description;
    }


    public void setImageId(String i) {
        imageId = i;
    }

    public void setVimId(String n) {
        vimId = n;
    }

    public void setDescription(String n) {
        description = n;
    }

    @Override
    public String toString() {
        return imageId + ":" + vimId;
    }
}
