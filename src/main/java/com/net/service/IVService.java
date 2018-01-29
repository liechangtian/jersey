package com.net.service;

import com.net.dao.IVDao;
import com.net.dao.ImageDao;
import com.net.dao.VIMDao;
import com.net.domain.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

/***
 * Created on 2017/11/11 at 23:20.  
 ***/
@Service
public class IVService {
    @Autowired
    private IVDao IVDao;
    @Autowired
    private ImageDao imageDao;
    @Autowired
    private VIMDao vimDao;

    public IVService() {
    }

    public Image_VIM saveIV(final Image_VIM image_vim) {
        Image image = imageDao.findById(image_vim.getImageId());
        Vim vim = vimDao.findById(image_vim.getVimId());
        image_vim.setDescription(vim.getName() + ":" + image.getName());
        Image_VIM result = IVDao.store(image_vim);

        String destination = "http://127.0.0.1:8089/rest/books/upload";
        dispatch(image, destination);

        return result;
    }

    public boolean delete(final String mid, final String vid) {
        return IVDao.remove(mid, vid);
    }

    public VIMs getVIMs(final String mid) {
        try {
            return IVDao.findVIMsByMid(mid);
        } catch (final Exception e) {
            return null;
        }
    }

    public Images getImages(final String vid) {
        try {
            return IVDao.findMirrorsByVid(vid);
        } catch (final Exception e) {
            return null;
        }
    }

    public ImageVimPairs getAll() {

        return new ImageVimPairs(IVDao.findAll());

    }

    private void dispatch(Image image, String uri) {
        File file = new File(image.getPosition());
        try {
            HttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(uri);

            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addBinaryBody("file", file)
                    .build();
            httpPost.setEntity(reqEntity);

            HttpResponse response = client.execute(httpPost);
            HttpEntity resEntity = response.getEntity();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
