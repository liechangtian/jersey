<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>NFVO管理平台</title>
  <script src="webjars/jquery/2.0.3/jquery.js"></script>
  <script src="js/index.js"></script>
  <style type="text/css">
    * {
      padding: 3px;
      margin: 3px;
    }

    ul {
      list-style-type: none
    }

    .box ul {
      height: 25px;
      line-height: 25px;
      margin-left: 2%;
      list-style-type: none;
    }

    /*position:relative;用于IE7上面*/
    .box ul li {
      float: left;
      border: 1px solid #9CF;
      margin-right: 5px;
      padding: 0 10px;
      border-bottom: none;
      position: relative;
      cursor: pointer;
      background: #E4F1FC
    }

    #content {
      width: 97%;
      height: 70%;
      border: 1px solid #9CF;
      padding: 10px;
      margin-left: 1%;
      margin-top: -2px;
      position: relative;
    }

    /*选项卡颜色变换*/
    .box ul li.one {
      background: #FFF;
    }

    .level2 {
      position: relative;
      margin-left: 2em;
    }
  </style>
</head>

<body>
<h2>NFVO 管理平台</h2>
<div class="box" id="tab">
  <ul>
    <li class="one">镜像管理</li>
    <li>VNF包管理</li>
    <li>VNF管理</li>
    <li>VNFM管理</li>
    <li>VIM管理</li>
    <li>信息查询</li>
  </ul>
</div>

