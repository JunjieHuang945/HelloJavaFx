package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

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
    private Button stopBtn;
    @FXML
    private Button startBtn;
    @FXML
    private Button sendBtn;
    @FXML
    private GridPane keyButtGroup;
    private KeyBean[][] keys = new KeyBean[][]{
            new KeyBean[]{
                    new KeyBean(null, -1),
                    new KeyBean("↑", KeyEvent.KEYCODE_DPAD_UP),
            },
            new KeyBean[]{
                    new KeyBean("←", KeyEvent.KEYCODE_DPAD_LEFT),
                    new KeyBean("确认", KeyEvent.KEYCODE_DPAD_CENTER),
                    new KeyBean("→", KeyEvent.KEYCODE_DPAD_RIGHT),
            },
            new KeyBean[]{
                    new KeyBean(null, -1),
                    new KeyBean("↓", KeyEvent.KEYCODE_DPAD_DOWN),
            },
            new KeyBean[]{
                    new KeyBean("F1", KeyEvent.KEYCODE_F1),
                    new KeyBean("F2", KeyEvent.KEYCODE_F2),
                    new KeyBean("F3", KeyEvent.KEYCODE_F3),
                    new KeyBean("F4", KeyEvent.KEYCODE_F4),
                    new KeyBean("F5", KeyEvent.KEYCODE_F5),
                    new KeyBean("F6", KeyEvent.KEYCODE_F6),
                    new KeyBean("F7", KeyEvent.KEYCODE_F7),
                    new KeyBean("F8", KeyEvent.KEYCODE_F8),
                    new KeyBean("F9", KeyEvent.KEYCODE_F9),
                    new KeyBean("F10", KeyEvent.KEYCODE_F10),
                    new KeyBean("F11", KeyEvent.KEYCODE_F11),
                    new KeyBean("F12", KeyEvent.KEYCODE_F12),
            },
            new KeyBean[]{
                    new KeyBean("主页", KeyEvent.KEYCODE_HOME),
                    new KeyBean("返回", KeyEvent.KEYCODE_BACK),
                    new KeyBean("菜单", KeyEvent.KEYCODE_MENU),
                    new KeyBean("退格", KeyEvent.KEYCODE_DEL),
                    new KeyBean("回车", KeyEvent.KEYCODE_ENTER),
                    new KeyBean("删除", KeyEvent.KEYCODE_FORWARD_DEL),
                    new KeyBean("设置", KeyEvent.KEYCODE_SETTINGS),
                    new KeyBean("V+", KeyEvent.KEYCODE_VOLUME_UP),
                    new KeyBean("V-", KeyEvent.KEYCODE_VOLUME_DOWN),
                    new KeyBean("静音", KeyEvent.KEYCODE_VOLUME_MUTE),
            },
            new KeyBean[]{
                    new KeyBean("0", KeyEvent.KEYCODE_NUMPAD_0),
                    new KeyBean("1", KeyEvent.KEYCODE_NUMPAD_1),
                    new KeyBean("2", KeyEvent.KEYCODE_NUMPAD_2),
                    new KeyBean("3", KeyEvent.KEYCODE_NUMPAD_3),
                    new KeyBean("4", KeyEvent.KEYCODE_NUMPAD_4),
                    new KeyBean("5", KeyEvent.KEYCODE_NUMPAD_5),
                    new KeyBean("6", KeyEvent.KEYCODE_NUMPAD_6),
                    new KeyBean("7", KeyEvent.KEYCODE_NUMPAD_7),
                    new KeyBean("8", KeyEvent.KEYCODE_NUMPAD_8),
                    new KeyBean("9", KeyEvent.KEYCODE_NUMPAD_9),
            },
    };

    public Controller() {
        runtimeHelper = new RuntimeHelper();
        mConnfigProperties = new ConnfigProperties();
    }

    /**
     * 初始化view
     */
    public void initView() {
        for (int rowIndex = 0; rowIndex < keys.length; rowIndex++) {
            KeyBean[] rowKeys = keys[rowIndex];
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
        String ip = mConnfigProperties.getIp();
        if (ip != null) {
            ipInput.setText(ip);
        }
    }

    public void sendKeyCode(int keyCode, Button view) {
        view.setDisable(true);
        runtimeHelper.execV("adb shell input keyevent " + keyCode, () -> view.setDisable(false));
    }

    public void connectDevice(ActionEvent actionEvent) {
        connectBtn.setDisable(true);
        String inputIp = ipInput.getText();
        runtimeHelper.execS("adb connect " + inputIp, (s) -> {
            System.out.println(s);
            connectBtn.setDisable(false);
            mConnfigProperties.setIp(inputIp);
            mConnfigProperties.save();
        });
    }

    public void stopAdb(ActionEvent actionEvent) {
        stopBtn.setDisable(true);
        runtimeHelper.execV("adb kill-server", () -> stopBtn.setDisable(false));
    }

    public void startAdb(ActionEvent actionEvent) {
        startBtn.setDisable(true);
        runtimeHelper.execV("adb start-server", () -> startBtn.setDisable(false));
    }

    public void sedText(ActionEvent actionEvent) {
        sendBtn.setDisable(true);
        String text = textInput.getText();
        textInput.setText("");
        runtimeHelper.execV("adb shell input text \"" + text + "\"", () -> sendBtn.setDisable(false));
        textInput.requestFocus();
    }
}
