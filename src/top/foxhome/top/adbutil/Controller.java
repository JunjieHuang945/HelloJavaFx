package top.foxhome.top.adbutil;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import top.foxhome.top.adbutil.bean.CmdKeyBean;
import top.foxhome.top.adbutil.bean.KeyBean;
import top.foxhome.top.adbutil.call.OnExecProgressCallBack;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class Controller {
    private ConnfigProperties mConnfigProperties;
    @FXML
    private TextField ipInput;
    @FXML
    private TextField textInput;
    private RuntimeHelper runtimeHelper;
    @FXML
    private Button connectBtn;
    @FXML
    private Button disconnectBtn;
    @FXML
    private Button stopBtn;
    @FXML
    private Button startBtn;
    @FXML
    private Button sendBtn;
    @FXML
    private Button cmdBtn;
    @FXML
    private TextArea cmdIput;
    @FXML
    private GridPane keyButtGroup;
    @FXML
    private TextArea cmdOutPut;
    @FXML
    private Button rebootBtn;
    @FXML
    private Button installApk;
    @FXML
    private Text adbVersion;
    @FXML
    private GridPane cmdButtGroup;
    @FXML
    private Text devicesInfo;
    private DevicesInfoTask updateDevicesInfoTask;

    public Controller() {
        runtimeHelper = new RuntimeHelper();
        mConnfigProperties = new ConnfigProperties();
    }

    /**
     * 初始化view
     */
    public void initView() {
        //构建快捷按钮
        for (int rowIndex = 0; rowIndex < KeyCodeConfig.KEYS_QUICK.length; rowIndex++) {
            KeyBean[] rowKeys = KeyCodeConfig.KEYS_QUICK[rowIndex];
            for (int columnIndex = 0; columnIndex < rowKeys.length; columnIndex++) {
                KeyBean columnKey = rowKeys[columnIndex];
                if (columnKey.getKeyName() == null) {
                    Pane mPane = new Pane();
                    mPane.setMinSize(30, 20);
                    keyButtGroup.add(mPane, columnIndex + 1, rowIndex + 1);//int columnIndex, int rowIndex
                } else {
                    Button mKeyButton = new Button();
                    mKeyButton.setText(columnKey.getKeyName());
                    mKeyButton.setOnAction((ActionEvent actionEvent) -> {
                        sendKeyCode(columnKey.getKeyCode(), mKeyButton);
                    });
                    mKeyButton.setMaxSize(45, 30);
                    keyButtGroup.add(mKeyButton, columnIndex + 1, rowIndex + 1);//int columnIndex, int rowIndex
                }
            }
        }

        //构建快捷按钮
        for (int rowIndex = 0; rowIndex < KeyCodeConfig.KEYS_TIPS.length; rowIndex++) {
            CmdKeyBean[] rowKeys = KeyCodeConfig.KEYS_TIPS[rowIndex];
            for (int columnIndex = 0; columnIndex < rowKeys.length; columnIndex++) {
                CmdKeyBean columnKey = rowKeys[columnIndex];
                Button mKeyButton = new Button();
                mKeyButton.setText(columnKey.getKeyName());
                mKeyButton.setOnAction((ActionEvent actionEvent) -> {
                    appendCmd(columnKey.getCmd());
                });
                mKeyButton.setMaxSize(80, 30);
                cmdButtGroup.add(mKeyButton, columnIndex + 1, rowIndex + 1);
            }
        }

        //构建相关显示信息
        String ip = mConnfigProperties.getIp();
        if (ip != null)
            ipInput.setText(ip);

        String cmds = mConnfigProperties.getCmds();
        if (cmds != null)
            cmdIput.setText(cmds);
        String cmd = "adb version";
        runtimeHelper.exec(cmd,
                msg -> adbVersion.setText(msg)
        );

        updateDevicesInfoTask = new DevicesInfoTask(s -> devicesInfo.setText(s));
        new Thread(updateDevicesInfoTask).start();
    }

    /**
     * 发送按键事件
     *
     * @param keyCode
     * @param view
     */
    public void sendKeyCode(int keyCode, final Button view) {
        view.setDisable(true);
        String cmd = "adb shell input keyevent " + keyCode;
        appendLog(cmd);
        runtimeHelper.exec(cmd,
                msg -> view.setDisable(false)
        );
    }

    /**
     * 连接设备
     *
     * @param actionEvent
     */
    public void connectDevice(ActionEvent actionEvent) {
        connectBtn.setDisable(true);
        String inputIp = ipInput.getText();
        String cmd = "adb connect " + inputIp;
        appendLog(cmd);
        runtimeHelper.exec(cmd, msg -> {
            System.out.println(msg);
            connectBtn.setDisable(false);
            mConnfigProperties.setIp(inputIp);
            mConnfigProperties.save();
            appendLog(msg);
        });
    }

    /**
     * 执行命令
     *
     * @param actionEvent
     */
    public void runCmd(ActionEvent actionEvent) {
        String cmdStr = cmdIput.getText();
        mConnfigProperties.setCmds(cmdStr);
        mConnfigProperties.save();
        String[] cmds = cmdStr.split("\n");
        List<String> cmdArr = new LinkedList<String>();
        for (String cmd : cmds) {
            if (cmd.trim().equals("")) continue;
            cmdArr.add(cmd.trim());
        }
        if (cmdArr.size() == 0) {
            cmdBtn.setDisable(false);
        } else {
            runtimeHelper.exec(cmdArr, new OnExecProgressCallBack<String>() {
                @Override
                public void onPrepare() {
                    cmdBtn.setDisable(true);
                    cmdIput.setDisable(true);
                }

                @Override
                public void onProgress(String msg) {
                    appendLog(msg);
                }

                @Override
                public void onFinish() {
                    cmdBtn.setDisable(false);
                    cmdIput.setDisable(false);
                }
            });
        }

    }

    /**
     * 停止adb
     *
     * @param actionEvent
     */
    public void stopAdb(ActionEvent actionEvent) {
        stopBtn.setDisable(true);
        String cmd = "adb kill-server";
        appendLog(cmd);
        runtimeHelper.exec(cmd, msg -> {
            appendLog(msg);
            stopBtn.setDisable(false);
        });
    }

    /**
     * 启动adb
     *
     * @param actionEvent
     */
    public void startAdb(ActionEvent actionEvent) {
        startBtn.setDisable(true);
        String cmd = "adb start-server";
        appendLog(cmd);
        runtimeHelper.exec(cmd, msg -> {
            startBtn.setDisable(false);
            appendLog(msg);
        });
    }

    /**
     * 发送文本
     *
     * @param actionEvent
     */
    public void sedText(ActionEvent actionEvent) {
        sendBtn.setDisable(true);
        String text = textInput.getText();
        textInput.setText("");
        String cmd = "adb shell input text \"" + text + "\"";
        appendLog(cmd);
        System.out.println("sedText:" + Thread.currentThread().getName());
        runtimeHelper.exec(cmd, msg -> {
            sendBtn.setDisable(false);
            appendLog(msg);
        });
        textInput.requestFocus();
    }

    /**
     * 将命令添加到输入框
     *
     * @param cmd
     */
    private void appendCmd(String cmd) {
        StringBuffer cmdsSb = new StringBuffer(cmdIput.getText());
        if (cmdsSb.length() == 0) {
            cmdsSb.append(cmd);
        } else {
            cmdsSb.append("\r\n");
            cmdsSb.append(cmd);
        }
        cmdIput.setText(cmdsSb.toString());
    }

    private List<String> logs = new LinkedList<String>();

    /**
     * 添加log并且显示
     *
     * @param str
     */
    private void appendLog(String str) {
        if (str == null || str.equals("") || str.equals("\r\n")) return;
        logs.add(String.format("%s\r\n", str));
        StringBuffer sb = new StringBuffer();
        for (String log : logs) {
            sb.append(log);
        }
        cmdOutPut.setText(sb.toString());
        cmdOutPut.setScrollTop(Double.MAX_VALUE);
    }

    /**
     * 清理日志
     */
    private void cleanLog() {
        logs.clear();
        cmdOutPut.setText("");
    }

    /**
     * 重启设备
     *
     * @param actionEvent
     */
    public void rebootDevice(ActionEvent actionEvent) {
        rebootBtn.setDisable(true);
        String cmd = "adb reboot";
        appendLog(cmd);
        runtimeHelper.exec(cmd, msg -> {
            rebootBtn.setDisable(false);
            appendLog(msg);
        });
    }

    private Window ownerWindow;

    public void setOwnerWindow(Window ownerWindow) {
        this.ownerWindow = ownerWindow;
    }

    /**
     * 安装apk
     *
     * @param actionEvent
     */
    public void installApk(ActionEvent actionEvent) {
        installApk.setDisable(true);
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("APK", "*.apk"));
        fileChooser.setTitle("选择APK");
        File mFile = fileChooser.showOpenDialog(ownerWindow);
        if (mFile != null && mFile.exists()) {
            String cmd = "adb install -r " + mFile.getAbsoluteFile();
            appendLog(cmd);
            runtimeHelper.exec(cmd, msg -> {
                installApk.setDisable(false);
                appendLog(msg);
            });
        }
    }

    /**
     * 清空命令输入框
     *
     * @param actionEvent
     */
    public void cleanCmd(ActionEvent actionEvent) {
        cmdIput.setText("");
    }

    /**
     * 清空日志框
     *
     * @param actionEvent
     */
    public void cleanLogBtn(ActionEvent actionEvent) {
        cleanLog();
    }

    public void disconnectDevice(ActionEvent actionEvent) {
        {
            disconnectBtn.setDisable(true);
            String inputIp = ipInput.getText();
            String cmd = "adb disconnect " + inputIp;
            appendLog(cmd);
            runtimeHelper.exec(cmd, msg -> {
                System.out.println(msg);
                disconnectBtn.setDisable(false);
                mConnfigProperties.setIp(inputIp);
                mConnfigProperties.save();
                appendLog(msg);
            });
        }
    }

    public void interruptCmd(ActionEvent actionEvent) {
        cmdBtn.setDisable(false);
        cmdIput.setDisable(false);
        runtimeHelper.newRuntime();
    }

    public void release() {
        updateDevicesInfoTask.stopUpdate();
    }
}
