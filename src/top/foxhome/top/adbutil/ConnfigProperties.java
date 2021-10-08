package top.foxhome.top.adbutil;

import java.io.*;
import java.util.Properties;

public class ConnfigProperties {
    private Properties mProperties;
    private final static String CONNFIG_NAME = "./Connfig";

    public ConnfigProperties() {
        File mFile = new File(CONNFIG_NAME);
        mProperties = new Properties();
        if (mFile.exists()) {
            try {
                InputStream inputStream = new FileInputStream(CONNFIG_NAME);
                mProperties.loadFromXML(inputStream);
                mProperties.list(System.out);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setIp(String ip) {
        mProperties.setProperty("ip", ip);
    }

    public String getIp() {
        return mProperties.getProperty("ip");
    }

    public void setCmds(String cmds) {
        mProperties.setProperty("cmds", cmds);
    }

    public String getCmds() {
        return mProperties.getProperty("cmds");
    }

    public void save() {
        try {
            PrintStream fW = new PrintStream(new File(CONNFIG_NAME));
            mProperties.storeToXML(fW, "test");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
