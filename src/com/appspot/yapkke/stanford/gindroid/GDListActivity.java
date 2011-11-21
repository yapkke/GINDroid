package com.appspot.yapkke.stanford.gindroid;

import android.app.*;
import android.content.*;
import android.preference.*;

import android.os.Bundle;

/** GINDroid's ListActivity
 * 
 * @author ykk
 * @date Nov 2011
 */
public class GDListActivity
    extends ListActivity
{
    /** WebGin instance
     */
    protected WebGin wg;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

	//Create new WebGin
	SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
	wg = new WebGin();
	wg.auth(settings.getString("username", ""),
		settings.getString("password", ""));	
    }
}