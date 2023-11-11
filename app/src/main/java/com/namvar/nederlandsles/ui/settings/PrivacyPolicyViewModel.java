package com.namvar.nederlandsles.ui.settings;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.namvar.nederlandsles.TermsAndConditionsDialogFragment;
import com.namvar.nederlandsles.data.SimpleDao;

public class PrivacyPolicyViewModel extends ViewModel {

    private final MutableLiveData<String> mHtml;

    public PrivacyPolicyViewModel() {
        this.mHtml = new MutableLiveData<>();
        this.mHtml.setValue(TermsAndConditionsDialogFragment.PRIVACY_POLICY);
    }

    public MutableLiveData<String> getHtml() {
        return mHtml;
    }
}