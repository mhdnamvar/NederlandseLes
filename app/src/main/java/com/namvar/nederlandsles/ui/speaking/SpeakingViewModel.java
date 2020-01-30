package com.namvar.nederlandsles.ui.speaking;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.namvar.nederlandsles.Section;
import com.namvar.nederlandsles.data.SimpleDao;

import java.util.List;

public class SpeakingViewModel extends AndroidViewModel {

    private MutableLiveData<List<String>> mItems;

    public SpeakingViewModel(@NonNull Application application) {
        super(application);
        mItems = new MutableLiveData<>();
        try{
            mItems.setValue(SimpleDao.getSpeaking());
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("SpeakingViewModel", e.getLocalizedMessage());
        }
    }

    public MutableLiveData<List<String>> getItems() {
        return mItems;
    }
}
