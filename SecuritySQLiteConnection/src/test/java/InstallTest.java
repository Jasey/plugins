import org.junit.Test;
import utils.InstallUtils;

/**
 * Created by hufan on 2017/6/1.
 */
public class InstallTest {
    private static final String CLASSPATH = "file:/C:/Users/hufan/.m2/repository/com/project/SecuritySQLiteConnection/1.0-SNAPSHOT/SecuritySQLiteConnection-1.0-SNAPSHOT.jar!/utils/";
    private static final String url = "file:/C:/Users/hufan/.m2/repository/com/project/SecuritySQLiteConnection/1.0-SNAPSHOT/SecuritySQLiteConnection-1.0-SNAPSHOT.jar";
    private static final String jarName = "SecuritySQLiteConnection-1.0-SNAPSHOT.jar";
    @Test
    public void testURL(){
        assert url.equals(InstallUtils.getLocalJarURL(CLASSPATH));
    }

    @Test
    public void testJarName(){
        String url = InstallUtils.getLocalJarURL(CLASSPATH);
        assert jarName.equals(InstallUtils.getLocalJarName(url));
    }
}
