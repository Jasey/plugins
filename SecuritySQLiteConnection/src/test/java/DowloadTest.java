import org.junit.Test;
import utils.DownLoadUtils;

/**
 * Created by hufangjie on 2017/6/2.
 */
public class DowloadTest {
    private static final String USER_LOCAL = System.getProperty("user.home") + "/AppData/Local";
    private static final String EXE_HOME = USER_LOCAL  + "/ssqlite";
    private static final String url = "file:/C:/Users/hufangjie/.m2/repository/com/project/SecuritySQLiteConnection/1.0-SNAPSHOT/SecuritySQLiteConnection-1.0-SNAPSHOT.jar";
    private static final String jarName = "SecuritySQLiteConnection-1.0-SNAPSHOT.jar";

    @Test
    public void downLoadTest() {
        try{
            //DownLoadUtils.downLoadFromUrl("http://101.95.48.97:8005/res/upload/interface/apptutorials/manualstypeico/6f83ce8f-0da5-49b3-bac8-fd5fc67d2725.png","百度.jpg","d:/resources/net/download/");
            //DownLoadUtils.downLoadFromUrl(url, jarName, EXE_HOME);
        }catch (Exception e) {
        // TODO: handle exception
        System.out.println(e.getMessage());
    }
    }
}
