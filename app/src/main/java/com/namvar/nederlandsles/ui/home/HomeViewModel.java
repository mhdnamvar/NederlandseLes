package com.namvar.nederlandsles.ui.home;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.namvar.nederlandsles.Section;

import java.util.List;
import java.util.Objects;

public class HomeViewModel extends AndroidViewModel {

    private final MutableLiveData<List<String>> mChapters;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        mChapters = new MutableLiveData<>();
        try{
            mChapters.setValue(Section.listValues());
        } catch (Exception e) {
            Log.d("HomeViewModel", Objects.requireNonNull(e.getLocalizedMessage()));
        }
    }

    public MutableLiveData<List<String>> getChapters() {
        return mChapters;
    }

}