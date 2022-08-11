package net.nedis.simpletaximeter.ui.main;

import static android.text.InputType.TYPE_CLASS_NUMBER;
import static android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL;
import static net.nedis.simpletaximeter.storage.SettingsStorage.ET_COST_IN_CITY;
import static net.nedis.simpletaximeter.storage.SettingsStorage.ET_COST_OUT_CITY;
import static net.nedis.simpletaximeter.storage.SettingsStorage.ET_MIN_COST;
import static net.nedis.simpletaximeter.storage.SettingsStorage.ET_MIN_COST_DISTANCE;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import net.nedis.simpletaximeter.adapter.TextWatcherAdapter;
import net.nedis.simpletaximeter.databinding.FragmentSettingsBinding;
import net.nedis.simpletaximeter.storage.SettingsStorage;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    private MainFragment mainFragment;

    private SettingsStorage settingsStorage;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        settingsStorage = new SettingsStorage(getContext());
        binding = FragmentSettingsBinding.inflate(inflater, container, false);

        initTextInputEditText(binding.etMinCost, ET_MIN_COST);
        initTextInputEditText(binding.etMinCostDistance, ET_MIN_COST_DISTANCE);
        initTextInputEditText(binding.etCostInCity, ET_COST_IN_CITY);
        initTextInputEditText(binding.etCostOutCity, ET_COST_OUT_CITY);

        return binding.getRoot();
    }

    private void initTextInputEditText(TextInputEditText editText, String name) {
        editText.setText(settingsStorage.getValue(name));
        EditTextChangeListener listener = new EditTextChangeListener(editText, this, settingsStorage, name);
        editText.addTextChangedListener(listener);
        editText.setOnFocusChangeListener(listener);
        editText.setInputType(TYPE_CLASS_NUMBER + TYPE_NUMBER_FLAG_DECIMAL);
    }

    public void setMainFragment(MainFragment mainFragment) {
        this.mainFragment = mainFragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        mainFragment = null;
    }

    void updateMainFragment() {
        if (mainFragment != null) {
            mainFragment.calculate();
        }
    }

    static final class EditTextChangeListener extends TextWatcherAdapter implements View.OnFocusChangeListener {

        private final SettingsFragment settingsFragment;

        private final SettingsStorage settingsStorage;

        private final String key;

        EditTextChangeListener(EditText editText,
                               SettingsFragment settingsFragment,
                               SettingsStorage settingsStorage,
                               String key) {
            super(editText);
            this.settingsFragment = settingsFragment;
            this.settingsStorage = settingsStorage;
            this.key = key;
        }

        @Override
        public void afterTextChanged(Editable s) {
            String value = s.toString();
            if (!value.trim().isEmpty()) {
                settingsStorage.setValue(key, value);
                settingsFragment.updateMainFragment();
            }
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                if (owner.getText().toString().isEmpty()) {
                    owner.setText(settingsStorage.getValue(key));
                    settingsFragment.updateMainFragment();
                }
            }
        }
    }
}
