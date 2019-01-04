package action;

import entity.FileEntity;
import entity.FolderEntity;
import net.sf.json.JSONObject;
import org.apache.struts2.interceptor.RequestAware;
import service.FolderService;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FolderAction implements RequestAware {

    private FolderService folderService;

    private FolderEntity folder;

    private InputStream inputStream;

    //得到存放新建文件夹-->父文件夹ID
    public static Integer fatherIdOfFolder;

    private JSONObject jsonResult;

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public JSONObject getJsonResult() {
        return jsonResult;
    }

    public void setJsonResult(JSONObject jsonResult) {
        this.jsonResult = jsonResult;
    }

    public FolderEntity getFolder() {
        return folder;
    }

    public void setFolder(FolderEntity folder) {
        this.folder = folder;
    }

    public FolderService getFolderService() {
        return folderService;
    }

    public void setFolderService(FolderService folderService) {
        this.folderService = folderService;
    }

    private Map<String, Object> application;

    @Override
    public void setRequest(Map<String, Object> map) {
        this.application = map;
    }

    //查询初始化左边的的文件夹列表
    public String queryFolder() {
        List<FolderEntity> list = folderService.select();

        application.put("FolderList", list);

        return "success";
    }

    //查询在文件夹中的文件夹、文件
    public String contentFolder() {

        String fatherFolderId = String.valueOf(folder.getId());

        //保存父文件夹ID
        setFatherId(fatherFolderId);

        refreshPage(fatherFolderId);

        return "refresh";
    }

    public String deleteFolder() {

        String id = String.valueOf(folder.getId());

        getSuperFolderId(id);

        folderService.deleteFolder(id);

        return "refresh";
    }


    public String addFolder() {

        //判断新建的文件夹名称不能为空
        if(folder.getFolderName().equals("")) {
            getMessage("2");
            return "nofresh";
        }


        //判断该目录下是否已经存在该名称的文件夹
        //没有返回true
        if(folderService.selectFolderOfTheSameName(folder.getFolderName(),fatherIdOfFolder)){

            //添加到文件夹表中
            Integer folderId = folderService.addFolder(folder);

            //添加到文件夹关系表中
            folderService.addFolderToRela(fatherIdOfFolder, folderId);

            getSuperFolderId(String.valueOf(fatherIdOfFolder));
            refreshPage(String.valueOf(fatherIdOfFolder));

            return "refresh";
        }else{
            getMessage("1");
            return "nofresh";
        }


    }


    public String attributeFolder() {

        FolderEntity folderEntity = folderService.selectContainFoldersById(String.valueOf(folder.getId()));

        jsonResult = JSONObject.fromObject(folderEntity);

        return "json";
    }

    public String updateNameFolder() {

        System.out.println(folder.getId());
        System.out.println(folder.getFolderName());

        //判断文件夹名称不能为空
        if(folder.getFolderName().equals("")) {
            getMessage("2");
            return "nofresh";
        }

        //判断该目录下是否已经存在该名称的文件夹
        //没有返回true
        if(folderService.selectFolderOfTheSameName(folder.getFolderName(),fatherIdOfFolder)) {

            folderService.updateName(folder.getId(), folder.getFolderName());

            folderService.updateTime(folder);

            refreshPage(String.valueOf(FolderAction.fatherIdOfFolder));

            return "refresh";
        }else {

            getMessage("1");
            return "nofresh";
        }
    }

    //共有
    private void getSuperFolderId(String fatherFolderId) {
        String superFolderId = folderService.selectSuperFolderIdFromRela(fatherFolderId);
        application.put("superFolderId", superFolderId);
    }

    //保存父文件夹id
    private Integer setFatherId(String fatherId) {
        return fatherIdOfFolder = Integer.valueOf(fatherId);
    }


    //刷新操作完页面(新增等)
    private void refreshPage(String fatherFolderId) {

        List<FolderEntity> folders = folderService.selectContainFolders(fatherFolderId);

        List<FileEntity> files = folderService.selectContainFiles(fatherFolderId);

        List<String> updateNameOfPerson = new ArrayList<>();

        for (FileEntity file : files) {
            updateNameOfPerson.add(folderService.selectUserById(file.getUpdatePerson()).getUserName());
        }

        getSuperFolderId(fatherFolderId);
        application.put("containFile", files);
        application.put("containFolder", folders);
        application.put("updateNameOfPerson", updateNameOfPerson);
    }

    private void getMessage(String message) {
        try {
            inputStream = new ByteArrayInputStream(message.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}

