package top.foxhome.top.adbutil;

import top.foxhome.top.adbutil.bean.CmdKeyBean;
import top.foxhome.top.adbutil.bean.KeyBean;

public interface KeyCodeConfig {
    public static KeyBean[][] KEYS_QUICK = new KeyBean[][]{
            new KeyBean[]{
                    new KeyBean(null, -1),
                    new KeyBean("↑", KeyEvent.KEYCODE_DPAD_UP),
            },
            new KeyBean[]{
                    new KeyBean("←", KeyEvent.KEYCODE_DPAD_LEFT),
                    new KeyBean("确认", KeyEvent.KEYCODE_DPAD_CENTER),
                    new KeyBean("→", KeyEvent.KEYCODE_DPAD_RIGHT),
            },
            new KeyBean[]{
                    new KeyBean(null, -1),
                    new KeyBean("↓", KeyEvent.KEYCODE_DPAD_DOWN),
            },
            new KeyBean[]{
                    new KeyBean("F1", KeyEvent.KEYCODE_F1),
                    new KeyBean("F2", KeyEvent.KEYCODE_F2),
                    new KeyBean("F3", KeyEvent.KEYCODE_F3),
                    new KeyBean("F4", KeyEvent.KEYCODE_F4),
                    new KeyBean("F5", KeyEvent.KEYCODE_F5),
                    new KeyBean("F6", KeyEvent.KEYCODE_F6),
                    new KeyBean("F7", KeyEvent.KEYCODE_F7),
                    new KeyBean("F8", KeyEvent.KEYCODE_F8),
                    new KeyBean("F9", KeyEvent.KEYCODE_F9),
                    new KeyBean("F10", KeyEvent.KEYCODE_F10),
                    new KeyBean("F11", KeyEvent.KEYCODE_F11),
                    new KeyBean("F12", KeyEvent.KEYCODE_F12),
            },
            new KeyBean[]{
                    new KeyBean("主页", KeyEvent.KEYCODE_HOME),
                    new KeyBean("返回", KeyEvent.KEYCODE_BACK),
                    new KeyBean("菜单", KeyEvent.KEYCODE_MENU),
                    new KeyBean("退格", KeyEvent.KEYCODE_DEL),
                    new KeyBean("回车", KeyEvent.KEYCODE_ENTER),
                    new KeyBean("Esc", KeyEvent.KEYCODE_ESCAPE),
                    new KeyBean("删除", KeyEvent.KEYCODE_FORWARD_DEL),
                    new KeyBean("设置", KeyEvent.KEYCODE_SETTINGS),
                    new KeyBean("V+", KeyEvent.KEYCODE_VOLUME_UP),
                    new KeyBean("V-", KeyEvent.KEYCODE_VOLUME_DOWN),
                    new KeyBean("静音", KeyEvent.KEYCODE_VOLUME_MUTE),
            },
            new KeyBean[]{
                    new KeyBean("0", KeyEvent.KEYCODE_NUMPAD_0),
                    new KeyBean("1", KeyEvent.KEYCODE_NUMPAD_1),
                    new KeyBean("2", KeyEvent.KEYCODE_NUMPAD_2),
                    new KeyBean("3", KeyEvent.KEYCODE_NUMPAD_3),
                    new KeyBean("4", KeyEvent.KEYCODE_NUMPAD_4),
                    new KeyBean("5", KeyEvent.KEYCODE_NUMPAD_5),
                    new KeyBean("6", KeyEvent.KEYCODE_NUMPAD_6),
                    new KeyBean("7", KeyEvent.KEYCODE_NUMPAD_7),
                    new KeyBean("8", KeyEvent.KEYCODE_NUMPAD_8),
                    new KeyBean("9", KeyEvent.KEYCODE_NUMPAD_9),
            },
    };

    public static CmdKeyBean[][] KEYS_TIPS = new CmdKeyBean[][]{
            new CmdKeyBean[]{
                    new CmdKeyBean("停止应用", "adb shell am force-stop %s"),
                    new CmdKeyBean("启动应用", "adb shell am start -n \"包名/活动名\""),
                    new CmdKeyBean("启动服务", "adb shell am startservice -n \"包名/服务名\""),
                    new CmdKeyBean("发送广播", "adb shell am broadcast -a 广播action"),
                    new CmdKeyBean("当前活动", "adb shell dumpsys window | grep mCurrentFocus"),
                    new CmdKeyBean("截屏", "#列如adb shell screencap -p /sdcard/screen.png\r\nadb shell screencap -p 安卓设备文件路径"),
            },
            new CmdKeyBean[]{
                    new CmdKeyBean("查看服务", "adb shell service list"),
                    new CmdKeyBean("上传文件", "#列如adb push D:\\Android\\screen.png /sdcard/\r\nadb push 本地计算机文件 安卓设备文件路径"),
                    new CmdKeyBean("下载文件", "#列如adb pull /sdcard/screen.png D:\\Android\\screen.png\r\nadb pull 安卓设备文件路径 本地计算机文件"),
                    new CmdKeyBean("卸载应用", "adb uninstall %s"),
                    new CmdKeyBean("apk路径", "adb shell pm path %s"),
                    new CmdKeyBean("清除缓存", "adb shell pm clear %s"),
            }, new CmdKeyBean[]{
                    new CmdKeyBean("应用列表", "adb shell pm list"),
                    new CmdKeyBean("停止应用", "adb shell am force-stop %s"),
                    new CmdKeyBean("分辨率", "adb shell wm size"),

    }
    };
}
