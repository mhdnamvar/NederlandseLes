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
            case MONTHS:
                return getMonth();
            case OPEN_HOURS:
                return getAskingOpenHours();
            case APPOINTMENT:
                return getAppointments();
            case NOT_UNDERSTOOD:
                return getNotUnderstood();
            case SEASONS:
                return getSeasons();
            case IN_RESTAURANT:
                return getInRestaurant();
            case ASKING_QUESTIONS:
                return getAskingQuestions();
            case LIKE_IT_OR_NOT:
                return getLikeOrNot();
            case PRICE:
                return getPriceQuestions();
            case CHECK_PAY:
                return getCheckPay();
            case COMPARATIVES:
                return getComparatives();
            case BUSY_PHRASES:
                return getBusyPhrases();
            case IETS_LEUKS:
                return getIetsLeuks();
            default:
                return Collections.emptyList();
        }
    }
    private static List<String> getIetsLeuks() {
        String[] array = {
                "Iets, wat, niets, niks + adjectief + s",
                "We willen iets leuks gaan doen.",
                "Nieks moeilijks aan!",
                "We gaan wat anders doen."
        };
        return new ArrayList<>(Arrays.asList(array));
    }

    private static List<String> getBusyPhrases() {
        String[] array = {
                "Ik ben aan het studeren.",
                "Hij is nu aan het schoonmaken.",
                "Wij zijn aan het chatten. Straks gaan we voetballen.",
                "Ik ben beizig met de TV kijken. Zo meteen ga ik douchen.",
                "Zij is aan het afwassen.",
                "Zij is bezig met de afwas.",
                "Zij is beizig met afwassen.",
                "Zij gaat morgen afwassen."
        };
        return new ArrayList<>(Arrays.asList(array));
    }

    private static List<String> getComparatives() {
        String[] array = {
                "Lief, liever, het liefst",
                "Graag, liever, het liefst",
                "Vies, viezer, het viest",
                "Weinig, minder, het minst",
                "Veel, meer, het meest",
                "Goed, beter, het best",
        };
        return new ArrayList<>(Arrays.asList(array));
    }


    private static List<String> getCheckPay() {
        String[] array = {
                "Hoeveel moet ik afrekenen?",
                "Hoeveel krijgt u van me?",
                "Hoeveek is het bij elkaar?"
        };
        return new ArrayList<>(Arrays.asList(array));
    }

    private static List<String> getPriceQuestions() {
        String[] array = {
                "Hoeveel kost het?",
                "Wat kost dat?",
                "Hoe duur is het?"
        };
        return new ArrayList<>(Arrays.asList(array));
    }

    private static List<String> getLikeOrNot() {
        String[] array = {
                "Ik vind spinazie heerlijk.",
                "Ik ben dol op dropjes.",
                "Ik eet het leefst brood.",
                "Ik vind worteltjes niet lekker.",
                "Ik houd niet van vis.",
                "Ik lust geen spruitjes",
                "Ik vind broccoli echt vies!",
                "Ik vind haring echt smerg!"
        };
        return new ArrayList<>(Arrays.asList(array));
    }

    private static List<String> getAskingQuestions() {
        String[] array = {
                "Hoe heet je?",
                "Wat is je naam?",
                "Waar woon je?",
                "Waar kom je vandaan?",
                "Uit welk land kom je?",
                "Welke nationaliteit heb je?",
                "Wat is uw moedertaal?",
                "Welke taal spreekt u?",
                "Welke talen spreek je verder nog?",
                "Hoe spel je dat?",
                "Wat voor werk doet hij?",
                "Hoe oud is uw vader",
                "Hoe oud ben je?",
                "Wat is je beroep?",
                "Wat studeren jullie",
                "Wat is zij geboortedatum?"
        };
        return new ArrayList<>(Arrays.asList(array));
    }

    private static List<String> getInRestaurant() {
        String[] array = {
                "Ik wil graag het trio van gestoofde vis.",
                "Mogen we de wijnkaart even zien?",
                "We willen graag het driegangenmenu",
                "Als voorgerecht wil ik graag de erwten soep.",
                "Als hoofdrecht de zalmfilet, alstublileft",
                "Voor mij de Hollandse garnalencocktail graag.",
                "Kan ik ook frietjes in plaats van gebakken aardappeltjes krijgen?"
        };
        return new ArrayList<>(Arrays.asList(array));
    }

    private static List<String> getSeasons() {
        String[] array = {
                "De lente - het voorjaar",
                "De zomer",
                "De herfst - het najaar",
                "De winter"
        };
        return new ArrayList<>(Arrays.asList(array));
    }

    private static List<String> getNotUnderstood() {
        String[] array = {
                "Sorry, wat betekend dat?",
                "Pardon, ik versta u niet. Kunt u dat herhalen?",
                "Kun je dat nog eens zeggen?",
                "Ik begrijp je niet.",
                "Neemt u me niet kwalijk, wat is dat?"
        };
        return new ArrayList<>(Arrays.asList(array));
    }

    private static List<String> getAppointments() {
        String[] array = {
                "Zullen we naar het museum gaan?",
                "Ga je mee naar bioscoop?",
                "Wat zullen gaan doen?",
                "Wanneer zullen we afspraken?",
                "Ja leuk! Kun je morgen ochtend om 10:00 uur?",
                "Dat is prima. Tot dan!",
                "Ik kan om tien uur 's morgens."
        };
        return new ArrayList<>(Arrays.asList(array));
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
                "januari",
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
                "Bij de balie krigen u een formulier.",
                "De melk is over de datum.",
                "Dat is goed voor je spijsverteing.",
                "Op gewicht blijven.",
                "Aan de beurt zijn.",
                "Eem boterham met hagelslag.",
                "Hij vraagt naar de prijs.",
                "Zij doet de boodschappen bij de supermarkt.",
                "Ik koop groente op de markt.",
                "De man is op zijn werk.",
                "De krentenbollen liggen achter u.",
                "De koekjes liggen in het schap.",
                "Het ontbijt staat op tafel.",
                "Ik help Luca met het huiswerk.",
                "Ik doe aan hockey en fitness.",
                "Ik zit op voetbal.",
                "Ik ben bij een tennisclub.",
                "Ik ben aan het squashen.",
                "Ik ben beizig met mijn favoriete hobby.",
                "Ik kom net uit bed.",
                "We heben drie aardappel per persoon.",
                "Vanavond staat erweten soep op het menu.",
                "De ingeredienten staan in het recept.",
                "Hij brengt de soep op smaak met zout en peper.",
                "De hardloper heeft een tijd onder de acht minuten.",
                "Hij luister naar muziek.",
                "Ik wandel met de hond."
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
