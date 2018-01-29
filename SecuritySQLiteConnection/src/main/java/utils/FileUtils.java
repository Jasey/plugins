package utils;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

/**
 * Created by hufangjie on 2017/6/2.
 */
public class FileUtils {
    private static Logger LOGGER = Logger.getLogger(FileUtils.class);
    //解压jar
    public static void unJar(String source , String destinationDir) throws  IOException{
        File src = new File(source);
        File desDir = new File(destinationDir);
        JarInputStream jarIn = new JarInputStream(new BufferedInputStream(new FileInputStream(src)));
        if(!desDir.exists())desDir.mkdirs();
        byte[] bytes = new byte[1024];

        while(true){
            ZipEntry entry = jarIn.getNextJarEntry();
            if(entry == null)break;

            File desTemp = new File(desDir.getAbsoluteFile() + File.separator + entry.getName());

            if(entry.isDirectory()){    //jar条目是空目录
                if(!desTemp.exists())desTemp.mkdirs();
                LOGGER.info("MakeDir: " + entry.getName());
            }else{    //jar条目是文件
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(desTemp));
                int len = jarIn.read(bytes, 0, bytes.length);
                while(len != -1){
                    out.write(bytes, 0, len);
                    len = jarIn.read(bytes, 0, bytes.length);
                }

                out.flush();
                out.close();

                LOGGER.info("Copyed: " + entry.getName());
            }
            jarIn.closeEntry();
        }

        //解压Manifest文件
        Manifest manifest = jarIn.getManifest();
        if(manifest != null){
            File manifestFile = new File(desDir.getAbsoluteFile()+File.separator+JarFile.MANIFEST_NAME);
            if(!manifestFile.getParentFile().exists())manifestFile.getParentFile().mkdirs();
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(manifestFile));
            manifest.write(out);
            out.close();
        }

        //关闭JarInputStream
        jarIn.close();
    }
}
