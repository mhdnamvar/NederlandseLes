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

    public static List<String> getCards() {
        String[] array = {
                "De verjaardag",
                "De geboorte",
                "Het huwelijk",
                "De ziekte",
                "Het examen",
                "Het overlijden",
        };
        return new ArrayList<>(Arrays.asList(array));
    }

    public static String getCard(int no) {
        String[] array = {
                "Beste Hans,<br><br>Van harte gefeliciteerd met je verjaardag!<br><br>Groetjes,<br>Nancy",
                "Lieve Aisha,<br><br>Van harte gefeliciteerd met de geboorte van jullie tweeling. Ik wil graag de baby's bekijken, daarom bel ik je om een afspraak te maken.<br><br>Groetjes,<br>Nancy",
                "Beste Peter en Wanda,<br><br>Van harte gefeliciteerd met jullie huwelijk! Helaas kan ik morgen niet naar de trouwerij komen, maar ik kom wel met Pim en de kinderen naar het feest.<br><br>Groetjes,<br>Nancy",
                "Lieve opa,<br><br>Van harte beterschap!<br>Omdat mijn kinderen ziek zijn, kan ik niet naar het ziekenhuis komen. Ik kom wel als je weer thuis bent.<br><br>Groetjes,<br>Nancy",
                "Lieve Marja,<br><br>Van harte gefeliciteerd met het halen van je examen. Goed gedaan!<br><br>Groetjes,<br>Nancy",
                "Beste familie Gerristen,<br><br>Gecondoleerd met het verlies van uw vader, ik wens jullie veel sterkte. Ik kom met mijn man naar de begrafenis.<br><br>Met vriendelijke groet,<br>Nancy Van Dijk"
        };
        if (no >= 0 && no < array.length){
            return array[no];
        } else {
            return "";
        }

    }

    public static List<String> get(Section s) {
        switch (s) {
            case SENTENCES: return getSentence(0);
            case SENTENCES_TWO_VERBS: return getSentence(1);
            case SENTENCES_CONJUCTION: return getSentence(2);
            case SENTENCES_OMDAT: return getSentence(3);
            case SENTENCES_ALS: return getSentence(4);
            case SENTENCES_DAT: return getSentence(5);
            case SENTENCES_DAAROM: return getSentence(6);
            case SENTENCES_QUESTIONS_WORD: return getSentence(7);
            case SENTENCES_NO_QUESTIONS_WORD: return getSentence(8);
            case SENTENCES_INVERSION: return getSentence(9);
            case NEGATIVES: return getNegatives();
            case PREPOSITIONS: return getPrepositions();
            case DAYS_OF_THE_WEEK: return getDaysOfTheWeek();
            case MONTHS: return getMonth();
            case OPEN_HOURS: return getAskingOpenHours();
            case APPOINTMENT: return getAppointments();
            case NOT_UNDERSTOOD: return getNotUnderstood();
            case SEASONS: return getSeasons();
            case IN_RESTAURANT: return getInRestaurant();
            case ASKING_QUESTIONS: return getAskingQuestions();
            case LIKE_IT_OR_NOT: return getLikeOrNot();
            case PRICE: return getPriceQuestions();
            case CHECK_PAY: return getCheckPay();
            case COMPARATIVES: return getComparatives();
            case BUSY_PHRASES: return getBusyPhrases();
            case SOMTHING_NICE: return getIetsLeuks();
            case TRANSPORT: return getTransport();
            case CLOTHES: return getClothes();
            case HERE_THERE: return getHereAndThere();
            case COMPLEMENT: return getComplements();
            case REACTION_TO_PHRASES: return getReactionToPhrases();
            case FESTIVALS: return getFestivals();
            case CHARACTERS: return getCharacters();
            case RELATIONSHIPS: return getRelationships();
            case EMOTIONS: return getEmotions();
            default: return Collections.emptyList();
        }
    }

    private static List<String> getEmotions() {
        String[] array = {
                "ontroerd",
                "blij",
                "gelukkig",
                "bang",
                "nerveus",
                "zenuwachtig",
                "ontspannen",
                "boos",
                "kwaad",
                "verdrietig",
                "triest",
                "moe",
                "verbaasd",
                "verlegen",
                "verveeld",
                "teleurgesteld"
        };
        return new ArrayList<>(Arrays.asList(array));
    }

    private static List<String> getRelationships() {
        String[] array = {
                "getrouwd",
                "laterlatie",
                "co-ouderschap",
                "verloofd",
                "gescheiden",
                "single",
                "vrijgezel"
        };
        return new ArrayList<>(Arrays.asList(array));
    }

    private static List<String> getCharacters() {
        String[] array = {
                "optimistisch",
                "pessimistisch",
                "introvert",
                "extrovert",
                "humoristisch",
                "arrogant",
                "vrolijk",
                "netjes",
                "slordig",
                "druk",
                "rustig",
                "eerlijk",
                "onzeker",
                "sociaal",
                "koppig",
                "romantisch",
                "spontaan",
                "eigenwijs",
                "agressief",
                "gevoelig",
                "behulpzaam",
                "nieuwsgierig",
                "hard",
                "ongeduldig",
                "vriendelijk",
                "lui",
                "aardig",
                "sympathiek",
                "perfectionistich",
                "egoistisch"
        };
        return new ArrayList<>(Arrays.asList(array));
    }

    private static List<String> getFestivals() {
        String[] array = {
                "nieuwjaarsdag",
                "Pasen",
                "Koningsdag",
                "Bevrijdingsdag",
                "Hemelvaartsdag",
                "Pinksteren",
                "Sinterklaas",
                "Kerst"
        };
        return new ArrayList<>(Arrays.asList(array));
    }

    private static List<String> getReactionToPhrases() {
        String[] array = {
                "Veel plezier!",
                "Beterschap!",
                "Goede reis!",
                "Wat jammer!",
                "Wat vervelend!",
                "Wat erg voor jullie!",
                "Veel succes!",
                "Sterkte!",
                "Gefeliciteerd!",
                "Gecondoleerd!",
                "Van harte!",
                "Volgende keer beter!",
                "Sorry, hoor!"
        };
        return new ArrayList<>(Arrays.asList(array));
    }

    private static List<String> getComplements() {
        String[] array = {
                "Wat heb je mooie ogen!",
                "Wat een prachtige trui hebt u vandaag aan!",
                "Wat een leuke broek!",
                "Wat een mooie rokje!",
                "Wat een leuke tas heb je!",
                "Die kleur staat je fantastisch!",
                "Wat zie je er leuk uit!",
                "Wat zit je haat mooie vandaag!",
                "Je draagt een prachtige jurk vandaag!"
        };
        return new ArrayList<>(Arrays.asList(array));
    }

    private static List<String> getHereAndThere() {
        String[] array = {
                "Ik woon in Amsterdam. Ik woon er al vijf jaar.",
                "Ik woon in Amsterdam. Daar woon ik al vijf jaar.",
        };
        return new ArrayList<>(Arrays.asList(array));
    }

        private static List<String> getClothes() {
        String[] array = {
                "Ik draag een rok.",
                "Ik heb een jurk aan.",
                "Ik trek een broek aan.",
                "Ik heb maat 42.",
                "Ik ben gekleed in een lange jurk.",
                "Ik heb tweehands kleren.",
                "Ik heb een muts op.",
                "Ik zet een hoed op.",
                "Deze schoenen zitten leker",
                "Kan ik u misschien helpen?",
                "Welke maat hebt u?",
                "Wilt u ze passen?",
                "De paskamers zijn achter in de winkel.",
                "De overhemden zijn nu de helft goedkoper.",
                "Ik wil liever rondkijken.",
                "Ik zoek een spijkerbroek",
                "Ik wil ze graag even passen?",
                "Kan ik dit even passen?",
                "Hebt u dit overhem ook een maatje kleiner?",
                "Hebt u deze broek misschien in een andere kleur?"
        };
        return new ArrayList<>(Arrays.asList(array));
    }

    private static List<String> getTransport() {
        String[] array = {
                "Weet u de weg naar centraal station?",
                "Kunnen jullie me de weg wijzen naar de supermarkt?",
                "Weet u waar de bioscoop is?",
                "Weet u soms waar ik bibliotheek kan vinden?",
                "Weten jullie een WC in de buurt?",
                "U gaat rechtdoor.",
                "U kunt hier beter omkeren.",
                "U moet de weg oversteken.",
                "U neemt de eerste afslag rechts.",
                "Het is aan de rechterkant.",
                "Het ligt aan uw rechterhand ",
                "Ik ben te voet.",
                "Ik ben te lopend.",
                "Ik neem de auto.",
                "Ik pak de fiets.",
                "Ik ga met de metro.",
                "Ik reis met de trein."
        };
        return new ArrayList<>(Arrays.asList(array));
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
                "Ik kan om tien uur 's morgens.",
                "Heb je zin om naar de film te gaan?",
                "Ga je me naar het cafe?"
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
                "Ik wandel met de hond.",
                "De weg vragen naar het theater.",
                "Bij het station rechtsaf.",
                "Na de stoplichten linksaf.",
                "U gaat de brug over.",
                "Aan de rechterkant.",
                "Aan uw linkerhand.",
                "Ik ben dol op opera.",
                "Ik heb een trui aan."
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
