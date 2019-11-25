package com.namvar.nederlandsles.ui.cards;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.namvar.nederlandsles.data.SimpleDao;

public class ShowCardViewModel extends ViewModel {
    private MutableLiveData<String> mHtml;

    public ShowCardViewModel() {
        this.mHtml = new MutableLiveData<>();
    }

    public MutableLiveData<String> getHtml() {
        return mHtml;
    }

    public void setHtml(int no) {
        this.mHtml.setValue(SimpleDao.getCard(no));
    }
}
