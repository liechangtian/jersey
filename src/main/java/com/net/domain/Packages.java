package com.net.domain;

import java.io.Serializable;
import java.util.List;

/***
 * Created on 2017/11/12 at 21:53.  
 ***/
public class Packages implements Serializable{
    private static final long serialVersionUID = -5070487415443208853L;
    private List<Package> packageList;

    public Packages() {
        super();
    }

    public Packages(final List<Package> packageList) {
        super();
        this.packageList = packageList;
    }

    public List<Package> getPackageList() {
        return packageList;
    }

    public void setPackageList(final List<Package> packageList) {
        this.packageList = packageList;
    }

    @Override
    public String toString() {
        return "{" + packageList + "}";
    }
}
