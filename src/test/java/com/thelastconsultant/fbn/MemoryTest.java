/*
 * Copyright (c) 2013 Daniël W. Crompton <info+fbn@specialbrands.net>
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 */

package com.thelastconsultant.fbn;

import android.content.Context;
import android.content.SharedPreferences;
import android.test.AndroidTestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class MemoryTest extends AndroidTestCase {
    Memory memory = null;

    @Test
    public void testSomething() {
        assertTrue(true);
    }

    @Test
    public void testSetValues() throws Exception {
        //memory.setValues();
    }

    @Test
    public void testGetValue() throws Exception {
        String expected = "My Goal";
        memory.setValue("goal", expected);
        String actual = memory.getValue("goal");

        assertEquals(expected,actual);
    }

    @Before
    public void setUp() throws Exception {
        Context context = getContext();
        SharedPreferences settings = context.getSharedPreferences(MyActivity.PREFS_NAME, 0);
        memory = new Memory(settings);
    }
}

