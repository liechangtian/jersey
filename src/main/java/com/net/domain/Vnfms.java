package com.net.domain;

import java.io.Serializable;
import java.util.List;

/***
 * Created on 2017/11/12 at 21:27.  
 ***/
public class Vnfms implements Serializable {
    private static final long serialVersionUID = -5070487415443208853L;
    private List<Vnfm> vnfmList;

    public Vnfms() {
        super();
    }

    public Vnfms(final List<Vnfm> vnfmList) {
        super();
        this.vnfmList = vnfmList;
    }

    public List<Vnfm> getVnfmList() {
        return vnfmList;
    }

    public void setVnfmList(final List<Vnfm> vnfmList) {
        this.vnfmList = vnfmList;
    }


    @Override
    public String toString() {
        return "{" + vnfmList + "}";
    }
}
