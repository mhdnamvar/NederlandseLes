package com.namvar.nederlandsles.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.namvar.nederlandsles.data.JsonDao;

import java.util.List;

public class HoofdstukViewModel extends ViewModel {

    private MutableLiveData<List<String>> mList;

    public HoofdstukViewModel() {
        List<String> section = JsonDao.getSection(1);
        mList = new MutableLiveData<>();
        mList.setValue(section);
    }

    public LiveData<List<String>> getList() {
        return mList;
    }
}
