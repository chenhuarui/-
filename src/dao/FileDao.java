package dao;

import entity.FileEntity;

import java.util.List;

public interface FileDao {

    //删除文件夹下的-->文件
    void deleteFile(String currentDirId);

    //删除文件
    void deleteFile(Integer fileId);

    //新增文件
    void addFile(FileEntity file);

    //查询文件表中最新的文件id
    Integer selectNewIdOfFile();

    //根据文件id->返回文件对象
    FileEntity selectFileOfId(Integer id);

    //重命名
    void updateName(Integer id, String name);

    //文件查询
    List<FileEntity> queryFiles(FileEntity file, String endTime);

    void updateTimeAndPerson(FileEntity file);

}
