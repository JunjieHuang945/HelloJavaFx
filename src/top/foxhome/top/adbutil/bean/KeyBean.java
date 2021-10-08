package top.foxhome.top.adbutil.bean;

public class KeyBean {
    private String keyName;
    private int keyCode;

    public KeyBean(String keyName, int keyCode) {
        this.keyName = keyName;
        this.keyCode = keyCode;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }
}
