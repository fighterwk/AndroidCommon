package com.kyle.carch;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Process;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.kyle.carch.activity.DetailsActivity;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * @Description描述: 异常信息处理
 * @Author作者: Kyle
 * @Date日期: 2018/4/9
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static final CrashHandler instance;

    static {
        instance = new CrashHandler();
    }


    public static final void init(Context context) {
        instance.context = context.getApplicationContext();
        Thread.setDefaultUncaughtExceptionHandler(instance);
    }

    public static final CrashHandler getInstance() {
        if (instance.context == null) {
            throw new ExceptionInInitializerError("请先调用 init()方法");
        }
        return instance;
    }

    private Context context;

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (handleException(t, e)) {
        }
    }

    private boolean handleException(Thread t, Throwable e) {
        // 保存异常信息
        // 线程名称
        String threadName = t.getName();

        // 异常信息
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        e.printStackTrace(printWriter);
        Throwable cause = e.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.flush();
        printWriter.close();
        String result = writer.toString();

        // 如果是debug模式打印日志
        if (BuildConfig.DEBUG) {
            e.printStackTrace();
        }

        // 通知用户
        notifyUser(context, result);
        // 退出程序
        Process.killProcess(Process.myPid());
        return true;
    }


    public void notifyUser(Context context, String exceptionInfo) {
        String appName = "应用名称";
        try {
            ApplicationInfo applicationInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), 0);
            int labelRes = applicationInfo.labelRes;
            appName = context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher_round);
        builder.setContentTitle(String.format("%s发生异常", appName));
        builder.setContentInfo("点击查看详情");
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(DetailsActivity.EXTRA_EXCEPTION, exceptionInfo);
        PendingIntent pi = PendingIntent
                .getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(pi);

        NotificationManagerCompat.from(context)
                .notify(1, builder.build());
    }
}
