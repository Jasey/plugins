import conn.SecuritySQLiteConnection;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by hufan on 2017/5/29.
 */
public class SecuritySQLiteTest {
    private static final String PATH = "F:\\posDB.db";
    private static final String PWD = "pptouch";
    @Test
    public void testMultiSelect(){
        String dbPath = PATH;
        String password = PWD;
        SecuritySQLiteConnection connection = new SecuritySQLiteConnection(dbPath, password);
        try {
            connection.open();
            System.out.println(connection.executeSQL("select * from user"));
            System.out.println(connection.executeSQL("select * from usingtable"));
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void testSingleSelect(){
        String dbPath = PATH;
        String password = PWD;
        SecuritySQLiteConnection connection = new SecuritySQLiteConnection(dbPath, password);
        try {
            connection.open();
            System.out.println(connection.executeSQL("select * from user"));
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void testNoPath(){
        String dbPath = "";
        String password = "";
        SecuritySQLiteConnection connection = new SecuritySQLiteConnection(dbPath, password);
        try {
            connection.open();
            System.out.println(connection.executeSQL("select * from user"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void testWrongSQL(){
        String dbPath = PATH;
        String password = PWD;
        SecuritySQLiteConnection connection = new SecuritySQLiteConnection(dbPath, password);
        try {
            connection.open();
            System.out.println(connection.executeSQL("select * from table"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    @Test
    public void testCloseConn(){
        String dbPath = PATH;
        String password = PWD;
        SecuritySQLiteConnection connection = new SecuritySQLiteConnection(dbPath, password);
        try {
            connection.open();
            connection.close();
            System.out.println(connection.executeSQL("select * from user"));
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void testSetPasswordAfter(){
        String dbPath = PATH;
        String password = PWD;
        SecuritySQLiteConnection connection = new SecuritySQLiteConnection(dbPath);
        try {
            connection.open();
            connection.setPassword(password);
            System.out.println(connection.executeSQL("select * from user"));
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void testSetPassword(){
        String dbPath = PATH;
        String password = PWD;
        SecuritySQLiteConnection connection = new SecuritySQLiteConnection(dbPath);
        try {
            connection.open();
            connection.close();
            connection.setPassword(password);
            connection.open();
            System.out.println(connection.executeSQL("select * from user"));
        } catch (Exception e) {
            e.printStackTrace();
        }  finally {
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
