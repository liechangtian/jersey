package com.net.domain;

import java.util.List;

/***
 * Created on 2017/11/12 at 20:39.  
 ***/
public class ImageVimPairs {
    private static final long serialVersionUID = -5070487415443208853L;
    private List<Image_VIM> imageVimList;

    public ImageVimPairs() {
        super();
    }

    public ImageVimPairs(final List<Image_VIM> imageVimList) {
        super();
        this.imageVimList = imageVimList;
    }

    public List<Image_VIM> getList() {
        return imageVimList;
    }

    public void setList(final List<Image_VIM> imageVimList) {
        this.imageVimList = imageVimList;
    }


    @Override
    public String toString() {
        return "{" + imageVimList + "}";
    }
}
