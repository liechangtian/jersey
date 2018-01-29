package com.net.domain;

import java.io.Serializable;
import java.util.List;

/***
 * Created on 2017/11/11 at 21:38.  
 ***/
public class VIMs implements Serializable {
    private static final long serialVersionUID = -5070487415443208853L;
    private List<Vim> vimList;

    public VIMs() {
        super();
    }

    public VIMs(final List<Vim> vimList) {
        super();
        this.vimList = vimList;
    }

    public List<Vim> getVIMList() {
        return vimList;
    }

    public void setVIMList(final List<Vim> VimList) {
        this.vimList = vimList;
    }


    @Override
    public String toString() {
        return "{" + vimList + "}";
    }
}
