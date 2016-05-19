package com.tam.pikachu.util;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

public class UtilActivity {
    public static void requestWindowFeature(Activity ac) {
        ac.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ac.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

}
