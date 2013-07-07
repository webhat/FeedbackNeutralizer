/*
 * Copyright (c) 2013 Daniël W. Crompton <info+fbn@specialbrands.net>
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 */

package com.thelastconsultant.fbn;

import android.app.Activity;
import android.os.Bundle;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Tracker;

/**
 * Created with IntelliJ IDEA.
 * User: danielcrompton
 * Date: 4/22/13
 * Time: 7:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class AnalyticsActivity extends Activity {

    private Tracker tracker;
    /**
     * @since 9 May 2013
     * @deprecated
     */
    private Object mGaInstance;

    /**
     * @since 9 May 2013
     * @deprecated
     */
    private String mGaUA;


    /**
     * @since 9 May 2013
     * @deprecated
     */
    public String getmGaUA() {

        return mGaUA;
    }

    /**
     * @since 9 May 2013
     * @deprecated
     */
    public void setmGaUA(String mGaUA) {
        this.mGaUA = mGaUA;
    }

    /**
     * @since 9 May 2013
     * @deprecated
     */
    public Object getmGaInstance() {
        return mGaInstance;
    }

    /**
     * @since 9 May 2013
     * @deprecated
     */
    public void setmGaInstance(Object mGaInstance) {
        this.mGaInstance = mGaInstance;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EasyTracker.getInstance().activityStart(this);

        tracker = EasyTracker.getTracker();
    }

    /* GA Tracking ID: UA-34762627-4 */
    @Override
    public void onStart() {
        super.onStart();
        EasyTracker.getInstance().activityStart(this);

        tracker = EasyTracker.getTracker();
    }

    @Override
    public void onStop() {
        super.onStop();
        EasyTracker.getInstance().activityStop(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EasyTracker.getInstance().activityStart(this);
    }

    public void trackEvent(String category, String action, String label) {
        tracker.sendEvent(category, action, label, 0L);
    }
}
