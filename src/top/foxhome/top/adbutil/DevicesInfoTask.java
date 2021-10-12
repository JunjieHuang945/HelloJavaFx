package top.foxhome.top.adbutil;

import javafx.concurrent.Task;

public class DevicesInfoTask extends Task<Boolean> {
    private boolean isRun;
    private RuntimeHelper mRuntimeHelper;
    private OnUpdateDevicesInfoCallBack callBack;

    public DevicesInfoTask(OnUpdateDevicesInfoCallBack callBack) {
        this.mRuntimeHelper = new RuntimeHelper();
        this.callBack = callBack;
    }

    @Override
    protected Boolean call() throws Exception {
        isRun = true;
        while (isRun) {
            mRuntimeHelper.exec("adb devices -l", s -> {
                s = s.replace("List of devices attached", "");
                if (s.contains("device product") && s.contains("model:") && s.contains("device:")) {//检查是否存在连接的设备
                    String[] insfos = s.split("\r");
                    StringBuffer sb = new StringBuffer();
                    for (String insfo : insfos) {
                        int ipIndexEnd = insfo.indexOf("device product");
                        int modelIndexStart = insfo.indexOf("model:");
                        int modelIndexEnd = insfo.indexOf("device:");
                        if (ipIndexEnd == -1 || modelIndexStart == -1 || modelIndexEnd == -1) continue;
                        String ip = insfo.substring(0, ipIndexEnd);
                        String modelName = insfo.substring(modelIndexStart + "model:".length(), modelIndexEnd);
                        sb.append(ip + modelName + "\r");
                    }
                    updateMessage(sb.toString());
                } else {
                    updateMessage("");
                }
            });
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
            }
        }
        return null;
    }

    @Override
    protected void updateMessage(String message) {
        super.updateMessage(message);
        System.out.println("message:" + message);
        callBack.onUpdateDevicesInfo(message);
    }

    public void stopUpdate() {
        isRun = false;
        mRuntimeHelper.newRuntime();
    }

    interface OnUpdateDevicesInfoCallBack {
        public void onUpdateDevicesInfo(String intfo);
    }
}
