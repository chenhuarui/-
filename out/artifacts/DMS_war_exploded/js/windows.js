

//点击div显示div
//弹窗
$(document).ready(function(){
    $("#fileAdd").click(function() {
        $(".addFile").toggle(1000);
        document.getElementById("file").value = document.getElementById("path").value;
    });
    $("#folderAdd").click(function() {
        $(".addFolder").toggle(1000);
        document.getElementById("folder").value = document.getElementById("path").value;
    });
    $("#uploadFile").click(function() {
        $(".upload").toggle(1000);
        document.getElementById("uploadPage").value = document.getElementById("path").value;
    });
    $("#query").click(function() {
        $(".queryPage").toggle(1000);
    });
});

//取消:隐藏弹框
function cancel(e) {
    switch (e){
        case 1:$('#addFolder').css('display','none');break;
        case 2:$('#addFile').css('display','none');break;
        case 3:$('#upload').css('display','none');break;
        case 4:$('#query-Page').css('display','none');break;
        case 5:$('#file-updateName').css('display','none');break;
        case 6:$('#file-attribute').css('display','none');break;
        case 7:$('#folder-attribute').css('display','none');break;
        case 8:$('#folder_updateName').css('display','none');break;
    }
}

// 新建文件夹
$(function () {
    $("#addFolder_submit").click(function () {
        var url = "add_Folder.action";

        $.ajax({
            url:url,
            type:"POST",
            data: $('#folderForm').serialize(),
            success:function (data) {
                if(data==1)
                    alert("该文件夹名称已被占用！！");
                else if(data==2)
                    alert("文件夹名不能为空！！");
                else {
                    $('#addFolder').css('display', 'none');
                    $(".right").html(data);
                }
            }
        });
        return false;
    }) ;
});


// 新建文件
$(function () {
    $("#addFile_submit").click(function () {
        var url = "add_File.action";

        $.ajax({
            url:url,
            type:"POST",
            data: $('#file-form').serialize(),
            success:function (data) {
                if(data==1)
                    alert("此位置已经包含同名文件！！");
                else if(data==2)
                    alert("文件名不能为空！！");
                else {
                    $('#addFile').css('display','none');
                    $(".right").html(data);
                }
            }
        });
        return false;
    }) ;
});


//上传文件
$(function () {
    $("#upload_submit").click(function () {

       var formData = new FormData();
       formData.append("uploadFile.file",document.getElementById("file1").files[0]);
       formData.append("fileEntity.fileName",document.getElementById("fileTheme").value);
       formData.append("fileEntity.keyword",document.getElementById("keyword").value);

       $.ajax({
           url:"upload.action",
           type:"POST",
           data:formData,

           //false才会自动加上正确的Content-Type
           contentType:false,

           //false才会避开jQuery对formdata的默认处理
           //XMlHttpRequest会对formdata进行正确处理
           processData:false,
           cache:false,
           success:function (data) {
                   alert("上传成功！");
                   $('#upload').css('display','none');
                   $(".right").html(data);
           },
           error:function () {
               alert("上传失败！");
           }
       });
        return false;
    });
});



//根据你选择的部门->出现对应的人员
function changePerson(e) {

    if(e==0) {
        alert("请选择部门");

    }else {

        $.ajax({
            url:"getPerson.action?user.department="+e,
            type:"GET",
            dataType:"JSON",
            success:function (data) {
                var str = "";

                for(var resCode in data) {
                    var resName = data[resCode];
                    str += "<option value\""+ resCode+"\">"+resName+"</option>";
                }
                $("#resGroupList").html("<option value=\"-1\">请选择</option>"+str);
            }
        });
    }
}


//查询
$(function () {
    $("#query_submit").click(function () {

        $.ajax({
            url: "query_File.action",
            type:"POST",
            data: $('#query-Form').serialize(),
            success:function (data) {
                $('#query-Form').css('display','none');
                $(".right").html(data);
            },
            error:function () {
                alert("异常！请重新填写！");
            }
        });
        return false;
    });
});


