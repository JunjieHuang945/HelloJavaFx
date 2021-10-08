package top.foxhome.top.adbutil;

import javafx.application.Platform;
import top.foxhome.top.adbutil.call.OnExecCallBack;
import top.foxhome.top.adbutil.call.OnExecCallBacksText;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RuntimeHelper {
    private ExecutorService cachedThreadPool;
    private Runtime mRuntime;

    public RuntimeHelper() {
        cachedThreadPool = Executors.newSingleThreadExecutor();
        mRuntime = Runtime.getRuntime();
    }

    public void newRuntime() {
        mRuntime.exit(0);
        mRuntime = Runtime.getRuntime();
    }

    public void exec(List<String> cmds, OnExecCallBack callBack) {
        System.out.println("cmds length:" + cmds.size());
        cachedThreadPool.execute(() -> {
            if (callBack instanceof OnExecCallBacksText)
                ((OnExecCallBacksText) callBack).onPrepare();
            for (String cmd : cmds) {
                Process mProcess = null;
                try {
                    System.out.println("cmds:" + cmd);
                    mProcess = mRuntime.exec(cmd);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (mProcess != null && callBack != null) {
                    Process finalMProcess = mProcess;
                    Platform.runLater(() -> callBack.onCallBack(finalMProcess));
                }
            }
            if (callBack instanceof OnExecCallBacksText)
                Platform.runLater(() ->((OnExecCallBacksText) callBack).onFinish());
        });
    }

    public void exec(String cmd, OnExecCallBack callBack) {
        System.out.println(cmd);
        cachedThreadPool.execute(() -> {
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
        });

    }

}
