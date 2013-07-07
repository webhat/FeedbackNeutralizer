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
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: danielcrompton
 *
 * @since 1.3
 */
public class Memory {
    public static final String DELIMITER = ","; // FIXME: not comma separated
    private SharedPreferences settings;

    public Memory(SharedPreferences settings) {
        setSettings(settings);
    }

    public SharedPreferences getSettings() {
        return settings;
    }

    public void setSettings(SharedPreferences settings) {
        this.settings = settings;
    }

    public void setValue(String key, String value) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);

        editor.commit();
    }

    public String getValue(String value) {
        return settings.getString(value, "");
    }

    public void setValues(String key, Object[] value) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, getString(value, DELIMITER));

        editor.commit();
    }

    /**
     * @since 1.3
     */
    public boolean setValues(String key, Map<String, String> map) {
        String hash = map.get("hash");

        SharedPreferences.Editor editor = settings.edit();
        key.concat(".");
        key.concat(hash);


        for (String keySetString : map.keySet()) {
            String entry = hash;
            entry.concat(".");
            entry.concat(keySetString);
            editor.putString(entry, map.get(keySetString));
        }

        editor.commit();

        return true;
    }

    public Map getValuesByHash(String hash) {
        Map<String, ?> keys = settings.getAll();

        for (String key : keys.keySet()) {
            if (!key.contains(hash)) {
                keys.remove(key);
            }
        }

        return keys;
    }

    private String getString(Object[] array, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (Object s : array) {
            if (sb.length() > 0) {
                sb.append(delimiter);
            }
            sb.append(s);
        }

        return sb.toString();
    }

    protected String[] getArray(String input, String delimiter) {
        return input.split(delimiter);
    }

    String getHash(String index) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(index.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }
        return new String(hash);
    }
}
