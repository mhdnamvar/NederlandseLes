package com.namvar.nederlandsles.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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

public class SharedViewModel extends AndroidViewModel {

    private final MutableLiveData<List<String>> chapters = new MutableLiveData<>();

    public SharedViewModel(@NonNull Application application) {
        super(application);
        try {
            chapters.setValue(loadChapters());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MutableLiveData<List<String>> getChapters() {
        return chapters;
    }

    private List<String> loadChapters() throws Exception {
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