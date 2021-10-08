package top.foxhome.top.adbutil.call;

public interface OnExecProgressCallBack {
    public void onPrepare();

    public void onProgress(String msg);

    public void onFinish();
}
