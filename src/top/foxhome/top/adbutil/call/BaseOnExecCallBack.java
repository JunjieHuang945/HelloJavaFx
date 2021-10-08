package top.foxhome.top.adbutil.call;

public class BaseOnExecCallBack implements OnExecCallBack {
    private Process process;

    public void onCallBack(Process process) {
        this.process = process;
    }

    @Override
    public void destroyProcess() {
        if (process != null) {
            process.destroy();
            process = null;
        }
    }
}
