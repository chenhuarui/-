package service.Impl;

import dao.FileDao;
import dao.FolderDao;
import entity.FileEntity;
import entity.FolderEntity;
import entity.RelationshipEntity;
import entity.UserEntity;
import service.FolderService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FolderServiceImpl implements FolderService {

    private FolderDao folderDao;

    private FileDao fileDao;

    public FileDao getFileDao() {
        return fileDao;
    }

    public void setFileDao(FileDao fileDao) {
        this.fileDao = fileDao;
    }

    public FolderDao getFolderDao() {
        return folderDao;
    }

    public void setFolderDao(FolderDao folderDao) {
        this.folderDao = folderDao;
    }

    /**
     * 查询初始化左边的文件夹列表
     * @return
     */
    @Override
    public List<FolderEntity> select() {

        List<FolderEntity> list = folderDao.select();
        List<FolderEntity> newList = new ArrayList<>();

        for(int i=0; i<list.size(); i++) {
            //查询文件关系表：该文件的上级文件夹是否为null
            if(folderDao.selectSuperFolderIdFromRela(String.valueOf(list.get(i).getId())).equals("null"))
                newList.add(list.get(i));
        }

        return newList;
    }

    /**
     * 查询该文件夹下包含的文件夹
     * 返回文件夹对象列表
     * @param id
     * @return
     */
    @Override
    public List<FolderEntity> selectContainFolders(String id) {
        List<RelationshipEntity> folders = folderDao.selectFoldersFromRela(id);
        List<FolderEntity> list = new ArrayList<>();
        for(int i=0; i<folders.size(); i++) {
            list.add(folderDao.selectContainFoldersById(String.valueOf(folders.get(i).getSubordinateFolder())));
        }

        return list;
    }

    @Override
    public List<FileEntity> selectContainFiles(String id) {
        return folderDao.selectContainFiles(id);
    }

    @Override
    public UserEntity selectUserById(String id) {
        return folderDao.selectUserById(id);
    }

    @Override
    public String selectSuperFolderIdFromRela(String subordinateFolder) {
        return folderDao.selectSuperFolderIdFromRela(subordinateFolder);
    }

    /**
     * 删除指定的文件夹:包括它包含的子文件夹等等
     * @param folderId
     */
    @Override
    public void deleteFolder(String folderId) {

        //删除文件夹表中的记录
        folderDao.deleteFromFolder(folderId);

        //删除该文件夹以及子文件下的文件
        fileDao.deleteFile(folderId);

        //删除文件夹关系表中:该文件夹作为子文件的记录
        folderDao.deleteFromRelaBySubordId(folderId);

        //该文件夹下的子文件夹有哪些
        //exp:1: 2 3
        List<RelationshipEntity> folders = folderDao.selectFoldersFromRela(folderId);

        if(folders.size()!=0) {
            for(RelationshipEntity folder : folders) {
                deleteFolder(String.valueOf(folder.getSubordinateFolder()));
            }
        }else
            return;

    }

    @Override
    public void deleteFile(String folderId) {
        fileDao.deleteFile(folderId);
    }

    @Override
    public Integer addFolder(FolderEntity folder) {

        FolderEntity folderEntity = new FolderEntity();

        folderEntity.setFolderName(folder.getFolderName());
        folderEntity.setKeyword(folder.getKeyword());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        folderEntity.setUpdateTime(df.format(new Date()));

        return folderDao.addFolder(folderEntity);
    }

    @Override
    public void addFolderToRela(int fatherId, int sonId) {
        RelationshipEntity rela = new RelationshipEntity();
        rela.setFolderId(fatherId);
        rela.setSubordinateFolder(sonId);

        folderDao.addFolderToRela(rela);
    }

    @Override
    public FolderEntity selectContainFoldersById(String id) {
        return folderDao.selectContainFoldersById(id);
    }

    @Override
    public void updateName(Integer id, String name) {
        folderDao.updateName(id, name);
    }

    @Override
    public boolean selectFolderOfTheSameName(String name, int fatherIdOfFolder) {

        List<FolderEntity> containFolders = selectContainFolders(String.valueOf(fatherIdOfFolder));

        for(FolderEntity folder : containFolders) {
            if(folder.getFolderName().equals(name))
                return false;
        }

        return true;
    }

    @Override
    public void updateTime(FolderEntity folder) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        folder.setUpdateTime(df.format(new Date()));

        folderDao.updateTime(folder);
    }


}
