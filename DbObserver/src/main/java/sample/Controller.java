package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.constant.DbTypeEnum;
import sample.service.ObserverService;
import sample.service.SqliteService;

import java.sql.SQLException;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Controller {
    @FXML
    RadioButton timeInterval10;
    @FXML
    RadioButton timeInterval20;
    @FXML
    RadioButton timeInterval30;
    @FXML
    Button startBtn;
    @FXML
    Button restartBtn;
    @FXML
    Button stopBtn;
    @FXML
    TextArea logTextArea;
    @FXML
    ChoiceBox dbStyleChoice;
    @FXML
    TextField urlTextField;
    @FXML
    TextField userNameTextField;
    @FXML
    TextField passwordTextField;

    private static ScheduledExecutorService executorService;

    private ScheduledExecutorService getExecutorService(){
        if(executorService == null)
            executorService = Executors.newSingleThreadScheduledExecutor();

        return executorService;
    }

    private void cleanExecutorService(){
        executorService = null;
    }

    public void selectDb(ActionEvent event){
        String dbSelected = String.valueOf(dbStyleChoice.getSelectionModel().getSelectedItem());

        String url = DbTypeEnum.getUrlByName(dbSelected);
        if(url != null){
            urlTextField.setText(url);
        }
    }
    public void goStart(ActionEvent event){
        startBtn.setDisable(true);
        restartBtn.setDisable(false);
        stopBtn.setDisable(false);

        startScheduler();
    }

    public void goRestart(ActionEvent event) throws SQLException {
        stopScheduler();
        startScheduler();
    }

    public void goStop(ActionEvent event) throws SQLException {
        startBtn.setDisable(false);
        restartBtn.setDisable(true);
        stopBtn.setDisable(true);
        stopScheduler();
    }

    private void startScheduler(){
        final Controller controller = this;
        final String dbUrl = urlTextField.getText();
        final String userName = userNameTextField.getText();
        final String password = passwordTextField.getText();

        println("dbUrl=" + dbUrl + ", userName=" + userName + ",pasword=" + password);
        getExecutorService().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

                ObserverService.getInstance(controller).observe(dbUrl, userName, password);
            }
        }, 0, getTimeInterval(), TimeUnit.SECONDS);
        println("observe started, current TimeInterval = " + getTimeInterval() +"\n");
    }

    private void stopScheduler() throws SQLException {
        SqliteService.closeAllInstance();
        ExecutorService service = getExecutorService();
        service.shutdown();
        cleanExecutorService();
        ObserverService.cleanInstance();
        println("observe stoped \n");
    }

    public void println(final Object object){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                logTextArea.appendText(object.toString() + "\n");
            }
        });

    }

    private int getTimeInterval(){
        int interval = 0;
        if(timeInterval10.isSelected())
            interval = 10;
        else if(timeInterval20.isSelected())
            interval = 20;
        else if (timeInterval30.isSelected())
            interval = 30;

        return interval;
    }
}
