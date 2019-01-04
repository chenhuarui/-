package action;

import com.opensymphony.xwork2.ActionSupport;
import entity.FileEntity;
import entity.UploadFile;
import org.apache.struts2.interceptor.RequestAware;
import service.FolderService;
import service.UploadService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UploadAction extends ActionSupport implements RequestAware {

    private UploadFile uploadFile;

    private FileEntity fileEntity;

    private UploadService uploadService;

    private FolderService folderService;

    public FolderService getFolderService() {
        return folderService;
    }

    public void setFolderService(FolderService folderService) {
        this.folderService = folderService;
    }

    public UploadService getUploadService() {
        return uploadService;
    }

    public void setUploadService(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    public UploadFile getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(UploadFile uploadFile) {
        this.uploadFile = uploadFile;
    }

    public FileEntity getFileEntity() {
        return fileEntity;
    }

    public void setFileEntity(FileEntity fileEntity) {
        this.fileEntity = fileEntity;
    }

    public Map<String, Object> application;


    @Override
    public void setRequest(Map<String, Object> map) {
        this.application = map;
    }

    @Override
    public String execute() throws IOException {

        System.out.println(uploadFile.getFile());
        System.out.println(uploadFile.getFileFileName());
        System.out.println(uploadFile.getFileContentType());

        //放在哪个文件夹下
        fileEntity.setCurrentDirId(FolderAction.fatherIdOfFolder);

        uploadService.uploadFile(uploadFile, fileEntity);

        refreshPage(String.valueOf(FolderAction.fatherIdOfFolder));


        return "refresh";
    }



    //刷新操作完页面(新增等)
    private void refreshPage(String fatherId) {

        List<FileEntity> files = folderService.selectContainFiles(fatherId);

        List<String> Persons = new ArrayList<>();

        for (FileEntity file : files) {
            Persons.add(folderService.selectUserById(file.getUpdatePerson()).getUserName());
        }

        getSuperFolderId(fatherId);
        application.put("containFile", files);
        application.put("containFolder", folderService.selectContainFolders(fatherId));
        application.put("updateNameOfPerson", Persons);
    }

    //共有
    private void getSuperFolderId(String fatherFolderId) {
        String superFolderId = folderService.selectSuperFolderIdFromRela(fatherFolderId);
        application.put("superFolderId", superFolderId);
    }
}
