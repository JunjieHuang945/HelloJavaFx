package top.foxhome.top.adbutil.call;

public abstract class OnExecCallBackFinish extends BaseOnExecCallBack {
    public void onCallBack(Process process) {
        onCallBack();
    }

    public abstract void onCallBack();
}
