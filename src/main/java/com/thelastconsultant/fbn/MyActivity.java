/*
 * Copyright (c) 2013 Daniël W. Crompton <info+fbn@specialbrands.net>
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 */

package com.thelastconsultant.fbn;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.thelastconsultant.R;

import static com.thelastconsultant.fbn.FeedbackNeutralizerState.*;

//import com.google.android.apps.analytics.easytracking.EasyTracker;

public class MyActivity extends AnalyticsActivity {
    private FeedbackNeutralizerMemory memory;

    protected static final String PREFS_NAME = "fbnmemory.1";
    private static final String TAG = MyActivity.class.getName();
    private static final String BULLET = "\t• ";
    private Integer score = 0;

    private Object[] antipatternArray;
    private int antipatternCount = 0;

    private int currentapiVersion = Build.VERSION.SDK_INT;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        memory = new FeedbackNeutralizerMemory(getSharedPreferences(PREFS_NAME, 0));


        triage(GOAL);

        //triage(FEARS);
    }

    private void triage(FeedbackNeutralizerState state) {

        final View goaltext;

        switch (state) {

            case ANTIPATTERN:
                setContentView(R.layout.antipattern_v2);
                drawScore();


                goaltext = (View) findViewById(R.id.goal);
                ((TextView) goaltext).setText(memory.getGoal());

                final Button antisave = (Button) findViewById(R.id.antisave);
                final Button antinext = (Button) findViewById(R.id.antinext);
                final EditText antit = (EditText) findViewById(R.id.anti);


                Log.w(TAG, "AP Count:" + memory.getAntipatterns().length);
                if (memory.getAntipatterns().length >= 3)
                    antinext.setEnabled(true);

                /*
                if (currentapiVersion < 11) {
                    Log.d(TAG, "Version: < 11");
                    if (((EditText) antit).getText().length() <= 0)
                    ((EditText) antit).setText(R.string.antipatternquestion);

                    ((EditText) antit).addTextChangedListener(new TextWatcher() {

                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            antit.setText("");
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            //  ((TextView) goaltext).setText(s);
                        }

                        public void afterTextChanged(Editable s) {
                        }
                    });
                }
                */

                final EditText anti = (EditText) findViewById(R.id.anti);
                antisave.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        String antitext = anti.getText().toString();
                        Log.w(TAG, antitext);
                        memory.setAntipattern(antitext);

                        if (getScore() < 40)
                            incrementScore(8);
                        else
                            incrementScore(3);


                        trackEvent("triage", "save", "antipattern");

                        triage(ANTIPATTERN);
                    }
                }
                );
                antinext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        antisave.performClick();

                        trackEvent("triage", "next", "antipattern");
                        triage(FEARS);
                    }
                });

                break;

            case FEARS:
                setContentView(R.layout.fears_v2);
                drawScore();

                goaltext = (TextView) findViewById(R.id.fears);

                if (antipatternCount == 0)
                    antipatternCount = memory.getAntipatterns().length;
                if (antipatternCount > 0) {
                    antipatternArray = memory.getAntipatterns();
                    ((TextView) goaltext).setText((String) antipatternArray[--antipatternCount]);
                }

                final Button fearsave = (Button) findViewById(R.id.fearsave);
                final Button fearnext = (Button) findViewById(R.id.fearnext);

                final EditText goal = (EditText) findViewById(R.id.fear);

                   /*
                if (currentapiVersion < 11) {
                    Log.d(TAG, "Version: < 11");
                    if (((EditText) goal).getText().length() <= 0)
                        ((EditText) goal).setText(R.string.goalquestion);

                    ((EditText) goal).addTextChangedListener(new TextWatcher() {

                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            goal.setText("");
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            //  ((TextView) goaltext).setText(s);
                        }

                        public void afterTextChanged(Editable s) {
                        }
                    });
                }  */


                Log.w(TAG, "Fear Count:" + memory.getFears().length);

                if (memory.getFears().length >= 3)
                    fearnext.setEnabled(true);

                final EditText fear = (EditText) findViewById(R.id.fear);
                fearsave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String feartext = fear.getText().toString();
                        Log.w(TAG, feartext);
                        memory.setFear(feartext);

                        if (getScore() < 70)
                            incrementScore(12);
                        else
                            incrementScore(3);


                        trackEvent("triage", "save", "fears");

                        triage(FEARS);
                    }
                }
                );

                fearnext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fearsave.performClick();

                        trackEvent("triage", "next", "fears");
                        triage(RESULT);
                    }
                });
                break;

            case RESULT:
                setContentView(R.layout.result_v2);
                drawScore();

                trackEvent("triage", "view", "result");

                trackEvent("triage", getScore().toString(), "score");

                TextView fearstext = (TextView) findViewById(R.id.fears);
                goaltext = (View) findViewById(R.id.goal);
                ((TextView) goaltext).setText(memory.getGoal());
                final StringBuffer fears = new StringBuffer("");

                Button sendToMyself = (Button) findViewById(R.id.send);

                for (String feartext : memory.getFears()) {
                    if (feartext.contentEquals("")) continue;

                    fears.append(BULLET);
                    fears.append(feartext);
                    fears.append("\n");
                }

                Log.w(TAG, "Fears: " + memory.getFears().toString());
                Log.w(TAG, fears.toString());

                fearstext.setText(fears.toString());

                sendToMyself.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {


                        trackEvent("triage", "send", "result");

                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.putExtra(Intent.EXTRA_EMAIL, "emailaddress@emailaddress.com");
                        intent.putExtra(Intent.EXTRA_SUBJECT, memory.getGoal());
                        intent.putExtra(Intent.EXTRA_TEXT, fears.toString());

                        intent.setType("text/plain");
                        //intent.setType("message/rfc822");
                        startActivity(Intent.createChooser(intent, "Send to ..."));
                    }
                });

                break;

            case GOAL:
            default:
                setContentView(R.layout.main_v2);
                drawScore();

                goaltext = (View) findViewById(R.id.goal);
                ((EditText) goaltext).setText(memory.getGoal());
                Button resultbutton = (Button) findViewById(R.id.resultbutton);

                if (memory.getFears().length > 0) {
                    resultbutton.setEnabled(true);

                    resultbutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            triage(RESULT);
                        }
                    });
                }


                final EditText goalx = (EditText) findViewById(R.id.goal);

                            /*
                if (currentapiVersion < 11) {
                    Log.d(TAG, "Version: < 11");
                    if (((EditText) goalx).getText().length() <= 0)
                        ((EditText) goalx).setText(R.string.goalquestion);

                    ((EditText) goalx).addTextChangedListener(new TextWatcher() {

                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            goalx.setText("");
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            //  ((TextView) goaltext).setText(s);
                        }

                        public void afterTextChanged(Editable s) {
                        }
                    });
                }           */

                Button goalsave = (Button) findViewById(R.id.goalbutton);
                final EditText goalt = (EditText) findViewById(R.id.goal);
                goalsave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        memory.setGoal(goalt.getText().toString());
                        incrementScore(10);

                        trackEvent("triage", "save", "goal");

                        triage(ANTIPATTERN);
                    }
                }
                );
                break;

        }

    }

    private void incrementScore(Integer score) {
        Integer total = getScore();
        int score1 = total + score;
        if (score1 > 100)
            score1 = 100;
        setScore(score1);
    }

    public void setScore(Integer score) {
        memory.setValue("score", score.toString());
    }

    public Integer getScore() {
        String score = memory.getValue("score");

        if (score == "") score = "0";

        return new Integer(score);
    }

    private void drawScore() {
        TextView complete = (TextView) findViewById(R.id.complete);
        complete.setText(getScore().toString() + "%");
    }
}
