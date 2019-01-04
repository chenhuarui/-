package service;

import entity.FileEntity;

import java.util.List;

public interface FileService {

    void deleteFile(String currentDirId);

    void addFile(FileEntity file);

    boolean isFileOfSameName(FileEntity file);

    void deleteFile(Integer fileId);

    FileEntity selectFileOfId(Integer id);

    void updateName(Integer id, String name);

    List<FileEntity> queryFiles(FileEntity file, String endTime);

    void updateTimeAndPerson(FileEntity file);
}
