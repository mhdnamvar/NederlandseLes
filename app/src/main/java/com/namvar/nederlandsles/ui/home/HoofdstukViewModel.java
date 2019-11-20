package com.namvar.nederlandsles.ui.home;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.namvar.nederlandsles.Section;
import com.namvar.nederlandsles.data.SimpleDao;

import java.util.List;

public class HoofdstukViewModel extends ViewModel {

    private MutableLiveData<List<String>> mList;
    private MutableLiveData<String> mHtml;

    public HoofdstukViewModel() {
        this.mList = new MutableLiveData<>();
        this.mHtml = new MutableLiveData<>();
    }

    MutableLiveData<List<String>> getList(String sectionName) {
        try {
            mList.setValue(SimpleDao.get(Section.get(sectionName)));
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("HoofdstukViewModel", e.getLocalizedMessage());
        }
        return mList;
    }

    public MutableLiveData<String> getHtml() {
        return mHtml;
    }
}
