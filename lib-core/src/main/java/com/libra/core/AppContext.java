package com.libra.core;

import android.app.Application;

/**
 * 全局静态
 *
 * @author Libra
 * @since 2018/6/13
 */

public class AppContext {
    private static Application application;

    public static Application getApplication() {
        return application;
    }

    public static void setApplication(Application application) {
        AppContext.application = application;
    }
}
