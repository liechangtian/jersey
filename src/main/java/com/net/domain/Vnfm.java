package com.net.domain;

import javax.persistence.*;
import java.io.Serializable;

/***
 * Created on 2017/11/12 at 21:27.  
 ***/
@Entity
@Table(name = "vnfm")
public class Vnfm implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final int ID_LENGTH = 20;
    private static final int NAME_LENGTH = 100;

    private String id;
    private String name;

    public Vnfm() {
    }

    public Vnfm(String id, String name) {
        this.id = id;
        this.name = name;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false, length = Vnfm.ID_LENGTH, name = "ID")
    public String getId() {
        return id;
    }

    @Column(length = Vnfm.NAME_LENGTH, name = "NAME")
    public String getName() {
        return name;
    }

    public void setId(String i) {
        id = i;
    }

    public void setName(String n) {
        name = n;
    }

    @Override
    public String toString() {
        return id + ":" + name;
    }
}
