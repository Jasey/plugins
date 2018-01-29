package sample.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by fanzhijie on 2017/5/27.
 */
public class SqlManagerUtil {

    public static Connection getSqlConnection(String url, String username, String password) throws ClassNotFoundException, SQLException {
        //Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        return DriverManager.getConnection(url, username, password);
    }
}
