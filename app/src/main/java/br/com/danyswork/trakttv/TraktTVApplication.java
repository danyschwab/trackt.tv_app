package br.com.danyswork.trakttv;

import android.app.Application;

import br.com.danyswork.trakttv.request.TraktTVVolley;

public class TraktTVApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TraktTVVolley.initialize(this);
    }
}
