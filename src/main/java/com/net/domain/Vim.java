package com.net.domain;

import javax.persistence.*;
import java.io.Serializable;

/***
 * Created on 2017/11/9 at 0:43.  
 ***/
@Entity
@Table(name = "vim")
public class Vim implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final int ID_LENGTH = 20;
    private static final int NAME_LENGTH = 100;
    private static final int URL_LENGTH = 300;

    private String id;
    private String name;
    private String url;

    public Vim() {
    }

    public Vim(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Vim(String id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false, length = Vim.ID_LENGTH, name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String i) {
        id = i;
    }

    @Column(length = Vim.NAME_LENGTH, name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String n) {
        name = n;
    }

    @Column(length = Vim.URL_LENGTH, name = "URL")
    public String getUrl() {
        return url;
    }

    public void setUrl(String u) {
        url = u;
    }

    @Override
    public String toString() {
        return id + ":" + name + ":" + url;
    }
}
