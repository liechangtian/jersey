package com.net.domain;

import java.io.Serializable;
import java.util.List;

/***
 * Created on 2017/11/7 at 22:04.  
 ***/
public class Images implements Serializable {
    private static final long serialVersionUID = -5070487415443208853L;
    private List<Image> imageList;

    public Images() {
        super();
    }

    public Images(final List<Image> imageList) {
        super();
        this.imageList = imageList;
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public void setImageList(final List<Image> imageList) {
        this.imageList = imageList;
    }


    @Override
    public String toString() {
        return "{" + imageList + "}";
    }
}
