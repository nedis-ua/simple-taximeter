package net.nedis.simpletaximeter;

import static net.nedis.simpletaximeter.Constants.DEFAULT_COST_IN_CITY;
import static net.nedis.simpletaximeter.Constants.DEFAULT_COST_OUT_CITY;
import static net.nedis.simpletaximeter.Constants.DEFAULT_MIN_COST;
import static net.nedis.simpletaximeter.Constants.DEFAULT_MIN_COST_DISTANCE;
import static net.nedis.simpletaximeter.storage.SettingsStorage.ET_COST_IN_CITY;
import static net.nedis.simpletaximeter.storage.SettingsStorage.ET_COST_OUT_CITY;
import static net.nedis.simpletaximeter.storage.SettingsStorage.ET_MIN_COST;
import static net.nedis.simpletaximeter.storage.SettingsStorage.ET_MIN_COST_DISTANCE;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import net.nedis.simpletaximeter.databinding.ActivityMainBinding;
import net.nedis.simpletaximeter.storage.SettingsStorage;
import net.nedis.simpletaximeter.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setDefaultSettingsValues();

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter =
                new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
    }

    private void setDefaultSettingsValues() {
        final SettingsStorage settingsStorage = new SettingsStorage(this);
        settingsStorage.setDefaultValue(ET_MIN_COST, DEFAULT_MIN_COST);
        settingsStorage.setDefaultValue(ET_MIN_COST_DISTANCE, DEFAULT_MIN_COST_DISTANCE);
        settingsStorage.setDefaultValue(ET_COST_IN_CITY, DEFAULT_COST_IN_CITY);
        settingsStorage.setDefaultValue(ET_COST_OUT_CITY, DEFAULT_COST_OUT_CITY);
    }
}