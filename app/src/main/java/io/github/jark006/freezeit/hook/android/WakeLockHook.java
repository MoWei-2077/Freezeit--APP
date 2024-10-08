package io.github.jark006.freezeit.hook.android;

import static io.github.jark006.freezeit.hook.XpUtils.log;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.IBinder;
import android.os.WorkSource;

import de.robv.android.xposed.XC_MethodHook;
import io.github.jark006.freezeit.base.AbstractMethodHook;
import io.github.jark006.freezeit.base.MethodHook;
import io.github.jark006.freezeit.hook.Config;
import io.github.jark006.freezeit.hook.Enum;
import io.github.jark006.freezeit.hook.XpUtils;

public class WakeLockHook {
    final static String TAG = "Freezeit[唤醒锁]";
    Config config;

    public WakeLockHook(Config config, ClassLoader classLoader) {
        this.config = config;


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            XpUtils.hookMethod(TAG, classLoader, callback,
                    Enum.Class.PowerManagerService, Enum.Method.acquireWakeLockInternal,
                    IBinder.class, int.class, int.class, String.class,
                    String.class, WorkSource.class, String.class, int.class, int.class,
                    Enum.Class.IWakeLockCallback);
        } else {
            XpUtils.hookMethod(TAG, classLoader, callback,
                    Enum.Class.PowerManagerService, Enum.Method.acquireWakeLockInternal,
                    IBinder.class, int.class, int.class, String.class,
                    String.class, WorkSource.class, String.class, int.class, int.class);
        }
    }

    XC_MethodHook callback = new XC_MethodHook() {
        @SuppressLint("DefaultLocale")
        public void beforeHookedMethod(MethodHookParam param) {

            // 测试应用实际是否获得唤醒锁  10XXX为UID
            // dumpsys power|grep 10xxx

            // android S+ 12+:[7]
            final int uid = (int) param.args[7];
            if (!config.managedApp.contains(uid)) return;

            param.setResult(null);
            if (XpUtils.DEBUG_WAKEUP_LOCK)
                log(TAG, "阻止 " + config.pkgIndex.getOrDefault(uid, "UID:" + uid));
        }
    };
}