<div id="content">
  <%--菜单一的内容--%>
  <div class="ct ct1">
    (1)镜像上载与注册：<br/>
    <div class="level2">
      镜像上载并注册至NFVO：<br/>
      <form method="post" action="rest/images/register" enctype="multipart/form-data"
            target="_blank">
        <input name="id" type="text" placeholder="请输入镜像ID:" style="width:300px"/><br/>
        <input name="name" type="text" placeholder="请输入镜像名称:" style="width:300px"/><br/>
        <input name="file" type="file" value="选择镜像文件"/>
        <input type="submit" value="提交"/><br/><br/>
      </form>
      <%--未完成下发功能--%>
      下发镜像至VIM：<br/>
      <form method="post" action="rest/images/dispatch" enctype="application/x-www-form-urlencoded"
            target="_blank">
        <input name="mid" type="text" placeholder="请输入镜像ID:" style="width:300px"/><br/>
        <input name="vid" type="text" placeholder="请输入VIM ID:" style="width:300px"/><br/>
        <input type="submit" value="提交"/><br/><br/>
      </form>
    </div>

    (2)查询镜像：<br/>
    <div class="level2">
      <form method="get" action="rest/images" target="_blank">
        NFVO上的所有镜像：
        <br/>
        <input type="submit" value="查询"/><br/><br/>
      </form>
      <form method="get" action="rest/images/image" target="_blank">
        NFVO上的指定镜像：
        <br/>
        <input name="id" type="text" placeholder="请输入镜像ID:" style="width:300px"/><br/>
        <input type="submit" value="查询"/><br/><br/>
      </form>
      <form method="get" action="rest/images/ivpairs" target="_blank">
        所有VIM上的所有镜像：<br/>
        <input type="submit" value="查询"/><br/><br/>
      </form>
      <form method="get" action="rest/images/onvim" target="_blank">
        指定VIM上的所有镜像：<br/>
        <input name="vimid" type="text" placeholder="请输入VIM ID:" style="width:300px"/><br/>
        <input type="submit" value="查询"/><br/><br/>
      </form>
      <form method="get" action="rest/images/vims" target="_blank">
        拥有指定镜像的所有VIM：<br/>
        <input name="imageid" type="text" placeholder="请输入镜像ID:" style="width:300px"/><br/>
        <input type="submit" value="查询"/><br/><br/>
      </form>
    </div>
    <%--仅删除了镜像记录，未删除镜像文件，未删除iv关系对，待添加--%>
    <%--删除可分为文件删除和注册删除--%>
    (3)删除镜像：<br/>
    <div class="level2">
      <form>
        <input id="deleteImageId" name="id" type="text" placeholder="请输入镜像ID:" style="width:300px"/><br/>
        <button type="button" onclick="deleteImage()">删除</button>
        <span id="deleteImageResult"></span>
      </form>
    </div>
  </div>

  <%--菜单二的内容--%>
  <div class="ct">VNF包管理<br/>
    <div class="level2">

    </div>
  </div>
  <%--菜单三的内容--%>
  <div class="ct">VNF管理<br/>
    <div class="level2">

    </div>
  </div>
  <%--菜单四的内容--%>
  <div class="ct">VNFM管理<br/>
    <div class="level2">

    </div>
  </div>

  <%--菜单五的内容--%>
  <div class="ct">
    (1)VIM注册与镜像获取：<br/>
    <div class="level2">
      注册VIM<br/>
      <form method="post" action="http://localhost:8080/rest/vims/register"
            enctype="application/x-www-form-urlencoded"
            target="_blank">
        <input name="id" type="text" placeholder="请输入VIM ID:" style="width:300px"/><br/>
        <input name="name" type="text" placeholder="请输入VIM名称:" style="width:300px"/><br/>
        <input name="url" type="text" placeholder="请输入VIM地址:" style="width:300px"/><br/>
        <input type="submit" value="提交"/><br/><br/>
      </form>
      <%--未完成下发功能--%>
      下发镜像至VIM：<br/>
      <form method="post" action="rest/images/dispatch" enctype="application/x-www-form-urlencoded"
            target="_blank">
        <input name="mid" type="text" placeholder="请输入镜像ID:" style="width:300px"/><br/>
        <input name="vid" type="text" placeholder="请输入VIM ID:" style="width:300px"/><br/>
        <input type="submit" value="提交"/><br/><br/>
      </form>
    </div>

    (2)查询VIM：<br/>
    <div class="level2">
      <form method="get" action="rest/vims" target="_blank">
        所有VIM：
        <br/>
        <input type="submit" value="查询"/><br/><br/>
      </form>
      <form method="get" action="rest/vims/vim" target="_blank">
        指定VIM：
        <br/>
        <input name="id" type="text" placeholder="请输入VIM ID:" style="width:300px"/><br/>
        <input type="submit" value="查询"/><br/><br/>
      </form>
      <form method="get" action="rest/images/ivpairs" target="_blank">
        所有VIM上的所有镜像：<br/>
        <input type="submit" value="查询"/><br/><br/>
      </form>
      <form method="get" action="rest/images/onvim" target="_blank">
        指定VIM上的所有镜像：<br/>
        <input name="vimid" type="text" placeholder="请输入VIM ID:" style="width:300px"/><br/>
        <input type="submit" value="查询"/><br/><br/>
      </form>
      <form method="get" action="rest/images/vims" target="_blank">
        拥有指定镜像的所有VIM：<br/>
        <input name="imageid" type="text" placeholder="请输入镜像ID:" style="width:300px"/><br/>
        <input type="submit" value="查询"/><br/><br/>
      </form>
    </div>
    (3)删除VIM与镜像：<br/>
    <div class="level2">
      删除VIM：<br/>
      <form>
        <input id="deleteVimId" name="id" type="text" placeholder="请输入VIM ID:" style="width:300px"/><br/>
        <button type="button" onclick="deleteVim()">删除</button>
        <span id="deleteVimResult"></span><br/><br/>
      </form>
      删除VIM内镜像：<br/>
      <form>
        <input id="deleteImageInVimId" name="id" type="text" placeholder="请输入VIM ID:" style="width:300px"/><br/>
        <input id="deleteImageIdInVim" name="id" type="text" placeholder="请输入镜像 ID:" style="width:300px"/><br/>
        <button type="button" onclick="deleteImageInVim()">删除</button>
        <span id="deleteImageInVimResult"></span>
      </form>
    </div>
  </div>

  <%--菜单六的内容--%>
  <div class="ct">信息查询<br/>

    <form method="post" action="http://localhost:8080/rest/images/upload" enctype="multipart/form-data"
          target="_blank">
      (1)镜像上载与注册：<br/>
      <input name="id" type="text" placeholder="请输入镜像ID:" style="width:300px"/><br/>
      <input name="name" type="text" placeholder="请输入镜像名称:" style="width:300px"/><br/>
      <input name="file" type="file" value="选择镜像文件"/>
      <input type="submit" value="提交"/><br/><br/>
    </form>
    (2)查询镜像：<br/>
    <form method="get" action="rest/images" target="_blank">
      <input type="submit" value="所有镜像"/><br/><br/>
    </form>
    <form method="get" action="rest/images" target="_blank">
      <input name="vid" type="text" placeholder="请输入VIM ID:" style="width:300px"/><br/>
      <input type="submit" value="所有镜像"/><br/><br/>
    </form>


    <p><a href="rest/images" target="_blank">default</a></p>
    <p><a href="rest/images/bb" target="_blank">bb</a></p>
    <p><a href="rest/images/json" target="_blank">json</a></p>
  </div>
</div>

</body>
</html>