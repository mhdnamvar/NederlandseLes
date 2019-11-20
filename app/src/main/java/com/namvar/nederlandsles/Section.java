package com.namvar.nederlandsles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Section {
    DAYS_OF_THE_WEEK("Dagen van de week"),
    MONTHS("Maanden"),
    SEASONS("De seizoenen"),
    ASKING_QUESTIONS("Vragen stellen"),
    SENTENCES("Zinnen maken"),
    LETTERS("Brieven"),
    PREPOSITIONS("Preposities"),
    NEGATIVES("Negatie"),
    OPEN_HOURS("Vragen naar openingstijden"),
    APPOINTMENT("Een afspraak maken en reageren"),
    NOT_UNDERSTOOD("Als je het niet begrijpt"),
    IN_RESTAURANT("Bestellen in een restaurant"),
    LIKE_IT_OR_NOT("Wat je wel en niet lekker vindt")
    ;

    private String text;
    private static final Map<String, Section> lookup = new HashMap<>();

    static {
        for (Section s : Section.values()) {
            lookup.put(s.text, s);
        }
    }

    Section(String name) {
        this.text = name;
    }

    public static Section get(String text) {
        return lookup.get(text);
    }

    public static List<String> listValues(){
        List<String> list = new ArrayList<>();
        for (Section s: values()){
            list.add(s.text);
        }
        return list;
    }
}
