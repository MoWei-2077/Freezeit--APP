package io.github.jark006.freezeit.hook.android;

import de.robv.android.xposed.XC_MethodHook;
import io.github.jark006.freezeit.hook.Enum;
import io.github.jark006.freezeit.base.AbstractMethodHook;
import io.github.jark006.freezeit.base.MethodHook;

public class ActivityManagerHook extends MethodHook {
    public ActivityManagerHook(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    public String getTargetClass() {
        return Enum.Class.ActivityManagerService;
    }

    @Override
    public String getTargetMethod() {
        return "setSystemProcess";
    }

    @Override
    public Object[] getTargetParam() {
        return new Object[0];
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
        return "已屏蔽活动助手";
    }
}