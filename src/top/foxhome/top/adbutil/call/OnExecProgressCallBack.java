package top.foxhome.top.adbutil.call;

public interface OnExecProgressCallBack<E> {
    public void onPrepare();

    public void onProgress(E e);

    public void onFinish();
}
