package sample.service;

import sample.util.SqlManagerUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fanzhijie on 2017/5/27.
 */
public class SqliteService {
    private static Connection conn;
    private static Statement state;

    public static Connection getConnInstance(String dbUrl, String userName, String password) throws SQLException, ClassNotFoundException {
        if(conn == null){
            conn = SqlManagerUtil.getSqlConnection(dbUrl, userName, password);
            //conn = SqlManagerUtil.getSqlConnection("jdbc:sqlite://d:/Users.db", "", "");
            //conn = SqlManagerUtil.getSqlConnection("jdbc:sqlite://C:/Program Files/君容开店宝/etc/Users.db", "", "");
        }
        return conn;
    }

    public static Statement getStateInstance(String dbUrl, String userName, String password) throws SQLException, ClassNotFoundException {
        if(state == null){
            state = getConnInstance(dbUrl, userName, password).createStatement();
        }
        return state;
    }

    public static List<String> getAllTables(String dbUrl, String userName, String password) throws SQLException, ClassNotFoundException {

        ResultSet rs = getStateInstance(dbUrl, userName, password).executeQuery("select name from sqlite_master where type ='table' order by name");
        List<String> list = new ArrayList<String>();
        while (rs.next()){
            list.add(rs.getString(1));
        }

        return list;
    }

    public static void closeAllInstance() throws SQLException {
        if(state != null){
            state.close();
            state = null;
        }
        if(conn != null){
            conn.close();
            conn = null;
        }
    }
}
