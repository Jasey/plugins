package conn;

import exception.DBPathException;
import exception.PassWordException;
import exception.SQLException;
import exception.SQLiteConnectionException;
import utils.CommunicationUtils;

import java.io.*;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import utils.InstallUtils;

/**
 * Created by hufan on 2017/5/29.
 */
public class SecuritySQLiteConnection {
    private static final String OPEN = "open";
    private static final String CLOSE = "close";

    private static Logger LOGGER = Logger.getLogger(SecuritySQLiteConnection.class);

    private Process process;
    private String status = CLOSE;
    private String exec;
    private String path;
    private String password;

    /**
     *
     * @param path
     * @param password
     */
    public SecuritySQLiteConnection(String path, String password) {
        this.path = path;
        this.password = password;
    }

    public SecuritySQLiteConnection(String path) {
        this.path = path;
    }

    public void setPassword(String password) throws PassWordException {
        if (OPEN.equals(status)){
            throw new PassWordException("password must be set before connection open!");
        }else {
            this.password = password;
        }
    }

    public SecuritySQLiteConnection open() throws IOException {
        //安装，注意打包之后，不能直接运行，需要解压，
        //exe位置在jar包的conn目录下


        exec = InstallUtils.install();

        //启动
        if (CLOSE.equals(status)) {
            ArrayList<String> commands = new ArrayList<String>();
            commands.add(exec);
            commands.add(path);
            commands.add(password);
            process = new ProcessBuilder(commands).start();
            status = OPEN;
        }
        return this;
    }

    public String executeSQL(String sql) throws IOException, DBPathException, SQLException, SQLiteConnectionException {
        if (status.equals(CLOSE)){
            throw new SQLiteConnectionException("connection be closed!");
        }
        return CommunicationUtils.executeSQL(sql, process);
    }

    public void close() throws IOException {
        if (process == null){
            return;
        }

        //发送退出信息
        CommunicationUtils.exitSQL(process);
        //关闭输入输出流
        process.getInputStream().close();
        process.getOutputStream().close();
        process.destroy();
        process = null;

        status = CLOSE;
    }

    public boolean testConnection(){
        if (CLOSE.equals(status)){
            return false;
        }
        try {
            executeSQL("select * from sqlite_master limit 1");
        } catch (Exception e) {
            LOGGER.error("SQLite connect error", e);
            return false;
        }
        return true;
    }
}
