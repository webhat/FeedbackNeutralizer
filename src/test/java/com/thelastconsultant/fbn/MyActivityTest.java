/*
 * Copyright (c) 2013 Daniël W. Crompton <info+fbn@specialbrands.net>
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 */

package com.thelastconsultant.fbn;

import android.app.Activity;
import android.test.AndroidTestCase;
import android.widget.Button;
import android.widget.TextView;
import com.thelastconsultant.R;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: danielcrompton
 * Date: 4/19/13
 * Time: 3:01 PM
 */
@RunWith(RobolectricTestRunner.class)
public class MyActivityTest extends AndroidTestCase {

    private Activity activity;
    private Button pressMeButton;
    private TextView results;

    @Before
    public void setUp() throws Exception {
        activity = new MyActivity();
        ((MyActivity) activity).onCreate(null);
        pressMeButton = (Button) activity.findViewById(R.id.goalbutton);
        results = (TextView) activity.findViewById(R.id.goal);
        android.test.InstrumentationTestRunner a;
    }

    @Test
    public void shouldHaveHappySmiles() throws Exception {
        String appName = new MyActivity().getResources().getString(R.string.app_name);
        assertThat(appName, equalTo("Feedback Neutralizer"));
    }

    @Test
    public void shouldUpdateResultsWhenButtonIsClicked() throws Exception {
        pressMeButton.performClick();
        String resultsText = results.getText().toString();
        assertThat(resultsText, equalTo(""));
    }
}
