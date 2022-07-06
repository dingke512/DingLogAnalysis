package com.dingke.myapp.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileUtils {

    // 图片允许的后缀扩展名
    public static String[] IMAGE_FILE_EXTD = new String[] { "png", "bmp", "jpg", "jpeg","pdf","xlsx","xls" };
    public static String[] IMAGE_FILE_EXTD1 = new String[] { "png", "bmp", "jpg", "jpeg"};
    public static String[] IMAGE_FILE_EXTD2 = new String[] { "pdf"};
    public static String[] IMAGE_FILE_EXTD3 = new String[] { "xlsx","xls"};
    public static boolean isFileAllowed(String fileName) {
        for (String ext : IMAGE_FILE_EXTD) {
            if (ext.equals(fileName)) {
                return true;
            }
        }
        return false;
    }
    public static boolean isFileAllowed(String fileName,String[] file) {
        for (String ext : file) {
            if (ext.equals(fileName)) {
                return true;
            }
        }
        return false;
    }


//     * 唯一识别的UUID

    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }


//     * 获取文件后缀

    public static String getSuffix(String fileName){
        return fileName.substring(fileName.lastIndexOf("."));
    }


//  * 和随机的UUID生成新的文件名

    public static String getFileName(String fileOriginName){
        return getUUID() + getSuffix(fileOriginName);
    }


//     * 保存文件到本地,并返回新的唯一文件名

    public static String upload(MultipartFile file, String path, String fileName){
        String newFileName = getFileName(fileName);
        // 生成新的文件名
        String realPath = path + "/" + newFileName;
        File dest = new File(realPath);
        //判断文件父目录是否存在
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdir();
        }
        try {
            //保存文件
            file.transferTo(dest);
            return newFileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
