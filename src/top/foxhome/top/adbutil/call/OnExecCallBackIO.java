package top.foxhome.top.adbutil.call;

import java.io.InputStream;
import java.io.OutputStream;

public abstract class OnExecCallBackIO extends BaseOnExecCallBack {
    @Override
    public void onCallBack(Process process) {
        onCallBack(process.getOutputStream(), process.getInputStream());
    }

    public abstract void onCallBack(OutputStream outputStream, InputStream inputStream);
}
