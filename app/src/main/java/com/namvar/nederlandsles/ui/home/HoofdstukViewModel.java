package com.namvar.nederlandsles.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HoofdstukViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HoofdstukViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Hoofdstuk fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
