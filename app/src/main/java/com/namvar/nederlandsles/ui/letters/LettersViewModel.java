package com.namvar.nederlandsles.ui.letters;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.namvar.nederlandsles.Section;
import com.namvar.nederlandsles.data.SimpleDao;

import java.util.List;

public class LettersViewModel extends ViewModel {

    private MutableLiveData<List<String>> mLetters;

    public LettersViewModel() {
        mLetters = new MutableLiveData<>();
        try{
            mLetters.setValue(SimpleDao.getLetters());
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("LettersViewModel", e.getLocalizedMessage());
        }
    }

    public MutableLiveData<List<String>> getLetters() {
        return mLetters;
    }
}