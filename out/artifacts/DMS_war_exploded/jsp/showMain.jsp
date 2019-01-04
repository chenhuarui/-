<%@ page import="entity.FolderEntity" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%--
  Created by IntelliJ IDEA.
  User: 陈华睿
  Date: 2018/11/24
  Time: 15:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>文档管理系统</title>
    <link rel="stylesheet" type="text/css" href="../css/showMain.css">
    <link rel="stylesheet" type="text/css" href="../css/windows.css">

    <script type="text/javascript" src="../js/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="../js/main.js"></script>
    <script type="text/javascript" src="../js/windows.js"></script>


</head>
<body>

<s:action name="query_Folder"/>
<div class="top_first">
    <div class="title">
        <img src="../img/play.png" class="play">
        <span>知识管理</span>
    </div>

    <div class="select">
        <ul>
            <li>列表显示</li>
            <li>
                <span id="folderAdd">新建文件夹</span>
            </li>
            <li>
                <span id="fileAdd">新建文件</span>
            </li>
            <li>
                <span id="uploadFile">上传文件</span>
            </li>
            <li>zip批量上传</li>
            <li>
                <span id="query">查询</span>
            </li>
        </ul>
    </div>

</div>

<hr>
<div class="top_second">
    <div class="path">
        <span>当前路径：</span>
        <img src="../img/folder.png">
    </div>

    <div class="showPath">
        <input type="text" id="path">
    </div>

    <div class="property">
        <img src="../img/folder.png">
        <span>属性</span>
    </div>
</div>

<hr>
<dic class="show_main">

    <div class="left" style="width: 9%;">
        <div class="book">
            <img src="../img/book.png">
            <span>知识管理</span>
        </div>

        <ul>
            <%
                List<FolderEntity> list = (List<FolderEntity>) request.getAttribute("FolderList");

                for(int i=0; i<list.size(); i++) {
            %>
            <li>
                <img src="../img/open.png">
                <img src="../img/file.png">
                <span>
                    <a href="content_Folder.action?folder.id=<%=list.get(i).getId()%>" class="refresh">
                         <%=list.get(i).getFolderName()%>
                    </a>
                    <input hidden value="<%=list.get(i).getFolderName()%>">
                </span>
            </li>
            <% }%>
        </ul>
    </div>

    <div class="right"></div>
</dic>



<%--新增文件夹的页面--%>
<div class="addFolder" id="addFolder">
    <form class="contact" id="folderForm">
        <h3>新建文件夹</h3>
        <p onclick="cancel(1)">ⓧ</p>
        <fieldset>
            文件夹名称<input type="text" name="folder.folderName">
        </fieldset>
        <fieldset>
            关键字<textarea rows="5" cols="10" name="folder.keyword"></textarea>
        </fieldset>
        <fieldset>
            上级文件夹<input type="text" id="folder">
        </fieldset>
        <fieldset>
            <button type="submit" id="addFolder_submit">确定</button>
        </fieldset>
    </form>
</div>


<%--新建文件的页面--%>
<div class="addFile" id="addFile">

    <form class="contact" id="file-form">
        <h3>新建文件</h3>
        <p onclick="cancel(2)">ⓧ</p>
        <fieldset>
            文件主题<input type="text" name="file.fileName" >
        </fieldset>
        <fieldset>
            文件类型
            <select name="file.type">
                <option value=".txt">.txt</option>
                <option value=".png">.png</option>
                <option value=".doc">.doc</option>
            </select>
        </fieldset>
        <fieldset>
            关键字<textarea rows="5" cols="10" name="file.keyword"></textarea>
        </fieldset>
        <fieldset>
            所属文件夹<input type="text" readonly="readonly" id="file">
        </fieldset>
        <fieldset>
            <button type="submit" id="addFile_submit">确定</button>
        </fieldset>
    </form>
</div>




<%--上传的页面--%>
<div class="upload" id="upload">
    <form class="contact" id="uploadForm">
        <h3>文件上传</h3>
        <p onclick="cancel(3)">ⓧ</p>
        <fieldset>
            文件主题<input type="text" id="fileTheme">
        </fieldset>
        <fieldset>
            关键字<textarea rows="5" cols="10" id="keyword"></textarea>
        </fieldset>
        <fieldset>
            文件信息&nbsp;&nbsp;<input type="file" id="file1">
        </fieldset>
        <fieldset>
            所属文件夹<input type="text" class="url" readonly="readonly" id="uploadPage">
        </fieldset>
        <fieldset>
            <button type="submit" id="upload_submit">确定</button>
        </fieldset>
    </form>
</div>




<%--查询的页面--%>
<div class="queryPage"  id="query-Page">
    <form class="contact" id="query-Form">
        <h3>查询</h3>
        <p onclick="cancel(4)">ⓧ</p>
        <fieldset>
            文件名<input type="text" name="file.fileName">
        </fieldset>
        <fieldset>
            关键字<input type="text" name="file.keyword">
        </fieldset>
        <fieldset>
            创建者&nbsp;
            <select name="file.updatePerson" class="select" id="resGroupList">
            </select>
            &nbsp;&nbsp;
            <select class="select" onchange="changePerson(this.value)">
                <option value="" selected="true" disabled="true" hidden>选择类型</option>
                <option>销售部</option>
                <option>技术部</option>
                <option>物流部</option>
            </select>
        </fieldset>
        <fieldset>
            创建日期&nbsp;&nbsp;
            <input type="date" name="file.createTime">&nbsp;至&nbsp;<input type="date" name="endTime">
        </fieldset>
        <fieldset>
            <button type="submit" id="query_submit">查询</button>
        </fieldset>
    </form>
</div>

</body>
</html>
