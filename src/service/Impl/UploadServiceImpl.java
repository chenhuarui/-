package service.Impl;

import com.opensymphony.xwork2.ActionContext;
import dao.FileDao;
import dao.UploadDao;
import entity.FileEntity;
import entity.UploadFile;
import entity.UserEntity;
import service.UploadService;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UploadServiceImpl implements UploadService {

    private UploadDao uploadDao;

    private FileDao fileDao;

    public FileDao getFileDao() {
        return fileDao;
    }

    public void setFileDao(FileDao fileDao) {
        this.fileDao = fileDao;
    }

    public UploadDao getUploadDao() {
        return uploadDao;
    }

    public void setUploadDao(UploadDao uploadDao) {
        this.uploadDao = uploadDao;
    }

    @Override
    public void uploadFile(UploadFile file, FileEntity fileEntity) throws IOException {

        //根据上传的文件得到输入流
        InputStream is = new FileInputStream(file.getFile());

        //设置文件保存的目录
        String dir = "E:\\Eclipse_Java\\DMS\\web\\WEB-INF\\uploadFile";

        String name = fileEntity.getFileName();

        //判断上传是否输入文件名
        //没有的就默认为上传文件原本的文件名
        if(name.equals("")) {
            name = file.getFileFileName().substring(0,file.getFileFileName().indexOf("."));
            fileEntity.setFileName(name);
        }

        String type = file.getFileFileName().substring(file.getFileFileName().indexOf("."));

        //设置目标文件
        File toFile = new File(dir, name+type);

        //文件输出流
        OutputStream os = new FileOutputStream(toFile);
        byte[] buffer = new byte[1024];


        //读取file文件输出到toFile文件中
        while(is.read(buffer, 0, buffer.length) != -1) {
            os.write(buffer);
        }

        //关闭输入和输出流
        os.close();
        is.close();


        fileEntity.setType(type);

        File f= new File("E:\\Eclipse_Java\\DMS\\web\\WEB-INF\\uploadFile\\"+name+type);
        fileEntity.setSize((double) f.length()/1000);

        fileEntity.setClickCount("0");

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        fileEntity.setCreateTime(df.format(new Date()));
        fileEntity.setUpdateTime(df.format(new Date()));

        ActionContext actionContext = ActionContext.getContext();
        UserEntity user = (UserEntity) actionContext.getSession().get("user");
        fileEntity.setUpdatePerson(String.valueOf(user.getId()));

        fileEntity.setId(fileDao.selectNewIdOfFile() + 1);

        uploadDao.uploadFile(fileEntity);

    }
}
