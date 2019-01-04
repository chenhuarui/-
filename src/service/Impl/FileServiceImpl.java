package service.Impl;

import com.opensymphony.xwork2.ActionContext;
import dao.FileDao;
import dao.FolderDao;
import entity.FileEntity;
import entity.UserEntity;
import service.FileService;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FileServiceImpl implements FileService {

    private FileDao fileDao;

    private FolderDao folderDao;

    public FolderDao getFolderDao() {
        return folderDao;
    }

    public void setFolderDao(FolderDao folderDao) {
        this.folderDao = folderDao;
    }

    public FileDao getFileDao() {
        return fileDao;
    }

    public void setFileDao(FileDao fileDao) {
        this.fileDao = fileDao;
    }

    @Override
    public void deleteFile(String currentDirId) {
        fileDao.deleteFile(currentDirId);
    }

    @Override
    public boolean isFileOfSameName(FileEntity file) {

        List<FileEntity> containFiles = folderDao.selectContainFiles(String.valueOf(file.getCurrentDirId()));

        //重命名的时候判断
        if(file.getType()==null)
            file.setType(selectFileOfId(file.getId()).getType());

        if(containFiles.size()!=0) {
            for (int i = 0; i < containFiles.size(); i++) {
                if (containFiles.get(i).getFileName().equals(file.getFileName())
                        && containFiles.get(i).getType().equals(file.getType()))
                    return false;
            }
        }

        return true;
    }

    @Override
    public void addFile(FileEntity file) {

        ActionContext actionContext = ActionContext.getContext();

        UserEntity user = (UserEntity) actionContext.getSession().get("user");

        file.setSize(0.00);
        file.setClickCount("0");

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        file.setCreateTime(df.format(new Date()));
        file.setUpdateTime(df.format(new Date()));

        file.setUpdatePerson(String.valueOf(user.getId()));

        String fileName = file.getFileName() + file.getType();
        File newFile = new File("E:\\Eclipse_Java\\DMS\\web\\WEB-INF\\uploadFile", fileName);
        try {
            newFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        fileDao.addFile(file);

    }


    @Override
    public void deleteFile(Integer fileId) {

        //在磁盘上删除该文件
        FileEntity file = selectFileOfId(fileId);
        String path = "E:\\Eclipse_Java\\DMS\\web\\WEB-INF\\uploadFile\\"+file.getFileName()+file.getType();
        File f = new File(path);
        f.delete();

        fileDao.deleteFile(fileId);
    }

    @Override
    public FileEntity selectFileOfId(Integer id) {
        return fileDao.selectFileOfId(id);
    }

    @Override
    public void updateName(Integer id, String name) {

        FileEntity file = fileDao.selectFileOfId(id);

        String path = "E:\\Eclipse_Java\\DMS\\web\\WEB-INF\\uploadFile\\";
        File oldFile = new File(path+file.getFileName()+file.getType());
        File newFile = new File(path+name+file.getType());
        oldFile.renameTo(newFile);


        fileDao.updateName(id, name);
    }

    @Override
    public List<FileEntity> queryFiles(FileEntity file, String endTime) {
        return fileDao.queryFiles(file, endTime);
    }

    @Override
    public void updateTimeAndPerson(FileEntity file) {
        ActionContext actionContext = ActionContext.getContext();

        UserEntity user = (UserEntity) actionContext.getSession().get("user");

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        file.setUpdateTime(df.format(new Date()));

        file.setUpdatePerson(String.valueOf(user.getId()));

        fileDao.updateTimeAndPerson(file);
    }
}
