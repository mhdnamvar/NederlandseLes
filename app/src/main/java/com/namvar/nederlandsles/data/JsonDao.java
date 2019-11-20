package com.namvar.nederlandsles.data;

import android.content.Context;

import com.namvar.nederlandsles.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class JsonDao {

    private static JSONObject jsonObject;

    public static void read(Context context) {
        try {
            InputStream is = context.getResources().openRawResource(R.raw.schrijven);
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            Reader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
            jsonObject = new JSONObject(writer.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> getSections() {
        try {
            JSONArray hoofdstukken = jsonObject.getJSONArray("hoofdstukken");
            List<String> titles = new ArrayList<>();
            for (int i = 0; i < hoofdstukken.length(); i++) {
                titles.add(hoofdstukken.getJSONObject(i).getString("titel"));
            }
            return titles;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static List<String> getSection(int section) {
        try {
            JSONArray hoofdstukken = jsonObject.getJSONArray("hoofdstukken");
            if(section > hoofdstukken.length()) {
                return Collections.emptyList();
            }
            List<String> examples = new ArrayList<>();
            JSONArray exercises = hoofdstukken.getJSONObject(section).getJSONArray("oefeningen");
            for (int i = 0; i < exercises.length(); i++) {
                JSONObject exercise = exercises.getJSONObject(i);
                String question = exercise.getString("vraag");
                JSONArray answers = exercise.getJSONArray("antwoorden");
                if (answers.length() > 0 ) {
                    examples.add(question + " " + answers.getString(0));
                }
            }
            return examples;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static List<String> getLetters() {
        try {
            JSONArray brieven = jsonObject.getJSONArray("brieven");
            List<String> titles = new ArrayList<>();
            for (int i = 0; i < brieven.length(); i++) {
                titles.add(brieven.getJSONObject(i).getString("titel"));
            }
            return titles;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static String getLetter(int no) {
        try {
            JSONArray brieven = jsonObject.getJSONArray("brieven");
            JSONObject letter = brieven.getJSONObject(no);
            JSONArray answers = letter.getJSONArray("antwoorden");
            if (answers.length() > 0) {
                return answers.getString(0);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static List<String> getPrepositions() {
        try {
            JSONArray prepositions = jsonObject.getJSONArray("preposities");
            List<String> list = new ArrayList<>();
            for (int i = 0; i < prepositions.length(); i++) {
                list.add(prepositions.getString(i));
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

}
