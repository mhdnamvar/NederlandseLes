package com.namvar.nederlandsles.ui.home;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.namvar.nederlandsles.Section;
import com.namvar.nederlandsles.data.SimpleDao;

import java.util.List;
import java.util.Objects;

public class PhrasesViewModel extends ViewModel {

    private final MutableLiveData<List<String>> mList;
    private final MutableLiveData<String> mHtml;

    public PhrasesViewModel() {
        this.mList = new MutableLiveData<>();
        this.mHtml = new MutableLiveData<>();
    }

    MutableLiveData<List<String>> getList(String sectionName) {
        try {
            mList.setValue(SimpleDao.get(Section.get(sectionName)));
        } catch (Exception e) {
            Log.d("PhrasesViewModel", Objects.requireNonNull(e.getLocalizedMessage()));
        }
        return mList;
    }

    public MutableLiveData<String> getHtml() {
        return mHtml;
    }
}
