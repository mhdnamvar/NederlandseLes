package com.namvar.nederlandsles;

import static com.namvar.nederlandsles.data.SimpleDao.setupData;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    static final String TERMS_AND_CONDITIONS_KEY = "TermsAndConditions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!isTermsAndConditionsAccepted()) {
            TermsAndConditionsDialogFragment dialogFragment = new TermsAndConditionsDialogFragment();
            dialogFragment.show(getSupportFragmentManager(), "TermsAndConditionsDialogFragment");
        }

        setupViews();
        setupData(getApplicationContext());
    }

    private Boolean isTermsAndConditionsAccepted() {
            SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
            return sharedPreferences.getBoolean(TERMS_AND_CONDITIONS_KEY, Boolean.FALSE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return getSupportFragmentManager().popBackStackImmediate();
    }

    private void setupViews() {
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,
                R.id.navigation_letters,
                R.id.navigation_cards,
                R.id.navigation_speaking)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }


}
