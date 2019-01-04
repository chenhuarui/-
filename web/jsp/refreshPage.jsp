<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="entity.FileEntity" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.FolderEntity" %>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>文档管理系统-刷新页面</title>
    <script type="text/javascript" src="../js/refreshPage.js"></script>

    <link rel="stylesheet" type="text/css" href="../css/showMain.css">
    <link rel="stylesheet" type="text/css" href="../css/windows.css">

</head>
<body>
<div class="refreshPage">
    <div class="right_top">

        <table class="table" border="0" cellpadding="0" cellspacing="0">
            <thead>
                <tr>
                    <th style="width: 45%">
                        <input type="checkbox" id="checkAll"/>
                        名称
                    </th>
                    <th style="width: 10%">大小</th>
                    <th style="width: 9%">点击</th>
                    <th style="width: 26%">最后更新</th>
                    <th>操作</th>
                </tr>
            </thead>

            <%
                String superFolderId = (String) request.getAttribute("superFolderId");

                if(!superFolderId.equals("null")) {
            %>
            <tr>
                <td style="float: left">
                    <span class="super">
                        <img src="../img/file.png" onclick="clickFolder(<%=superFolderId%>)" id="reducePath">
                        ...
                    </span>
                </td>
                <td></td>
                <td></td>
                <td></td>
                <td>
                </td>
            </tr>
            <%
                }
            %>



            <%
                List<FileEntity> files = (List<FileEntity>) request.getAttribute("containFile");

                List<FolderEntity> folders = (List<FolderEntity>) request.getAttribute("containFolder");

                List<String> updateNameOfPerson = (List<String>) request.getAttribute("updateNameOfPerson");

                if(folders != null) {

                    for(int i=0; i<folders.size(); i++) {
            %>
            <tr>
                <td name="folderId" hidden><%=folders.get(i).getId()%></td>
                <td style="float: left">
                    <input type="checkbox" class="check" onclick="selectPa()" name="folderBox"/>

                    <img src="../img/file.png" id="folderId" onclick="clickFolder(<%=folders.get(i).getId()%>)">
                    <input hidden value="<%=folders.get(i).getFolderName()%>">
                    <%=folders.get(i).getFolderName()%>
                </td>
                <td></td>
                <td></td>
                <td><%=folders.get(i).getUpdateTime()%>
                    管理员
                </td>
                <td class="td">
                    <span onclick="folderAttribute(<%=folders.get(i).getId()%>)">属性</span>
                    <span id="folder-changeName" onclick="folderChangeName(<%=folders.get(i).getId()%>)">重命名</span>
                </td>
            </tr>

            <% }

            } %>

            <%
                if(files != null) {
                    for(int j=0; j<files.size(); j++) {
            %>
            <tr>
                <td name="fileId" hidden><%=files.get(j).getId()%></td>
                <td style="float: left;">
                    <input type="checkbox" class="check" name="fileBox" onclick="selectPa()" value="<%=files.get(j).getId()%>"/>
                    <img src="../img/file2.png">
                    <%=files.get(j).getFileName()%>
                    <%=files.get(j).getType()%>
                </td>
                <td><%=files.get(j).getSize()%>KB</td>
                <td><%=files.get(j).getClickCount()%></td>

                <td>
                    <%=files.get(j).getUpdateTime()%>
                    &nbsp;
                    <%=updateNameOfPerson.get(j)%>
                </td>

                <td class="td">
                    <span onclick="fileAttribute(<%=files.get(j).getId()%>)">属性</span>
                    <span id="changeName" onclick="changeName(<%=files.get(j).getId()%>)">重命名</span>
                    <span>编辑</span>
                    <span>查看</span>
                </td>
            </tr>
            <%
                    }
                }
            %>

        </table>
    </div>
    <div class="right_foot">
        <ul>
            <li>
                <input type="button" id="deleteSubmit" value="删除">
            </li>
            <li>
                <a href="#" id="a" ><button id="download">下载</button></a>
            </li>
            <li>
                <button class="button">历史</button>
            </li>
        </ul>
    </div>
</div>


<%--文件夹重命名--%>
<div class="folder_updateName">
    <form class="contact" id="folder_updateName">
        <h3>重命名</h3>
        <p onclick="cancel(8)">ⓧ</p>
        <input hidden id="changeFolderName" name="folder.id">
        <fieldset>
            新名字:<input type="text" name="folder.folderName">
        </fieldset>
        <fieldset>
            <button type="submit" id="folder_updateName_submit">确定</button>
        </fieldset>
    </form>
</div>



<%--文件重命名--%>
<div class="updateName">
    <form class="contact" id="file-updateName">
        <h3>重命名</h3>
        <p onclick="cancel(5)">ⓧ</p>
        <input hidden id="changeFileName" name="file.id">
        <fieldset>
            新名字:<input type="text" name="file.fileName">
        </fieldset>
        <fieldset>
            <button type="submit" id="updateName_submit">确定</button>
        </fieldset>
    </form>
</div>




<%--文件夹-属性弹框--%>
<div class="folder-attribute" id="folder-attribute">
    <form class="contact" id="folderFrom">
        <h3>详细信息</h3>
        <p onclick="cancel(7)">ⓧ</p>
        <fieldset>
            文件夹名称<input type="text" id="folderTitle" readonly="readonly">
        </fieldset>
        <fieldset>
            关键字<textarea rows="5" cols="10" id="folder-keyword" readonly="readonly"></textarea>
        </fieldset>
    </form>
</div>


<%--文件-属性弹框--%>
<div class="file-attribute" id="file-attribute">
    <form class="contact" id="fileForm">
        <h3>详细信息</h3>
        <p onclick="cancel(6)">ⓧ</p>
        <fieldset>
            文件主题<input type="text" id="fileTitle" readonly="readonly">
        </fieldset>
        <fieldset>
            文件类型<input type="text" id="fileType" readonly="readonly">
        </fieldset>
        <fieldset>
            关键字<textarea rows="5" cols="10" id="keyword" readonly="readonly"></textarea>
        </fieldset>
    </form>
</div>




</body>
</html>
