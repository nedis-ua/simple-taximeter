package net.nedis.simpletaximeter.adapter;

import android.text.TextWatcher;
import android.widget.EditText;

public abstract class TextWatcherAdapter implements TextWatcher {

    protected final EditText owner;

    protected TextWatcherAdapter(EditText owner) {
        this.owner = owner;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }
}
