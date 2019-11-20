package com.namvar.nederlandsles.ui.home;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.namvar.nederlandsles.Section;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private MutableLiveData<List<String>> mChapters;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        mChapters = new MutableLiveData<>();
        try{
            mChapters.setValue(Section.listValues());
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("HomeViewModel", e.getLocalizedMessage());
        }
    }

    public MutableLiveData<List<String>> getChapters() {
        return mChapters;
    }

}