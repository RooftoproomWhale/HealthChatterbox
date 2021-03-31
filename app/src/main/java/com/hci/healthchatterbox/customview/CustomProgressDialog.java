package com.hci.healthchatterbox.customview;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.hci.healthchatterbox.R;

/**
 * Created by w on 2018-05-16.
 */

public class CustomProgressDialog extends Dialog {
    public CustomProgressDialog(Context context)
    {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog);
    }
}
