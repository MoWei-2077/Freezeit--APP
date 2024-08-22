package io.github.jark006.freezeit.hook.android;

import io.github.jark006.freezeit.hook.Enum;
import android.os.Build;
import de.robv.android.xposed.XC_MethodHook;
import io.github.jark006.freezeit.base.MethodHook;
import io.github.jark006.freezeit.hook.XpUtils;

/**
 * 禁用暂停执行已缓存Hook.
 */
public class CacheFreezerHook extends MethodHook {


    public CacheFreezerHook(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    public String getTargetClass() {
        return Enum.Class.CachedAppOptimizer;
    }

    @Override
    public String getTargetMethod() {
        return Enum.Method.useFreezer;
    }

    @Override
    public Object[] getTargetParam() {
        return new Object[0];
    }

    @Override
    public XC_MethodHook getTargetHook() {

        // 返回不使用暂停执行已缓存
        return constantResult(false);
    }

    @Override
    public int getMinVersion() {
        return Build.VERSION_CODES.R;
    }

    @Override
    public String successLog() {
        return "已屏蔽暂停执行已缓存";
    }

}