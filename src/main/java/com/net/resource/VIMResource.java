package com.net.resource;

import com.net.domain.*;
import com.net.service.IVService;
import com.net.service.ImageService;
import com.net.service.VIMService;
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

/***
 * Created on 2017/11/12 at 0:28.  
 ***/
@Path("v1.0/vims")
public class VIMResource {
    @Context
    HttpServletRequest request;
    @Context
    HttpServletResponse response;

    @Autowired
    private ImageService imageService;
    @Autowired
    private VIMService vimService;
    @Autowired
    private IVService IVService;

    //返回NFVO上注册的所有VIM
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public VIMs getVIMs() {
        final VIMs viMs = vimService.getVIMs();
        return viMs;
    }

    //返回指定VIM
    @GET
    @Path("vim")
    @Produces(MediaType.APPLICATION_JSON)
    public Vim getVimByQuery(@QueryParam("id") final String id) {
        final Vim vim = vimService.getVIM(id);
        return vim;
    }

    //注册VIM
    @POST
    @Path("register")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Vim saveMirror(@FormParam("id") final String id, @FormParam("name") final String name, @FormParam("url") final String url) {
        final Vim vim = new Vim(id, name,url);
        return vimService.saveVIM(vim);
    }


    //删除NFVO上注册的VIM及相关镜像下发信息（VIM中镜像如何删除待确定）
    @DELETE
    @Path("deletevim")
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteVim(@QueryParam("id") final String Id) {
        //清理VIM注册信息
        if (vimService.deleteVIM(Id)) {
            return "Success! Deleted VIM id=" + Id;
        } else {
            return "Failed!";
        }
    }

    //删除指定VIM中的指定镜像（VIM中镜像如何删除待确定）
    @DELETE
    @Path("deleteimageinvim")
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteImageInVim(@QueryParam("vid") final String vId,@QueryParam("iid") final String iId) {
        if (vimService.deleteImageInVim(vId,iId)) {
            return "Success! Deleted Image id=" + iId+" in VIM id="+vId;
        } else {
            return "Failed!";
        }
    }

}
