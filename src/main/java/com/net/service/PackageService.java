package com.net.service;

import com.net.dao.IVDao;
import com.net.dao.ImageDao;
import com.net.dao.PackageDao;
import com.net.dao.VIMDao;
import com.net.domain.Image;
import com.net.domain.Package;
import com.net.domain.Packages;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c) 2015 XiaoMi Inc. All Rights Reserved.
 * Authors: Wangshuai <wangshuai16@xiaomi.com> .
 * Date: 18-1-29.
 */
@Service
public class PackageService {
    @Autowired
    private ImageDao imageDao;
    @Autowired
    private PackageDao packageDao;
    @Autowired
    private VIMDao vimDao;
    @Autowired
    private IVDao ivDao;

    public PackageService() {
    }

    public Package register(final HttpServletRequest request) {
        try {
            //在项目路径下设置packageFiles文件夹作为路径
            String upload_file_path = request.getSession().getServletContext().getRealPath("/") + "packageFiles/";
            if (!Paths.get(upload_file_path).toFile().exists()) {
                Paths.get(upload_file_path).toFile().mkdirs();
            }
            System.out.println(upload_file_path);
            // 设置工厂
            DiskFileItemFactory factory = new DiskFileItemFactory();
            // 设置文件存储位置
            factory.setRepository(Paths.get(upload_file_path).toFile());
            // 设置大小，如果文件小于设置大小的话，放入内存中，如果大于的话则放入磁盘中,单位是byte
            factory.setSizeThreshold(0);

            ServletFileUpload upload = new ServletFileUpload(factory);
            // 中文文件名处理
            upload.setHeaderEncoding("utf-8");
            // 保存包文件
            String fileName = new String();
            String packageId = new String();
            String packageName = new String();
            List<FileItem> list = upload.parseRequest(request);
            for (FileItem item : list) {
                if (item.isFormField()) {
                    String name = item.getFieldName();
                    String value = item.getString("utf-8");
                    if (name.equals("id"))
                        packageId = value;
                    else if (name.equals("name"))
                        packageName = value;
                    item.delete();
                    request.setAttribute(name, value);
                } else {
                    String name = item.getFieldName();
                    String value = item.getName();
                    System.out.println(name);
                    System.out.println(value);

                    fileName = Paths.get(value).getFileName().toString();
                    request.setAttribute(name, fileName);
                    // 写文件到path目录，文件名问filename
                    item.write(new File(upload_file_path, fileName));
                }
            }
            // 解析包文件
            File file = new File(upload_file_path + fileName);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String content = br.readLine();
            JSONObject json = new JSONObject(content);
            // 如果文件内容格式正常，则注册该包并返回
            if (json.has("version") && json.has("supplier") && json.has("imageIdList")) {
                // imageIdList为字符串，imageId以','分割
                String[] imageIds = json.getString("imageIdList").split(",");
                List<String> imageIdList = new ArrayList<>();
                for (String s : imageIds)
                    imageIdList.add(s);
                boolean isReady = isReady(packageId);
                Package aPackage = new Package(packageId, packageName, upload_file_path + fileName,
                        json.getString("version"), json.getString("supplier"), isReady, false, imageIdList);
                return savePackage(aPackage);
            }
            // 删除包文件
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 是否就绪
    public Boolean isReady(final String packageId) {
        Package aPackage = packageDao.findById(packageId);
        if (aPackage != null) {
            updateIsReady(packageId);
            return aPackage.getIsReady();
        }
        return false;
    }

    // 是否激活
    public Boolean isActive(final String packageId) {
        Package aPackage = packageDao.findById(packageId);
        if (aPackage != null)
            return aPackage.getIsActive();
        return false;
    }

    // 生成并刷新就绪状态
    public JSONObject updateIsReady(final String packageId) {
        Package aPackage = packageDao.findById(packageId);
        if (aPackage != null) {
            // 键为镜像id，值为该镜像在NFVO注册情况
            JSONObject isImagesReady = new JSONObject();
            for (String imageId : aPackage.getImageIds()) {
                if (imageDao.findById(imageId) != null)
                    isImagesReady.put(imageId, true);
                else
                    isImagesReady.put(imageId, false);
            }
            Boolean isReady = true;
            for (String imageId : isImagesReady.keySet())
                if (!isImagesReady.getBoolean(imageId)) {
                    isReady = false;
                    break;
                }
            packageDao.setIsReady(packageId, isReady);
            return isImagesReady;
        }
        return null;
    }

    public Boolean setIsActive(final String packageId, final String isActive) {
        Package aPackage = packageDao.findById(packageId);
        if (aPackage != null) {
            if (isActive != null)
                packageDao.setIsActive(packageId, true);
            else
                packageDao.setIsActive(packageId, false);
            // aPackage此时已过期，故须重新执行findById()
            return packageDao.findById(packageId).getIsActive();
        }
        return false;

    }

    public Package savePackage(final Package aPackage) {
        return packageDao.store(aPackage);
    }


    public Package getPackage(final String packageId) {
        return packageDao.findById(packageId);
    }

    public Packages getPackages() {
        return new Packages(packageDao.findAll());
    }

    public JSONObject getImagesStatus(final String packageId) {
        Package aPackage = packageDao.findById(packageId);
        if (aPackage != null) {
            return updateIsReady(packageId);
        }
        return null;
    }

    public JSONObject getStatuses(final String packageId) {
        JSONObject statuses=new JSONObject();
        Package aPackage = packageDao.findById(packageId);
        if (aPackage != null) {
            updateIsReady(packageId);
            boolean isReady=aPackage.getIsReady();
            boolean isActive=aPackage.getIsActive();
            statuses.put("isReady",isReady);
            statuses.put("isActive",isActive);
            statuses.put("Deployable",isReady&&isActive);
            return statuses;
        }
        return null;
    }

    public boolean deletePackage(final String Id) {
        return packageDao.remove(Id);
    }
}
