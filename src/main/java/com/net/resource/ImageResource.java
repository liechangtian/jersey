package com.net.resource;

import com.net.domain.*;
import com.net.service.IVService;
import com.net.service.ImageService;
import com.net.service.VIMService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.nio.file.Paths;
import java.util.List;

@Path("images")
public class ImageResource {
    @Context
    HttpServletRequest request;
    @Context
    HttpServletResponse response;

    @Autowired
    private ImageService imageService;
//    @Autowired
//    private VIMService vimService;
    @Autowired
    private IVService IVService;

    //返回测试json对象供untitled接收
    @GET
    @Path("json")
    @Produces(MediaType.APPLICATION_JSON)
    public Image getJson() {
        Image p = new Image("23", "打粉底", "cccc");
        return p;

    }

    //返回NFVO上所有镜像
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Images getMirrors() {
        final Images images = imageService.getImages();
        return images;
    }

    //返回指定镜像
    @GET
    @Path("image")
    @Produces(MediaType.APPLICATION_JSON)
    public Image getImageByQuery(@QueryParam("id") final String Id) {
        final Image image = imageService.getImage(Id);
        return image;
    }

    //指定VIM上的所有镜像
    @GET
    @Path("onvim")
    @Produces(MediaType.APPLICATION_JSON)
    public Images getMirrors(@QueryParam("vimid") final String vimid) {
        final Images images = IVService.getImages(vimid);
        return images;

    }

    //拥有指定镜像的所有VIM
    @GET
    @Path("vims")
    @Produces(MediaType.APPLICATION_JSON)
    public VIMs getVIMs(@QueryParam("imageid") final String imageid) {
        final VIMs vims = IVService.getVIMs(imageid);
        return vims;
    }

    //所有VIM中所有镜像信息
    @GET
    @Path("ivpairs")
    @Produces(MediaType.APPLICATION_JSON)
    public ImageVimPairs getPairs() {
        final ImageVimPairs imageVimPairs = IVService.getAll();
        return imageVimPairs;
    }

    //NFVO保存镜像文件并注册至数据库
    @POST
    @Path("register")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Image upLoad() throws Exception {
        //在项目路径下设置imagefiles文件夹作为路径
        String upload_file_path = request.getSession().getServletContext().getRealPath("/") + "imagefiles/";

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
        //中文文件名处理
        upload.setHeaderEncoding("utf-8");
        String fileName = new String();
        String imageId = new String();
        String imageName = new String();
        List<FileItem> list = upload.parseRequest(request);
        for (FileItem item : list) {
            if (item.isFormField()) {
                String name = item.getFieldName();
                String value = item.getString("utf-8");
                if (name.equals("id"))
                    imageId = value;
                else if (name.equals("name"))
                    imageName = value;
                item.delete();
                request.setAttribute(name, value);
            } else {
                String name = item.getFieldName();
                String value = item.getName();
                System.out.println(name);
                System.out.println(value);

                fileName = Paths.get(value).getFileName().toString();
                request.setAttribute(name, fileName);
                // 写文件到path目录，文件名为filename
                item.write(new File(upload_file_path, fileName));
            }
        }
        Image image = new Image(imageId, imageName, upload_file_path + fileName);
        return imageService.saveImage(image);
    }

    //将镜像下发至VIM，并在NFVO注册下发信息
    @POST
    @Path("dispatch")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Image_VIM register(@FormParam("mid") final String mid, @FormParam("vid") final String vid) {
        return IVService.saveIV(new Image_VIM(mid, vid));
    }

    //删除NFVO上镜像注册信息及文件
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteImageOnNfvo(@QueryParam("id") final String Id) {
        if (imageService.deleteImage(Id)) {
            return "Success! Deleted Image id=" + Id;
        } else {
            return "Failed!";
        }
    }

//    下发文件
    @POST
    @Path("distribute")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String distribute(@FormParam("fileName") final String fileName,
                             @FormParam("destination") final String destination) {
        String result = new String();
        String upload_file_path = request.getSession().getServletContext().getRealPath("/") + "imagefiles/";

        String source = "home/ws/files/" + fileName;
        try {
            HttpClient client = HttpClients.createDefault();
            HttpPost post = new HttpPost(destination);
            HttpEntity entity = MultipartEntityBuilder.create()
                    .addBinaryBody("file", new File(source))
                    .build();
            post.setEntity(entity);
            HttpResponse response = client.execute(post);
            HttpEntity resEntity = response.getEntity();
            result = EntityUtils.toString(resEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //更新镜像信息
//    @PUT
//    @Path("refresh")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Image updateMirror(@QueryParam("id") final String id, final Image image) {
//        if (image == null) {
//            return null;
//        }
//        return imageService.updateImage(id, image);
//    }

}