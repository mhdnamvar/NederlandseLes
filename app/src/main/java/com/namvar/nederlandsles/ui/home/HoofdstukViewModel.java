package com.namvar.nederlandsles.ui.home;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.namvar.nederlandsles.data.JsonDao;

import java.util.List;

public class HoofdstukViewModel extends ViewModel {

    private MutableLiveData<List<String>> mList;

    public HoofdstukViewModel() {
        this.mList = new MutableLiveData<>();
    }

    MutableLiveData<List<String>> getList(int section) {
        try{
            mList.setValue(JsonDao.getSection(section));
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("HoofdstukViewModel", e.getLocalizedMessage());
        }
        return mList;
    }

}
