package com.example.intheaters;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;

/**
 * Class for the settings activity
 * Allows for the user to change to darkmode
 */
public class SettingsActivity extends AppCompatActivity {

    /**
     * Create the layout.
     * Also check if the dark theme is checked or not
     */
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if(savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean darkTheme = sharedPreferences.getBoolean(getString(R.string.dark), false);
        if(darkTheme){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    /**
     * Create preferences and grab theme preference
     */
    public static class SettingsFragment extends PreferenceFragmentCompat{
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey){
            setPreferencesFromResource(R.xml.preferences, rootKey);

            SwitchPreference themePref = findPreference(getString(R.string.dark));
            if(themePref != null){
                themePref.setOnPreferenceChangeListener((preference, newValue) -> {
                    requireActivity().recreate();
                    return true;
                });
            }
        }
    }

}
