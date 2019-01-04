package dao;

import entity.FileEntity;
import entity.FolderEntity;
import entity.RelationshipEntity;
import entity.UserEntity;

import java.util.List;

public interface FolderDao {

    List<FolderEntity> select();

    List<FileEntity> selectContainFiles(String id);

    List<RelationshipEntity> selectFoldersFromRela(String id);

    //返回该文件夹的上级文件夹的id
    String selectSuperFolderIdFromRela(String subordinateFolder);

    FolderEntity selectContainFoldersById(String id);

    UserEntity selectUserById(String id);

    void deleteFromFolder(String folderId);

    void deleteFromRelaBySubordId(String subordinateId);

    //新增文件夹:成功返回新增文件夹的id
    Integer addFolder(FolderEntity folder);

    //查询文件夹表中最新的文件夹id
    Integer selectNewIdOfFolder();

    //新增文件夹:添加记录到文件夹关联表中
    void addFolderToRela(RelationshipEntity rela);

    //查询文件夹关联表中最新的文件夹id
    Integer selectNewIdOfRela();

    void updateName(Integer id, String name);

    void updateTime(FolderEntity folder);


}
