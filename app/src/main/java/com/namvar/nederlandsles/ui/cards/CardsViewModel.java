package com.namvar.nederlandsles.ui.cards;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.namvar.nederlandsles.data.SimpleDao;

import java.util.List;

public class CardsViewModel extends ViewModel {

    private MutableLiveData<List<String>> mCards;

    public CardsViewModel() {
        mCards = new MutableLiveData<>();
        try {
            mCards.setValue(SimpleDao.getCards());
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("CardsViewModel", e.getLocalizedMessage());
        }
    }

    public MutableLiveData<List<String>> getCards() {
        return mCards;
    }
}