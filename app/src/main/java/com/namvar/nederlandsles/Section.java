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
    SENTENCES_TWO_VERBS("Zinnen maken met twee werkwoorden"),
    SENTENCES_CONJUCTION("Zinnen maken met voegwoorden"),
    SENTENCES_OMDAT("Zinnen maken met Omdat"),
    SENTENCES_ALS("Zinnen maken met Als"),
    SENTENCES_DAT("Zinnen maken met Dat"),
    SENTENCES_DAAROM("Zinnen maken met Daarom en Daarmee"),
    SENTENCES_QUESTIONS_WORD("Vraagzinnen maken met vraagwoorden"),
    SENTENCES_NO_QUESTIONS_WORD("Vraagzinnen maken zonder vraagwoorden"),
    SENTENCES_INVERSION("Inversi maar geen vraagin"),
    PREPOSITIONS("Preposities"),
    NEGATIVES("Negatie"),
    OPEN_HOURS("Vragen naar openingstijden"),
    APPOINTMENT("Een afspraak maken en reageren"),
    NOT_UNDERSTOOD("Als je het niet verstaat"),
    IN_RESTAURANT("Bestellen in een restaurant"),
    LIKE_IT_OR_NOT("Wat je wel en niet lekker vindt"),
    PRICE("Naar de prijs vragen"),
    COMPARATIVES("Comparatieven en superlatieven"),
    CHECK_PAY("Afrekenen"),
    BUSY_PHRASES("Bezigheiden uitdruken"),
    SOMETHING_NICE("Iets leuks"),
    TRANSPORT("Vervoermiddelen en de weg vragen"),
    CLOTHES("Kleiding"),
    HERE_THERE("Er en daar"),
    COMPLEMENT("Een compliment geven"),
    REACTION_TO_PHRASES("Reacties op uitspraken en mededelingen"),
    FESTIVALS("Feestdagen in Nederland"),
    CHARACTERS("Karaktereigenschappen"),
    RELATIONSHIPS("Relatievormen"),
    EMOTIONS("Emoties benoemen"),
    SPEAKING("Spreken"),
    ;

    private String text;
    private static final Map<String, Section> lookup = new HashMap<>();

    static {
        for (Section s : Section.values()) {
            lookup.put(s.text, s);
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
            list.add(s.getText());
        }
        return list;
    }
}
