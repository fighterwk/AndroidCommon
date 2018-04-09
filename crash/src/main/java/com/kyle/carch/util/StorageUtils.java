package com.kyle.carch.util;

import android.content.Context;

import java.io.File;

/**
 * @Description描述:
 * @Author作者: Kyle
 * @Date日期: 2018/4/9
 */
public class StorageUtils {

    /**
     * 获取默认存储路径
     *
     * @param context
     * @return
     */
    public static final File getDefaultStorageDir(Context context) {
        File dir = null;
        if (FileUtils.isMounted()) {
            dir = context.getExternalCacheDir().getParentFile();
        }

        return new File(dir, "crash");
    }

}
