package com.rezaandreza.shop.System.UIComponents;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.rezaandreza.shop.Configuration.Season;

public class EdittextHelper {
    public static void setFocus(final EditText edit)
    {
        edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                edit.post(new Runnable() {
                    @Override
                    public void run() {
                        InputMethodManager imm = (InputMethodManager) Season.applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(edit, InputMethodManager.SHOW_IMPLICIT);
                    }
                });
            }
        });
    }
}
