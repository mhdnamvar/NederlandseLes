package com.namvar.nederlandsles;

import java.util.ArrayList;
import java.util.List;

public enum Section {
    SENTENCES(1, "Hoofdstuken"),
    LETTERS(2, "Brieven"),
    PREPOSITIONS(3, "Preposities"),
    ;

    private int code;
    private String name;

    Section(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public boolean equals(Section s) {
        return s.code == this.code && s.name.equals(this.name);
    }

    public List<String> listValues(){
        List list = new ArrayList();
        for (Section s: values()){
            list.add(s.name);
        }
        return list;
    }
}
