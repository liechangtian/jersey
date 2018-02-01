package com.net.service;

import com.net.dao.IVDao;
import com.net.dao.VIMDao;
import com.net.domain.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/***
 * Created on 2017/11/11 at 21:25.  
 ***/
@Service
public class VIMService {
    @Autowired
    private VIMDao vimDao;
    @Autowired
    private IVDao ivDao;

    public VIMService() {
    }

    public Vim saveVIM(final Vim vim) {
        return vimDao.store(vim);
    }


    public Vim updateVIM(final String Id, final Vim vim) {
        vim.setId(Id);
        vimDao.update(vim);
        return vim;
    }


    public Vim getVIM(final String Id) {
        try {
            return vimDao.findById(Id);
        } catch (final Exception e) {
            return null;
        }
    }

    public VIMs getVIMs() {
        return new VIMs(vimDao.findAll());
    }


    //删除相关IV关系，然后删除VIM
    public boolean deleteVIM(final String vimId) {
        Vim vim = getVIM(vimId);
        if (vim == null) return false;
        String url = vim.getUrl();
        if (url.lastIndexOf('/') != (url.length() - 1))
            url += '/';
        //释放VIM资源
        try {
            HttpClient client = HttpClients.createDefault();
            HttpDelete delete = new HttpDelete(url + "rest/resources/delete?id=" + vimId);
            client.execute(delete);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Images images = ivDao.findMirrorsByVid(vimId);
        for (Image image : images.getImageList())
            ivDao.remove(image.getId(), vimId);
        return vimDao.remove(vimId);
    }

    //删除相关IV关系
    public boolean deleteImageInVim(final String vimId, final String imageId) {
        return ivDao.remove(imageId, vimId);
    }


}
