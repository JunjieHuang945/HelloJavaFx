package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class RuntimeHelper {
    private Runtime mRuntime;

    public RuntimeHelper() {
        mRuntime = Runtime.getRuntime();
    }

    public void newRuntime() {
        mRuntime.exit(0);
        mRuntime = Runtime.getRuntime();
    }

    public void execV(String cmd, OnExecCallBackFinish callBack) {
        new Thread(() -> {
            execP(cmd);
            callBack.onCallBack();
        }).start();
    }

    public Process execP(String cmd) {
        System.out.println(cmd);
        Process mProcess = null;
        try {
            mProcess = mRuntime.exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mProcess;
    }

    public void execS(String cmd, OnExecCallBackText callBack) {
        new Thread(() -> {
            StringBuffer sb = new StringBuffer();
            Process mProcess = execP(cmd);
            InputStream inputStream = mProcess.getInputStream();
            BufferedReader mBufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String tempStr = null;
            try {
                while ((tempStr = mBufferedReader.readLine()) != null) {
                    sb.append(tempStr);
                    sb.append("\r\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            callBack.onCallBack(sb.toString());
        }).start();
    }

    public interface OnExecCallBackText {
        public void onCallBack(String msg);
    }

    public interface OnExecCallBackFinish {
        public void onCallBack();
    }
}
