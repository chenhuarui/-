package service;

import entity.FileEntity;
import entity.FolderEntity;
import entity.UserEntity;

import java.util.List;

public interface FolderService {

    List<FolderEntity> select();

    List<FileEntity> selectContainFiles(String id);

    List<FolderEntity> selectContainFolders(String id);

    UserEntity selectUserById(String id);

    String selectSuperFolderIdFromRela(String subordinateFolder);

    void deleteFolder(String folderId);

    void deleteFile(String folderId);

    Integer addFolder(FolderEntity folder);

    void addFolderToRela(int fatherId, int sonId);

    FolderEntity selectContainFoldersById(String id);

    void updateName(Integer id, String name);

    boolean selectFolderOfTheSameName(String name, int fatherIdOfFolder);

    void updateTime(FolderEntity folder);
}
