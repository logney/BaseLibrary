package com.pengyu.base.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by PengYu on 2018/3/1.
 */

public class EditextChangeListener {

    public static void change(EditText editText, final TextChange textChange) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String value = editable.toString();
                if (textChange != null) {
                    textChange.change(value);
                }
            }
        });
    }

    public interface TextChange {
        void change(String value);
    }
}

