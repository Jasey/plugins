import conn.SecuritySQLiteConnection;

import java.io.IOException;

/**
 * Created by hufan on 2017/5/29.
 */
public class Start {
    private static final String PATH = "E:/posDB.db";
    private static final String PWD = "pptouch";
    public static void main(String[] args){
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
}
