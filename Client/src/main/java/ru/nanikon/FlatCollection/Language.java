package ru.nanikon.FlatCollection;

import java.util.Locale;

public enum Language {
    RUSSIAN("ru", "RU", "russian"),
    SLOVAK("sk", "SK", "slovak"),
    DANISH("da", "DA", "danish"),
    SPANISH("es", "EC", "spanish");
    private final Locale locale;
    private final String languageStr;

    Language(String language, String country, String languageStr) {
        locale = new Locale(language, country);
        this.languageStr = languageStr;
    }

    public Locale getLocale() {
        return this.locale;
    }

    @Override
    public String toString() {
        return App.getRB().getString(languageStr + "Language");
    }
}
