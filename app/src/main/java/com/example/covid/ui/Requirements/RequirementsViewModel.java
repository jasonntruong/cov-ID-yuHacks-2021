package com.example.covid.ui.Requirements;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RequirementsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RequirementsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue(" ");
    }

    public LiveData<String> getText() {
        return mText;
    }
}