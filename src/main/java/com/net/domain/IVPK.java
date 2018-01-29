package com.net.domain;

import java.io.Serializable;

/***
 * Created on 2017/11/11 at 23:11.  
 ***/

//复合主键
public class IVPK implements Serializable {
    private static final long serialVersionUID = 1L;

    private String mid;
    private String vid;
    public IVPK(){
    }

    public String getImageId(){
        return this.mid;
    }
    public String getVimId(){
        return this.vid;
    }
    public void setImageId(String mid){
        this.mid=mid;
    }
    public void setVimId(String vid){
        this.vid=vid;
    }
    @Override
    public boolean equals(Object o) {
        if(o instanceof IVPK){
            IVPK key = (IVPK) o ;
            if(this.mid == key.getImageId() && this.vid.equals(key.getVimId())){
                return true ;
            }
        }
        return false ;
    }

    @Override
    public int hashCode() {
        return this.mid.hashCode();
    }
}
