package top.foxhome.top.adbutil.call;

public interface OnExecCallBack {
    public abstract void onCallBack(Process process);

    public abstract void destroyProcess();
}
