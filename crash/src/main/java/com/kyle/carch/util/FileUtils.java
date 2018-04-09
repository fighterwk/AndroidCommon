package com.kyle.carch.util;

import android.os.Environment;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description描述:
 * @Author作者: Kyle
 * @Date日期: 2018/4/9
 */
public class FileUtils {

    private FileUtils() {
        throw new UnsupportedOperationException();
    }


    /**
     * 外部存储器是否已装载
     *
     * @return
     */
    public static boolean isMounted() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            return true;
        } else {
            return false;
        }
    }


    public static boolean mkDirs(String path) {
        return mkDirs(new File(path));
    }

    public static boolean mkDirs(File path) {
        if (path.exists()) {
            return true;
        }
        return path.mkdirs();
    }

    public static boolean newFile(String file) {
        return newFile(new File(file));
    }

    public static boolean newFile(File file) {
        if (file.exists()) {
            return true;
        }

        if (mkDirs(file.getParentFile())) {
            try {
                return file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public static List<File> getFileListInDir(String dir) {
        return getFileListInDir(new File(dir), null);
    }

    /**
     * 获取文件列表
     *
     * @param dir
     * @return
     */
    public static List<File> getFileListInDir(File dir, FileFilter fileFilter) {
        if (!dir.exists()) {
            return new ArrayList<>();
        }

        return Arrays.asList(dir.listFiles(fileFilter));
    }


    /**
     * 删除目录里面的指定文件
     * @param dir
     * @param fileFilter
     * @return
     */
    public static boolean delFileInDir(File dir, FileFilter fileFilter) {
        List<File> fileList = getFileListInDir(dir, fileFilter);
        for (File file : fileList) {
            if (file.exists() && file.isFile()) {
                file.delete();
            }
        }

        return true;
    }
}
