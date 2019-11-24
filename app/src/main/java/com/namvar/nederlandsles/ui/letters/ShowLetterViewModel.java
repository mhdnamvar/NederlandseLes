package com.namvar.nederlandsles.ui.letters;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.namvar.nederlandsles.data.SimpleDao;

public class ShowLetterViewModel extends ViewModel {

    private MutableLiveData<String> mHtml;

    public ShowLetterViewModel() {
        this.mHtml = new MutableLiveData<>();
    }

    public MutableLiveData<String> getHtml() {
        return mHtml;
    }

    public void setHtml(int no) {
        this.mHtml.setValue(SimpleDao.getLetter(no));
    }
}
