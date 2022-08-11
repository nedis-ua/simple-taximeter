package net.nedis.simpletaximeter.ui.main;

import static net.nedis.simpletaximeter.Constants.CURRENCY_SCALE;
import static net.nedis.simpletaximeter.Constants.DISTANCE_SCALE;
import static net.nedis.simpletaximeter.storage.SettingsStorage.ET_COST_IN_CITY;
import static net.nedis.simpletaximeter.storage.SettingsStorage.ET_COST_OUT_CITY;
import static net.nedis.simpletaximeter.storage.SettingsStorage.ET_MIN_COST;
import static net.nedis.simpletaximeter.storage.SettingsStorage.ET_MIN_COST_DISTANCE;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import net.nedis.simpletaximeter.adapter.RightDrawableClickListener;
import net.nedis.simpletaximeter.adapter.TextWatcherAdapter;
import net.nedis.simpletaximeter.databinding.FragmentMainBinding;
import net.nedis.simpletaximeter.logic.Calculator;
import net.nedis.simpletaximeter.storage.SettingsStorage;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainFragment extends Fragment {

    private static final String DEFAULT_ACTUAL_DISTANCE_VALUE = "0.0";

    private SettingsStorage settingsStorage;

    private FragmentMainBinding binding;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        settingsStorage = new SettingsStorage(getContext());
        binding = FragmentMainBinding.inflate(inflater, container, false);
        initTextInputEditText(binding.etDistanceInCity);
        initTextInputEditText(binding.etDistanceOutCity);

        calculate();
        return binding.getRoot();
    }

    private void initTextInputEditText(TextInputEditText editText) {
        editText.setText(DEFAULT_ACTUAL_DISTANCE_VALUE);
        EditTextChangeListener listener = new EditTextChangeListener(editText);
        editText.addTextChangedListener(listener);
        editText.setOnFocusChangeListener(listener);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_FLAG_DECIMAL);
        editText.setOnTouchListener(new RightDrawableClickListener());
    }

    public void calculate() {
        Calculator calculator = new Calculator.Builder()
                .setKmInCity(new BigDecimal(getNumberFromEdit(binding.etDistanceInCity))
                        .setScale(1, RoundingMode.HALF_UP))
                .setKmOutCity(new BigDecimal(getNumberFromEdit(binding.etDistanceOutCity))
                        .setScale(1, RoundingMode.HALF_UP))
                .setMinCost(settingsStorage.getBigDecimal(ET_MIN_COST, CURRENCY_SCALE))
                .setMinCostDistance(settingsStorage.getBigDecimal(ET_MIN_COST_DISTANCE, DISTANCE_SCALE))
                .setCostInCity(settingsStorage.getBigDecimal(ET_COST_IN_CITY, CURRENCY_SCALE))
                .setCostOutCity(settingsStorage.getBigDecimal(ET_COST_OUT_CITY, CURRENCY_SCALE))
                .build();

        BigDecimal result = calculator.calculate();

        binding.tvResult.setText(result.toPlainString());
    }

    private String getNumberFromEdit(TextInputEditText editText) {
        Editable text = editText.getText();
        if (text != null && !text.toString().trim().isEmpty()) {
            return text.toString();
        } else {
            return DEFAULT_ACTUAL_DISTANCE_VALUE;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    final class EditTextChangeListener extends TextWatcherAdapter implements View.OnFocusChangeListener {

        EditTextChangeListener(EditText owner) {
            super(owner);
        }

        @Override
        public void afterTextChanged(Editable s) {
            calculate();
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                if (owner.getText().toString().isEmpty()) {
                    owner.setText(DEFAULT_ACTUAL_DISTANCE_VALUE);
                }
            }
        }
    }
}