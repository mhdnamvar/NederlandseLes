package com.namvar.nederlandsles.data;

import android.content.Context;

import com.namvar.nederlandsles.R;
import com.namvar.nederlandsles.Section;

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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class SimpleDao {

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

    public static List<String> getSentences() {
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

    public static List<String> getSentence(int section) {
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

    public static List<String> get(Section s) {
        switch (s) {
            case SENTENCES:
                return getSentences();
            case NEGATIVES:
                return getNegatives();
            case LETTERS:
                return getLetters();
            case PREPOSITIONS:
                return getPrepositions();
            case DAYS_OF_THE_WEEK:
                return getDaysOfTheWeek();
            case Months:
                return getMonth();
            case ASK_OPENING_HOURS:
                return getAskingOpenHours();
            default:
                return Collections.emptyList();
        }
    }

    private static List<String> getAskingOpenHours() {
        String[] array = {
                "Wanneer is het museum open?",
                "Hoe laat gaat het museum dicht?",
                "Van hoe laat tot hoe laat is het restaurant open?",
                "Hoe laat sluit de school?",
                "Op welke dagen is bibliotheek open?"
        };
        return new ArrayList<>(Arrays.asList(array));
    }

    private static List<String> getMonth() {
        String[] array = {
                "january",
                "februari",
                "maart",
                "april",
                "mei",
                "juni",
                "juli",
                "augustus",
                "september",
                "oktober",
                "november",
                "december",
        };
        return new ArrayList<>(Arrays.asList(array));
    }

    private static List<String> getDaysOfTheWeek() {
        String[] array = {
                "maandag",
                "dinsdag",
                "woensdag",
                "donderdag",
                "vrijdag",
                "zaterdag",
                "zondag"
        };
        return new ArrayList<>(Arrays.asList(array));
    }

    private static List<String> getPrepositions() {
        String[] array = {
                "Zullen we naar het museum gaan?",
                "Het museum is open van 09:00 tot 17:00.",
                "Het is gesloten op maandag.",
                "Om drie uur kan ik niet.",
                "In de nacht van zaterdag op zondag.",
                "Dit schilderij is van Rembrant.",
                "Ik kijk naar het schilderij.",
                "Een museum voor kunst en archeologie.",
                "In het weekend is het niet zo druk.",
                "Tussen de middag eet ik.",
                "Hij wacht op een vriendin.",
                "Hij werkt in een museum.",
                "Ik ben druk me de studie.",
                "Ik heb het druk met de examens.",
                "Zuurkool met rookworst.",
                "Na het weekend ga ik op vakantie.",
                "Ik woon in Nederland.",
                "Woon jij in Amsterdam?",
                "Zij komt uit Denemarken.",
                "Ik woon in de Langestraat op nummer 13.",
                "Wij zijn op vakantie.",
                "Wij praten met de cursisten in de klas.",
                "Bij de balie krigen u een formulier."
        };
        return new ArrayList<>(Arrays.asList(array));
    }

    private static List<String> getNegatives() {
        String[] array = {
                "Nee, Ik kan niet op woensdag.",
                "Nee, op woensdag heb ik geen tijd."
        };
        return new ArrayList<>(Arrays.asList(array));

    }
}
