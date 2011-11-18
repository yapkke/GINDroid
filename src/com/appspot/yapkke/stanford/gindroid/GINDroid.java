package com.appspot.yapkke.stanford.gindroid;

import android.app.Activity;
import android.os.Bundle;

public class GINDroid extends Activity
{
    /** WebGin instance
     */
    WebGin wg;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

	wg = new WebGin();
	wg.auth("usename", "password");
    }
}
