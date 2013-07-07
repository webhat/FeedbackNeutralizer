/*
 * Copyright (c) 2013 Daniël W. Crompton <info+fbn@specialbrands.net>
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 */

package com.thelastconsultant.fbn;

import android.content.SharedPreferences;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @since 1.3
 */
public class FeedbackNeutralizerMemory extends Memory {

    public FeedbackNeutralizerMemory(SharedPreferences settings) {
        super(settings);
    }

    public String getGoal() {
        return getSettings().getString("goal", "");
    }

    public void setGoal(String goal) {
        setValue("goal", goal);
    }

    public String[] getAntipatterns() {
        return getArray(getSettings().getString("antipatterns", ""), ",");
    }

    public void setAntipattern(String antipattern) {
        List<String> antipatterns = new ArrayList<String>(Arrays.asList(getAntipatterns()));
        antipatterns.add(antipattern);
        setValues("antipatterns", (Object[]) antipatterns.toArray());
    }

    public String[] getFears() {
        return getArray(getSettings().getString("fears", ""), ",");
    }

    public void setFear(String fear) {
        List<String> fears = new ArrayList<String>(Arrays.asList(getFears()));
        fears.add(fear);
        setValues("fears", (Object[]) fears.toArray());
    }
    /**
     * @since 1.3
     */
    public boolean setValues(String key, Map<String, String> map) {
        String hash = map.get("hash");

        return setValues(key, map);
    }
}