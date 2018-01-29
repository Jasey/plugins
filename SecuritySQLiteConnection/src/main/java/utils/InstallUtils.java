package utils;

import java.io.File;
import java.io.IOException;

/**
 * Created by hufan on 2017/6/1.
 */
public class InstallUtils {
    private static final String CLASSPATH = InstallUtils.class.getResource("").getFile().toString();
    private static final String USER_LOCAL = System.getProperty("user.home") + "/AppData/Local";
    private static final String EXE_HOME = USER_LOCAL  + "/Security SQLite";
    private static final String TEMP = "/.temp";
    private static final String EXE_NAME = "/ssqlite/SecuritySQLite.exe";

    /**
     * 返回安装文件路径
     * @return
     */
    public static String install() throws IOException {
        //检测文件是否存在
        if (InstallUtils.isExeInstalled(EXE_HOME)){
            return EXE_HOME + EXE_NAME;
        }
        //检测文件是代码形式还是jar包形式
        if (CLASSPATH.contains("jar")) {
            //获取exe安装包url
            String url = getLocalJarURL(CLASSPATH);

            String jarName = getLocalJarName(url);
            //下载安装包
            DownLoadUtils.downLoadFromUrl(url, jarName, EXE_HOME + TEMP);
            //解压安装
            String filePath = EXE_HOME + TEMP + File.separator + jarName;
            FileUtils.unJar(filePath, EXE_HOME);
            return EXE_HOME + EXE_NAME;
        }//代码形式
        else {
            File classFile = new File(CLASSPATH);
            String root = classFile.getParent();
            return root + EXE_NAME;
        }

    }

    public static boolean isExeInstalled(String exe_home){
        File file = new File(exe_home);
        if (file.exists() && file.isDirectory()){
            return true;
        }
        return false;
    }

    public static String getLocalJarURL(String classPath){
        //"!"之前的即为url
        int lastIndex = classPath.lastIndexOf("!");
        return classPath.substring(0, lastIndex);
    }

    public static String getLocalJarName(String url){
        int beginIndex = url.lastIndexOf("/") + 1;
        return url.substring(beginIndex, url.length());
    }
}
