package com.net.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/***
 * Created on 2017/11/12 at 21:28.
 ***/
@Entity
@Table(name = "package")
public class Package implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final int ID_LENGTH = 20;
    private static final int NAME_LENGTH = 100;
    private static final int POSITION_LENGTH = 200;
    private static final int VERSION_LENGTH = 20;
    private static final int SUPPLIER_LENGTH = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private String name;
    private String position;
    private String version;
    private String supplier;
    private Boolean isReady;
    private Boolean isActive;
    @ElementCollection
    private List<String> imageIds;

    public Package() {
    }

    public Package(String id, String name, String position) {
        this.id = id;
        this.name = name;
        this.position = position;
    }

    public Package(String id, String name, String position, String version, String supplier) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.version = version;
        this.supplier = supplier;
    }

    public Package(String id, String name, String position, String version, String supplier, List imageIds) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.version = version;
        this.supplier = supplier;
        this.imageIds = imageIds;
    }

    public Package(String id, String name, String position, String version, String supplier, Boolean isReady, Boolean isActive, List imageIds) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.version = version;
        this.supplier = supplier;
        this.isReady = isReady;
        this.isActive = isActive;
        this.imageIds = imageIds;
    }


    @Column(unique = true, nullable = false, length = Package.ID_LENGTH, name = "ID")
    public String getId() {
        return id;
    }

    @Column(length = Package.NAME_LENGTH, name = "NAME")
    public String getName() {
        return name;
    }

    @Column(length = Package.POSITION_LENGTH, name = "POSITION")
    public String getPosition() {
        return position;
    }

    @Column(length = Package.VERSION_LENGTH, name = "VERSION")
    public String getVersion() {
        return version;
    }

    @Column(length = Package.SUPPLIER_LENGTH, name = "SUPPLIER")
    public String getSupplier() {
        return supplier;
    }

    @Column(name = "ISREADY")
    public Boolean getIsReady() {
        return isReady;
    }

    @Column(name = "ISACTIVE")
    public Boolean getIsActive() {
        return isActive;
    }

    public List<String> getImageIds() {
        return imageIds;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public void setIsReady(Boolean isReady) {
        this.isReady = isReady;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public void setImageIds(List<String> imageIdList) {
        this.imageIds = imageIdList;
    }

    @Override
    public String toString() {
        return id + ":" + name + ":" + position + ":" + isReady + ":" + isActive;
    }
}