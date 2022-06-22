package com.github.cxlina.smpbot.util;

import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Locale;

public class CumManager {

    private final HashMap<String,String> cumTranslations = new HashMap<>();

    public CumManager(ConfigurationSection cumSection) {
        for(String wrongWord : cumSection.getKeys(false)) {
            cumTranslations.put(wrongWord.toLowerCase(Locale.ROOT), cumSection.getString(wrongWord));
        }
    }

    public String getCumTranslation(String word) {
        return cumTranslations.get(word.toLowerCase(Locale.ROOT).replaceAll("[^a-z]", ""));
    }

}
