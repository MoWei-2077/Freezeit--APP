package io.github.jark006.freezeit.hook.android;

import de.robv.android.xposed.XC_MethodHook;
import io.github.jark006.freezeit.hook.Enum;
import io.github.jark006.freezeit.base.AbstractMethodHook;
import io.github.jark006.freezeit.base.MethodHook;

public class ActivityManagerServiceHook extends MethodHook {
    public ActivityManagerServiceHook(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    public String getTargetClass() {
        return Enum.Class.ActivityManagerService;
    }

    @Override
    public String getTargetMethod() {
        return "noExcessivePowerUsage";
    }

    @Override
    public Object[] getTargetParam() {
        return new Object[] { long.class, boolean.class, long.class, String.class, String.class, int.class, Enum.Class.ProcessRecord };
    }

    @Override
    public XC_MethodHook getTargetHook() {
        return new AbstractMethodHook() {
            @Override
            protected void beforeMethod(MethodHookParam param) {

            }
        };
    }

    @Override
    public int getMinVersion() {
        return ANY_VERSION;
    }

    @Override
    public String successLog() {
        return "拦截活动管理器服务";
    }
}