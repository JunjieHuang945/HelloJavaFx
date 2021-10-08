package top.foxhome.top.adbutil.bean;

public class CmdKeyBean {
    private String keyName;
    private String cmd;

    public CmdKeyBean() {
    }

    public CmdKeyBean(String keyName, String cmd) {
        this.keyName = keyName;
        this.cmd = cmd;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    @Override
    public String toString() {
        return "CmdKeyBean{" +
                "keyName='" + keyName + '\'' +
                ", cmd='" + cmd + '\'' +
                '}';
    }
}
