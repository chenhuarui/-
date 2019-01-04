

//父选框的全选和取消全选
var ischeck=false;//全选标记
$("#checkAll").click(function(){
    if (ischeck) {
        $(".check").prop("checked",!ischeck);
        ischeck=false;
    }else{
        $(".check").prop("checked",!ischeck);
        ischeck=true;
    }
});


//子选框全部选中,父选框选中;子选框不全选中,父选框不选中
var isallcheck=true;//所有的子选框选中的标记
function selectPa(){
    $(".check").each(function(i,o){
        if(!$(o).prop("checked")){
            isallcheck=false;
            return;
        }
    });
    if(isallcheck){
        ischeck=true;
        $("#checkAll").prop("checked",isallcheck);
    }else{
        ischeck=false;
        $("#checkAll").prop("checked",isallcheck);
    }
    isallcheck=true;//恢复所有的子选框选中的默认标记
};

//删除勾选的文件或者是文件夹
$("#deleteSubmit").click(function deleteFile() {
        //在table中找input下类型为checkbox属性为选中状态的数据
        var check = $("table input[type=checkbox]:checked");
        if(check.length == 0)
            alert("请选择文件或者文件夹！");
        else if(confirm("是否删除？")) {
            check.each(function () {//遍历
                //获取选中行
                var row = $(this).parent("td").parent("tr");

                //获取name='folderId'的数值
                var folderId = row.find("[name = 'folderId']").html();

                //获取name='fileId'的数值
                var fileId = row.find("[name = 'fileId']").html();

                if(fileId !=null) {
                    $.ajax({
                        url:"delete_File.action?file.id="+ fileId,
                        type:"POST",
                        success:function () {
                            row.remove();

                        }
                    })
                }else if(folderId != null) {
                    $.ajax({
                        url:"delete_Folder.action?folder.id="+folderId,
                        type:"POST",
                        success:function () {
                            row.remove();

                        }
                    })
                }

            });
        }
    }
);


//下载勾选的文件
$("#download").click(function downloadFile() {

    var a = document.getElementById("a");
    a.href = "#";
    //在table中找input下类型为checkbox属性为选中状态的数据

    var folderBox = $(":checkbox[name='folderBox']:checked");
    if(folderBox.length>0)
        alert("不能选择文件夹下载！！");
    else{

        var check = $(":checkbox[name='fileBox']:checked");
        if(check.length == 0)
            alert("请选择下载的文件！");
        else if(confirm("是否下载？")) {

            var totalArr = [];

            check.each(function () {
                //获取选中行
                var row = $(this).parent("td").parent("tr");

                //获取name='fileId'的数值
                var fileId = row.find("[name = 'fileId']").html();

                var file = {};
                file["id"]=fileId;
                totalArr.push(file);

            });

            var data = JSON.stringify(totalArr);
            a.href = encodeURI("download.action?fileIds="+data);
        }
    }


});


//打开文件夹->展示内容
function clickFolder(e) {
    $.ajax({
        url:"content_Folder.action?folder.id="+e,
        type:"POST",
        success:function (data) {
            $(".right").html(data);
        }
    });
}

//path根据文件不断打开而丰富
$("#folderId").click(function () {
    var name = $(this).next(":input").val();
    document.getElementById("path").value = document.getElementById("path").value + name + "/";

});

//path根据文件返回而减少
$("#reducePath").click(function () {
   var name = document.getElementById("path").value;
    for (var i = -1, arr = [];
         (i = name.indexOf("/", i + 1)) > -1;
         arr.push(i));
    var path = name.substr(0,arr[arr.length - 2]);
    document.getElementById("path").value = path+"/";

});



//将文件id赋给表单中input的value
function changeName(e) {
    document.getElementById("changeFileName").value=e;
    $(".updateName").css('display','block');
}

//文件重命名
$("#updateName_submit").click(function () {
    $.ajax({
        url: "updateName_File.action",
        type:"POST",
        data: $('#file-updateName').serialize(),
        success:function (data) {
            if(data==1)
                alert("此位置已经包含同名文件！！");
            else if(data==2)
                alert("文件名不能为空！！");
            else{
                $('#file-updateName').css('display','none');
                $(".right").html(data);
            }
        }
    });
    return false;
});


//将文件夹id赋给表单中input的value
function folderChangeName(e) {
    document.getElementById("changeFolderName").value=e;
    $(".folder_updateName").css('display','block');
}

//文件夹重命名
$("#folder_updateName_submit").click(function () {

    $.ajax({
        url: "updateName_Folder.action",
        type:"POST",
        data: $('#folder_updateName').serialize(),
        success:function (data) {
            if(data==1)
                alert("该文件夹名称已被占用!!");
            else if(data==2)
                alert("文件夹名不能为空！！");
            else {
                $('#folder_updateName').css('display','none');
                $(".right").html(data);
            }
        }
    });
    return false;
});


//文件属性
function fileAttribute(e) {
    $.ajax({
        url: "attribute_File.action?file.id="+e,
        type:"GET",
        dataType:"JSON",
        cache:false,
        success:function (data) {
            document.getElementById("fileTitle").value=data.fileName;
            document.getElementById("fileType").value=data.type;
            document.getElementById("keyword").value=data.keyword;
            $(".file-attribute").toggle(1000);
        }
    });
    return false;
}

//文件夹属性
function folderAttribute(e) {
    $.ajax({
        url: "attribute_Folder.action?folder.id="+e,
        type:"GET",
        dataType:"JSON",
        success:function (data) {

            document.getElementById("folderTitle").value=data.folderName;
            document.getElementById("folder-keyword").value=data.keyword;
            $(".folder-attribute").toggle(1000);
        }
    });
    return false;
}



































//勾选复选框->删除指定文件并刷新
// $("#deleteSubmit").click(function () {
//
//     var selectedItems = new Array();
//
//     $("[class='check']:checked").each(function() {
//         selectedItems.push($(this).val());
//
//     });
//     alert(selectedItems.length);
//
//     $.ajax({
//         type: "post",
//         url: "delete_Folder.action",
//         traditional : true,//需要加入这句代码才能正确的将数组正确的传到后台，要不然传的是Null
//         data : {
//             selectedItems : selectedItems
//         },
//         async:false,
//         dataType : "json",
//         success: function(date){
//
//             alert(date);
//
//         }
//     });
// });





