package utils;

import net.sf.json.JSONObject;
import exception.DBPathException;
import exception.SQLException;

import java.io.*;

/**
 * Created by hufan on 2017/5/29.
 */
public class CommunicationUtils {
    private static final String EXIT = ".quit";
    private static final String PATH_ERROR = "401";
    private static final String SQL_ERROR = "403";
    private static final String SUCCESS = "400";

    /**

     *
     * @param sql
     * @param process
     * @return
     * 返回结果为json
     *     [
     *     {"column_name1":"column_value1"},
     *     {"column_name1":"column_value1"},
     *     ...
     *     {"column_nameN":"column_valueN"}
     *     ]
     */
    public static String executeSQL(String sql, Process process) throws IOException, DBPathException, SQLException {
        //发送sql指令
        send(sql, process);
        //接受结果
        String readLine = receive(process);
        //解析结果
        return CommunicationUtils.getResult(readLine);
    }

    public static void exitSQL(Process process) throws IOException {
        //发送退出指令
        send(EXIT, process);
    }

    //只支持单行通讯
    private static void send(String msg, Process process) throws IOException {
        final BufferedWriter bufferedWriter = new BufferedWriter(
                new OutputStreamWriter(process.getOutputStream()));
        bufferedWriter.write(msg);
        bufferedWriter.write("\r\n");
        bufferedWriter.flush();
    }

    //只支持单行通讯
    private static String receive(Process process) throws IOException {
        final BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(process.getInputStream(), "gbk"));
         return bufferedReader.readLine();
    }

    private static String getResult(String readLine) throws DBPathException, SQLException {
        JSONObject jsonObject = JSONObject.fromObject(readLine);
        String code = jsonObject.getString("code");
        String result = jsonObject.getString("result");
        if (SUCCESS.equals(code)){
            return result;
        }
        if (SQL_ERROR.equals(code)){
            throw new SQLException(result);
        }
        if (PATH_ERROR.equals(code)) {
            throw new DBPathException(result);
        }
        return "";
    }
}
