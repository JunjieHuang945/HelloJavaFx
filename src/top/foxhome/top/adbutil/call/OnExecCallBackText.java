package top.foxhome.top.adbutil.call;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public abstract class OnExecCallBackText extends BaseOnExecCallBack {
    @Override
    public void onCallBack(Process process) {
        System.out.println("onCallBack(Process process)");
        InputStream errorStream = process.getErrorStream();
        String errMsg = getStringByInputStream(errorStream);
        if (!errMsg.equals(""))
            onCallBack(errMsg);
        InputStream inputStream = process.getInputStream();
        String msg = getStringByInputStream(inputStream);
        if (!msg.equals(""))
            onCallBack(msg);
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

    public abstract void onCallBack(String msg);
}
