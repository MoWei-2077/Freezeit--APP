package io.github.jark006.freezeit.hook.android;

import android.os.Build;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import io.github.jark006.freezeit.base.AbstractMethodHook;
import io.github.jark006.freezeit.base.MethodHook;
import io.github.jark006.freezeit.hook.Config;
import io.github.jark006.freezeit.hook.XpUtils;

// 广播滤波器跳过Hook.
public class BroadcastSkipFilterHook extends MethodHook {
    private final Config config;

    public BroadcastSkipFilterHook(ClassLoader classLoader, Config config) {
        super(classLoader);
        this.config = config;
    }

    @Override
    public String getTargetClass() {
        return "com.android.server.am.BroadcastSkipPolicy";
    }

    @Override
    public String getTargetMethod() {
        return "shouldSkipMessage";
    }

    @Override
    public Object[] getTargetParam() {
        if (XposedHelpers.findClassIfExists("com.android.server.am.IVivoBroadcastQueueModern", classLoader) != null)
            return new Object[]{ "com.android.server.am.BroadcastRecord", "com.android.server.am.BroadcastFilter", boolean.class, int.class, "com.android.server.am.IVivoBroadcastQueueModern" };
        return new Object[]{ "com.android.server.am.BroadcastRecord", "com.android.server.am.BroadcastFilter" };
    }

    @Override
    public XC_MethodHook getTargetHook() {
        return new AbstractMethodHook() {
            @Override
            protected void afterMethod(XC_MethodHook.MethodHookParam param) {
                if (param.getResult() != null)
                    return;

                final int uid = config.getBroadcastFilterOwningUid(param.args[1]);// BroadcastFilter

                // 不在管理范围，或顶层前台 则不清理广播
                if (!config.managedApp.contains(uid) || config.foregroundUid.contains(uid))
                    return;

                param.setResult("Skipping deliver [FreezeIt]: frozen process");
                if (XpUtils.DEBUG_BROADCAST) {
                    // BroadcastRecord https://cs.android.com/android/platform/superproject/+/master:frameworks/base/services/core/java/com/android/server/am/BroadcastRecord.java
                    final int callerUid = config.getBroadcastRecordCallingUid(param.args[0]); // broadcastRecord
                    XpUtils.log("Freezeit[BroadCastHook]:", "拦截动态广播: " +
                            config.pkgIndex.getOrDefault(callerUid, String.valueOf(callerUid)) +
                            " 发往 " +
                            config.pkgIndex.getOrDefault(uid, String.valueOf(uid))
                    );
                }
            }
        };
    }

    @Override
    public int getMinVersion() {
        return Build.VERSION_CODES.UPSIDE_DOWN_CAKE;
    }

    @Override
    public String successLog() {
        return "修改跳过广播";
    }
}
