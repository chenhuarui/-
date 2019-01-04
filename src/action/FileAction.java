package action;

import entity.FileEntity;
import net.sf.json.JSONObject;
import org.apache.struts2.interceptor.RequestAware;
import service.FileService;
import service.FolderService;
import service.UserService;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileAction implements RequestAware {

    private FileService fileService;

    private FolderService folderService;

    private FileEntity file;

    private JSONObject jsonResult;

    private String endTime;

    private UserService userService;

    private InputStream inputStream;

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public JSONObject getJsonResult() {
        return jsonResult;
    }

    public void setJsonResult(JSONObject jsonResult) {
        this.jsonResult = jsonResult;
    }

    public FileEntity getFile() {
        return file;
    }

    public void setFile(FileEntity file) {
        this.file = file;
    }

    public FileService getFileService() {
        return fileService;
    }

    public void setFileService(FileService fileService) {
        this.fileService = fileService;
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

    //删除文件
    public String deleteFile() {

        getSuperFolderId(String.valueOf(FolderAction.fatherIdOfFolder));

        fileService.deleteFile(file.getId());

        return "refresh";

    }


    public String addFile() {

        //判断文件名称不能为空
        if(file.getFileName().equals("")) {
            getMessage("2");
            return "nofresh";
        }

        //设置该文件的父文件夹ID
        file.setCurrentDirId(FolderAction.fatherIdOfFolder);

        //该目录下是否已经存在同名同类型的文件
        //无则返回true
        if(fileService.isFileOfSameName(file)) {

            fileService.addFile(file);

            refreshPage(String.valueOf(FolderAction.fatherIdOfFolder));

            return "refresh";
        }else {
            getMessage("1");
            return "nofresh";
        }

    }


    //文件重命名
    public String updateNameFile() {

        //判断文件名称不能为空
        if(file.getFileName().equals("")) {
            getMessage("2");
            return "nofresh";
        }

        //设置该文件的父文件夹ID
        file.setCurrentDirId(FolderAction.fatherIdOfFolder);

        if(fileService.isFileOfSameName(file)) {

            //更新名字
            fileService.updateName(file.getId(), file.getFileName());

            //更新时间
            fileService.updateTimeAndPerson(file);

            refreshPage(String.valueOf(FolderAction.fatherIdOfFolder));
            return "refresh";
        }else {
            getMessage("1");
            return "nofresh";
        }
    }

    //文件属性
    public String attributeFile() {

        FileEntity fileEntity = fileService.selectFileOfId(file.getId());

        jsonResult = JSONObject.fromObject(fileEntity);



        return "json";
    }

    //查询
    public String queryFile() {

        //没有选择则为null
        if(file.getUpdatePerson()!=null) {

                file.setUpdatePerson(String.valueOf(userService.selectUser(file.getUpdatePerson()).getId()));
        }


        List<FileEntity> list = fileService.queryFiles(file, endTime);

        System.out.println(list.toString());

        application.put("containFile", list);

        application.put("superFolderId", "null");
        application.put("containFolder",null);
        getUpdateOfPerson(list);

        return "refresh";
    }

    //刷新操作完页面(新增等)
    private void refreshPage(String fatherId) {

        List<FileEntity> files = folderService.selectContainFiles(fatherId);

        getUpdateOfPerson(files);

        getSuperFolderId(fatherId);
        application.put("containFile", files);
        application.put("containFolder", folderService.selectContainFolders(fatherId));
    }

    //共有
    private void getSuperFolderId(String fatherFolderId) {
        String superFolderId = folderService.selectSuperFolderIdFromRela(fatherFolderId);
        application.put("superFolderId", superFolderId);
    }


    private void getUpdateOfPerson(List<FileEntity> files) {
        List<String> updateNameOfPerson = new ArrayList<>();

        for (FileEntity file : files) {
            updateNameOfPerson.add(folderService.selectUserById(file.getUpdatePerson()).getUserName());
        }

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
