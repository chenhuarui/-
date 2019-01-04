package action;

import entity.FileEntity;
import net.sf.json.JSONArray;
import service.FileService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class DownloadAction {

    private FileService fileService;

    private String fileIds;

    private String fileName;

    private InputStream inputStream;

    public String getFileIds() {
        return fileIds;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileIds(String fileIds) {
        this.fileIds = fileIds;
    }

    public FileService getFileService() {
        return fileService;
    }

    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String execute() throws Exception {

        JSONArray jsonArray = JSONArray.fromObject(fileIds);

        List<Integer> ids = new ArrayList<>();

        for (int i = 0; i < jsonArray.size(); i++) {
            int id = Integer.valueOf((String) jsonArray.getJSONObject(i).get("id"));
            ids.add(id);
            System.out.println(id);
        }

        String path = "E:\\Eclipse_Java\\DMS\\web\\WEB-INF\\uploadFile\\";
        if (ids.size() == 1) {
            FileEntity file = fileService.selectFileOfId(ids.get(0));

            fileName = new String(file.getFileName().getBytes(), "ISO-8859-1") + file.getType();

            //下载路径
            path = path + file.getFileName() + file.getType();
            inputStream = new FileInputStream(path);
        } else {

            //多个文件
            List<File> files = new ArrayList<>();
            for (int i = 0; i < ids.size(); i++) {
                FileEntity file = fileService.selectFileOfId(ids.get(i));
                path = path + file.getFileName() + file.getType();
                files.add(new File(path));
            }

            System.out.println(files.size());


            File fileAll = new File("e:/AllFile.zip");
            if (!fileAll.exists()) {
                fileAll.createNewFile();
            }
            fileName = "AllFile.zip";

            //创建压缩文件输出流
            FileOutputStream fous = new FileOutputStream(fileAll);
            ZipOutputStream zos = new ZipOutputStream(fous);


            zipFile(files, zos);

            zos.close();
            fous.close();

            inputStream = new FileInputStream("e:/AllFile.zip");
        }

        return "download";
    }


    private void zipFile (List<File> files, ZipOutputStream zipoutputStream) throws IOException {
            for (int i =0; i<files.size(); i++) {
                File inputFile = files.get(i);
                // 判断文件是否存在
                if (inputFile.exists()) {

                    // 判断是否属于文件，还是文件夹
                    if (inputFile.isFile()) {

                        // 创建输入流读取文件
                        FileInputStream fis = new FileInputStream(inputFile);
                        BufferedInputStream bis = new BufferedInputStream(fis);

                        // 将文件写入zip内，即将文件进行打包
                        ZipEntry ze = new ZipEntry(inputFile.getName()); // 获取文件名
                        zipoutputStream.putNextEntry(ze);

                        // 写入文件的方法，同上
                        byte[] b = new byte[1024];
                        long l = 0;
                        while (l < inputFile.length()) {
                            int j = bis.read(b, 0, 1024);
                            l += j;
                            zipoutputStream.write(b, 0, j);
                        }
                        // 关闭输入输出流
                        bis.close();
                        fis.close();
                    }

                }

            }
    }

//    private void zipFile (File inputFile, ZipOutputStream zipoutputStream){
//        try {
//            // 判断文件是否存在
//            if (inputFile.exists()) {
//
//                // 判断是否属于文件，还是文件夹
//                if (inputFile.isFile()) {
//
//                    // 创建输入流读取文件
//                    FileInputStream fis = new FileInputStream(inputFile);
//                    BufferedInputStream bis = new BufferedInputStream(fis);
//
//                    // 将文件写入zip内，即将文件进行打包
//                    ZipEntry ze = new ZipEntry(inputFile.getName()); // 获取文件名
//                    zipoutputStream.putNextEntry(ze);
//
//                    // 写入文件的方法，同上
//                    byte[] b = new byte[1024];
//                    long l = 0;
//                    while (l < inputFile.length()) {
//                        int j = bis.read(b, 0, 1024);
//                        l += j;
//                        zipoutputStream.write(b, 0, j);
//                    }
//                    // 关闭输入输出流
//                    bis.close();
//                    fis.close();
//                }
//                else { // 如果是文件夹，则使用穷举的方法获取文件，写入zip
//                    try {
//                        File[] files = inputFile.listFiles();
//                        for (int i = 0; i < files.length; i++) {
//                            zipFile(files[i], zipoutputStream);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
