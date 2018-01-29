package sample.service;

import conn.SecuritySQLiteConnection;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by fanzhijie on 2017/5/31.
 */
public class SqliteEncryptService {
    public static SecuritySQLiteConnection connection;


    public static SecuritySQLiteConnection getConnectionInstance(String dbUrl, String password) throws IOException {
        //System.out.println("get ConnectionInstance, connecttion=" + connection);
        if(connection == null){
            //System.out.println("get connection");
            connection = new SecuritySQLiteConnection(dbUrl, password);
            //System.out.println("get connection Success");
            connection.open();
        }

        return connection;
    }

    public static void closeConnection(){
        if(connection != null){
            try {
                connection.close();
                connection = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<String> getAllTables(String dbUrl, String password) {
        //System.out.println("enter getAllTables");
        List<String> tableList = new ArrayList<String>();
        try {

            SecuritySQLiteConnection connection = getConnectionInstance(dbUrl, password);

            String result = connection.executeSQL("select name from sqlite_master where type ='table' order by name");
            //System.out.println(result);
            JSONArray jsonArray = JSONArray.fromObject(result);

            Iterator<Object> iterator = jsonArray.iterator();
            while (iterator.hasNext()){
                JSONObject object = (JSONObject) iterator.next();
                String name = object.getString("name");
                if(name != null){
                    tableList.add(name);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(tableList);
        return tableList;
    }

    public static int getTableCount(String table, String dbUrl, String password){
        try {
            SecuritySQLiteConnection connection = getConnectionInstance(dbUrl, password);
            String sql = "select count(*) count from " + table;
            String result = connection.executeSQL(sql);
            JSONArray array = JSONArray.fromObject(result);
            Iterator<Object> iterator = array.iterator();
            if(iterator.hasNext()){
                JSONObject object = (JSONObject) iterator.next();
                return object.getInt("count");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return 0;
    }

    public static void main(String[] args) {
        List<String> list = getAllTables("E:/posDB.db", "pptouch");
        System.out.println(list);
    }
}
