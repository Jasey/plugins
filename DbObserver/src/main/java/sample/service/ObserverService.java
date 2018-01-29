package sample.service;

import sample.Controller;
import sample.constant.ObserveConst;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by fanzhijie on 2017/5/27.
 */
public class ObserverService {

    private static ObserverService observerService;

    public static ObserverService getInstance(Controller controller){
        if(observerService == null)
            observerService = new ObserverService(controller);

        return observerService;
    }

    private ObserverService(Controller observerController) {
        this.observer = observerController;
    }

    private Controller observer;

    private Map<String, Integer> tableMap = new HashMap<String, Integer>();

    public static void cleanInstance(){
        observerService = null;
    }

    public synchronized void observe(String dbUrl, String userName, String password) {
        //Map<String, Integer> tableMap = new TreeMap<>();
        BufferedWriter writer = null;
        try {
            Map<String, String> changeMap = new HashMap<String, String>();
            List<String> tableNameList = SqliteEncryptService.getAllTables(dbUrl, password);
            //Statement stmt = SqliteService.getStateInstance(dbUrl, userName, password);
            for (String table : tableNameList) {
                int cnt = SqliteEncryptService.getTableCount(table, dbUrl, password);
                /*String querySql = "select count(*) count from " + table;
                ResultSet rs = stmt.executeQuery(querySql);
                if (rs.next()) {
                    int cnt = rs.getInt(1);*/
                    Integer preValue = tableMap.get(table);
                    if (preValue == null || preValue != cnt) {
                        System.out.println("table[" + table + "] changed");
                        changeMap.put(table, preValue + "-->" + cnt);
                    }
                    tableMap.put(table, cnt);
                //}
                //rs.close();
            }

            //输出改变记录
            StringBuilder changeBuilder = new StringBuilder();
            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHH");
            changeBuilder.append("-----------------------------------Observe Time @" + dateTime + "-----------------------------------\r\n");
            for (Map.Entry<String, String> entry : changeMap.entrySet()) {
                changeBuilder.append(entry.getKey() + "  ::  " + entry.getValue() + "\r\n");
            }

            String fileName = dateTime.format(formatter) + ".log";

            File filePath = new File(ObserveConst.LOG_FILE_PATH);
            if(!filePath.exists()){
                filePath.mkdirs();
            }
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(ObserveConst.LOG_FILE_PATH + fileName, true)));
            writer.write(changeBuilder.toString());
            observer.println(changeBuilder);
        } catch (IOException e) {
            e.printStackTrace();
            observer.println(e);
        } finally {
            try {
                writer.close();
                SqliteEncryptService.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
                observer.println(e);
            }
        }
    }
}
