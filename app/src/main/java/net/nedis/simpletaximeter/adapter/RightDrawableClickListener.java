package net.nedis.simpletaximeter.adapter;

import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

public class RightDrawableClickListener implements View.OnTouchListener {

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        boolean hasConsumed = false;
        if (v instanceof EditText) {
            if (event.getX() >= v.getWidth() - ((EditText) v).getTotalPaddingRight()) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    ((EditText) v).setText("");
                }
                hasConsumed = true;
            }
        }

        return hasConsumed;
    }
}
