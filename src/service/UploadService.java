package service;

import entity.FileEntity;
import entity.UploadFile;

import java.io.IOException;

public interface UploadService {

    void uploadFile(UploadFile file, FileEntity fileEntity) throws IOException;
}
