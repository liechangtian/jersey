//@ sourceURL=index.js
/**
 * Created by 王帅 on 2017/10/30.
 */

//标签切换
$(document).ready(function () {
    $('.ct:gt(0)').hide()
    $('.box ul li').click(function () {
        $(this).addClass("one")
            .siblings().removeClass();
        //获取当前标签的索引值
        // var content_idnex = $('.box ul li').index(this);
        var content_index = $(this).index();
        $('.ct').eq(content_index).show()
            .siblings().hide();
    });

});

var bookRestUrl = 'rest/images';
function rest(restUrl, httpMethod, param, contenttype, datatype, callback) {
    var request = jQuery.ajax({
        type: httpMethod,
        url: restUrl,
        data: param,
        contentType: contenttype,
        dataType: datatype
    });
    request.done(function (data) {
        try {
            if (data === null || data === undefined) {
                jQuery('#resultDiv').html("No result from server");
            } else {
                callback(data);
            }
        } catch (e) {
            jQuery('#resultDiv').html(e);
        }
    });
    request.fail(function (textStatus, errorThrown) {
        jQuery('#resultDiv').html(errorThrown + " status=" + textStatus.status);
    });
}

/*删除镜像*/
function deleteImage() {
    var xmlhttp;
    var id = $("#deleteImageId").val();
    if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp = new XMLHttpRequest();
    }
    else {// code for IE6, IE5
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange = function () {
        if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
            document.getElementById("deleteImageResult").innerHTML = xmlhttp.responseText;
        }
    }
    xmlhttp.open("DELETE", "rest/images/delete?id=" + id, true);
    xmlhttp.send();
}

/*删除VIM*/
function deleteVim() {
    var xmlhttp;
    var id=$("#deleteVimId").val();
    if (window.XMLHttpRequest)
    {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp=new XMLHttpRequest();
    }
    else
    {// code for IE6, IE5
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange=function()
    {
        if (xmlhttp.readyState==4 && xmlhttp.status==200)
        {
            document.getElementById("deleteVimResult").innerHTML=xmlhttp.responseText;
        }
    }
    xmlhttp.open("DELETE","rest/vims/deletevim?id="+id,true);
    xmlhttp.send();
}
/*删除VIM中镜像*/
function deleteImageInVim() {
    var xmlhttp;
    var vId=$("#deleteImageInVimId").val();
    var iId=$("#deleteImageIdInVim").val();
    if (window.XMLHttpRequest)
    {// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp=new XMLHttpRequest();
    }
    else
    {// code for IE6, IE5
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange=function()
    {
        if (xmlhttp.readyState==4 && xmlhttp.status==200)
        {
            document.getElementById("deleteImageInVimResult").innerHTML=xmlhttp.responseText;
        }
    }
    xmlhttp.open("DELETE","rest/vims/deleteimageinvim?vid="+vId+"&iid="+iId,true);
    xmlhttp.send();
}



// 文件上传
$(".ct1 #file").on("change", function () {
    var formData = new FormData();
    formData.append("file", $(".ct1 #file")[0].files);
    // formData.append("token", $("#token").val());
    $.ajax({
        url: "http://localhost:8080/rest/images/upload",
        type: "POST",
        data: formData,
        processData: false,
        contentType: false,
        success: function (response) {
            // 根据返回结果指定界面操作
        }
    });
});
// 文件上传
// $("#file").on("change", function () {
//     var formData = new FormData();
//     formData.append("file", $("#file")[0].files);
//     // formData.append("token", $("#token").val());
//     $.ajax({
//         url: "http://localhost:8080/rest/images/upload",
//         type: "POST",
//         data: formData,
//         processData: false,
//         contentType: false,
//         success: function (response) {
//             // 根据返回结果指定界面操作
//         }
//     });
// });