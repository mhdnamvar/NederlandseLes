package com.namvar.nederlandsles.ui.home;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.namvar.nederlandsles.data.JsonDao;

import java.util.List;

public class HoofdstukViewModel extends ViewModel {

    private MutableLiveData<List<String>> mList;
    private MutableLiveData<String> mHtml;

    public HoofdstukViewModel() {
        this.mList = new MutableLiveData<>();
        this.mHtml = new MutableLiveData<>();
    }

    MutableLiveData<List<String>> getList(int section) {
        try{
            if(section > 24){
                mList.setValue(JsonDao.getPrepositions());
            } else if(section >= 10){
                mHtml.setValue(JsonDao.getLetter(section - 10));
            } else {
                mList.setValue(JsonDao.getSection(section));
            }
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
