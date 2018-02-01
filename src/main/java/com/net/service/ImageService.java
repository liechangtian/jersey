package com.net.service;

import com.net.dao.ImageDao;
import com.net.domain.Image;
import com.net.domain.Images;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 * Created on 2017/11/7 at 21:57.  
 ***/
@Service
public class ImageService {
    @Autowired
    private ImageDao imageDao;


    public ImageService() {
    }

    public Image saveImage(final Image image) {
        return imageDao.store(image);
    }


    public Image updateImage(final String Id, final Image Image) {
        Image.setId(Id);
        imageDao.update(Image);
        return Image;
    }


    public Image getImage(final String id) {
        return imageDao.findById(id);
    }


    public Images getImages() {
        return new Images(imageDao.findAll());
    }


    public boolean deleteImage(final String Id) {
        return imageDao.remove(Id);
    }
}
