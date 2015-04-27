package com.honigsheroes.checkers.model.test;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.test.mock.MockContext;
import android.test.mock.MockResources;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * Created by pulu on 4/26/15.
 */
public class CheckersMockContext extends MockContext {
    @Override
    public Resources getResources() {
        return new MockResources() {
            @Override
            public DisplayMetrics getDisplayMetrics() {
                return new DisplayMetrics();
            }
            @Override
            public Configuration getConfiguration() {
                return new Configuration();
            }
            @Override
            public void getValue (int id, TypedValue outValue, boolean resolveRefs) {
                return;
            }
            @Override
            public boolean getBoolean(int id) {
                return true;
            }
            @Override
            public int getDimensionPixelSize(int id) {
                return 0;
            }
        };
    }

    @Override
    public Object getSystemService(String name) {
        return new WindowManager() {
            @Override
            public void addView(View view, ViewGroup.LayoutParams params) {

            }

            @Override
            public void updateViewLayout(View view, ViewGroup.LayoutParams params) {

            }

            @Override
            public void removeView(View view) {

            }

            @Override
            public Display getDefaultDisplay() {
                return new Display();
            }

            @Override
            public void removeViewImmediate(View view) {

            }
        };
    }
}
