package com.namvar.nederlandsles.ui.home;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.namvar.nederlandsles.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private MutableLiveData<List<String>> mChapters;

    public HomeViewModel(@NonNull Application application) {
        super(application);

        mChapters = new MutableLiveData<>();
        try{
            mChapters.setValue(fetchChapters());
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("HomeViewModel", e.getLocalizedMessage());
        }
    }


    public MutableLiveData<List<String>> getChapters() {
        return mChapters;
    }

    private List<String> fetchChapters() throws Exception {
        try (InputStream is = getApplication().getResources().openRawResource(R.raw.schrijven)) {
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }

            JSONObject json = new JSONObject(writer.toString());
            JSONArray hoofdstukken = json.getJSONArray("hoofdstukken");
            JSONArray brieven = json.getJSONArray("brieven");

            List<String> titles = new ArrayList<>();
            for (int i = 0; i < hoofdstukken.length(); i++) {
                titles.add(hoofdstukken.getJSONObject(i).getString("titel"));
            }
            for (int i = 0; i < brieven.length(); i++) {
                titles.add(brieven.getJSONObject(i).getString("titel"));
            }
            return titles;
        }
    }
}