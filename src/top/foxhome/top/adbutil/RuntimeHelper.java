package top.foxhome.top.adbutil;

import javafx.concurrent.Task;
import top.foxhome.top.adbutil.call.OnExecCallBack;
import top.foxhome.top.adbutil.call.OnExecProgressCallBack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RuntimeHelper {
    private ExecutorService cachedThreadPool;
    private Runtime mRuntime;

    public RuntimeHelper() {
        cachedThreadPool = Executors.newFixedThreadPool(5);
        mRuntime = Runtime.getRuntime();
    }

    public void newRuntime() {
        mRuntime.exit(0);
        mRuntime = Runtime.getRuntime();
    }

    public void exec(List<String> cmds, OnExecProgressCallBack callBack) {
        System.out.println("cmds length:" + cmds.size());
        callBack.onPrepare();
        cachedThreadPool.execute(new Task<Boolean>() {
            @Override
            protected Boolean call() throws Exception {
                for (String cmd : cmds) {
                    Process mProcess = null;
                    try {
                        System.out.println("cmds:" + cmd);
                        mProcess = mRuntime.exec(cmd);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (mProcess != null) {
                        String msg = getStringByProcess(mProcess);
                        updateMessage(msg);
                    }
                }
                return true;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                callBack.onFinish();
            }

            @Override
            protected void failed() {
                super.failed();
                callBack.onFinish();
            }

            @Override
            protected void updateMessage(String message) {
                super.updateMessage(message);
                callBack.onProgress(message);
            }
        });
    }

    public void exec(String cmd, OnExecCallBack<String> callBack) {
        System.out.println(cmd);
        cachedThreadPool.execute(new Task<String>() {
            @Override
            protected String call() throws Exception {
                System.out.println("call:" + cmd);
                Process mProcess = null;
                try {
                    mProcess = mRuntime.exec(cmd);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (mProcess != null)
                    return getStringByProcess(mProcess);
                else
                    return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                callBack.onCallBack(getValue());
            }

            @Override
            protected void failed() {
                super.failed();
            }
        });
        /*cachedThreadPool.execute(() -> {

            Process mProcess = null;
            try {
                mProcess = mRuntime.exec(cmd);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (mProcess != null && callBack != null) {
                Process finalMProcess = mProcess;
                Platform.runLater(() -> callBack.onCallBack(finalMProcess));
            }
        });*/

    }

    public String getStringByProcess(Process process) {
        System.out.println("onCallBack(Process process)");
        InputStream errorStream = process.getErrorStream();
        StringBuffer sb = new StringBuffer();
        String errMsg = getStringByInputStream(errorStream);
        if (errMsg.length() > 0)
            sb.append(errMsg + "\r\n");
        InputStream inputStream = process.getInputStream();
        String msg = getStringByInputStream(inputStream);
        if (msg.length() > 0)
            sb.append(msg + "\r\n");
        return sb.toString();
    }

    private String getStringByInputStream(InputStream inputStream) {
        BufferedReader mBufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        String tempStr = null;
        StringBuffer sb = new StringBuffer();
        try {
            while ((tempStr = mBufferedReader.readLine()) != null) {
                System.out.println("tempStr:" + tempStr);
                if (sb.length() != 0)
                    sb.append("\r\n");
                sb.append(tempStr);
            }
            mBufferedReader.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
