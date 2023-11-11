package com.namvar.nederlandsles.ui.settings;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.namvar.nederlandsles.data.SimpleDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SettingsViewModel extends AndroidViewModel {

    private final MutableLiveData<List<String>> mItems;

    public SettingsViewModel(@NonNull Application application) {
        super(application);
        mItems = new MutableLiveData<>();
        try {
            mItems.setValue(getSettings());
        } catch (Exception e) {
            Log.d("SettingsViewModel", Objects.requireNonNull(e.getLocalizedMessage()));
        }
    }

    @NonNull
    private static List<String> getSettings() {
        List<String> settingsItems = new ArrayList<>();
        settingsItems.add("Privacy policy");
        return settingsItems;
    }

    MutableLiveData<List<String>> getItems() {
        return mItems;
    }
}
