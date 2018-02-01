package com.net.resource;

import com.net.domain.*;
import com.net.domain.Package;
import com.net.service.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/***
 * Created on 2018/1/29 at 0:19.
 ***/

@Path("v1.0/packages")
public class PackageResource {
    @Context
    HttpServletRequest request;
    @Context
    HttpServletResponse response;

    @Autowired
    private ImageService imageService;
    @Autowired
    private IVService IVService;
    @Autowired
    private PackageService packageService;

    //返回测试json对象供untitled接收
    @GET
    @Path("json")
    @Produces(MediaType.APPLICATION_JSON)
    public Image getJson() {
        Image p = new Image("23", "打粉底", "cccc");
        return p;

    }

    //返回NFVO上所有包
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Packages getPackages() {
        final Packages packages = packageService.getPackages();
        return packages;
    }

    //返回指定包
    @GET
    @Path("package")
    @Produces(MediaType.APPLICATION_JSON)
    public Package getPackageById(@QueryParam("id") final String id) {
        final Package aPackage = packageService.getPackage(id);
        return aPackage;
    }

    //返回指定包所依赖镜像在NFVO上的注册情况
    @GET
    @Path("imagesStatus")
    @Produces(MediaType.TEXT_PLAIN)
    public String getImagesStatus(@QueryParam("id") final String id) {
        JSONObject imageStatus = packageService.getImagesStatus(id);
        return imageStatus.toString();
    }

    //返回包就绪状态和激活状态
    @GET
    @Path("statuses")
    @Produces(MediaType.TEXT_PLAIN)
    public String getStatuses(@QueryParam("id") final String id) {
        JSONObject statuses = packageService.getStatuses(id);
        return statuses.toString();
    }

    // 注册包
    // NFVO保存并解析包文件，最后注册至数据库
    @POST
    @Path("register")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Package register() {
        final Package aPackage = packageService.register(request);
        return aPackage;
    }

    //设置包激活状态
    @POST
    @Path("setactive")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Boolean setActive(@FormParam("id") final String id,@FormParam("setActive") final String isActive) {
        return packageService.setIsActive(id,isActive);
    }

    // 删除NFVO上包注册信息及文件
    // 删除前须确认该包未被实例化
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public String deletePackageOnNfvo(@QueryParam("id") final String id) {
        if (packageService.deletePackage(id)) {
            return "Success! Deleted Package id=" + id;
        } else {
            return "Failed!";
        }
    }
}