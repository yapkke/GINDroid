package com.appspot.yapkke.stanford.gindroid;

import android.app.*;
import android.view.*;
import android.widget.*;
import android.content.*;
import android.content.res.*;
import android.os.Bundle;

/** GINDroid main activity
 *
 * @author ykk
 * @date Nov 2011
 */
public class GINDroid extends TabActivity
{    
    public static final String MASTER_PASS = "GATES";

    /** Starting GINDroid activity
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

	//Create tabs
	Resources res = getResources();
	TabHost tabHost = getTabHost();
	TabHost.TabSpec spec; 
	Intent intent; 

	intent = new Intent().setClass(this, Now.class);
	spec = tabHost.newTabSpec("Now").setIndicator("Now").setContent(intent);
	tabHost.addTab(spec);

	intent = new Intent().setClass(this, Room.class);
	String roomExtra = getIntent().getStringExtra("Room");
	if (roomExtra != null)
	    intent.putExtra("Room", roomExtra);
	spec = tabHost.newTabSpec("Now").setIndicator("Room").setContent(intent);
	tabHost.addTab(spec);
	if (roomExtra != null)
	    tabHost.setCurrentTab(1);

	intent = new Intent().setClass(this, All.class);
	spec = tabHost.newTabSpec("All").setIndicator("All").setContent(intent);
	tabHost.addTab(spec);

	//Set tab height
	for (int i = 0; i < 3; i++)
	    tabHost.getTabWidget().getChildAt(i).getLayoutParams().height = 30;
    }

    /** Create menu from XML
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
	MenuInflater inflater = getMenuInflater();
	inflater.inflate(R.menu.main, menu);
	return true;
    }

    /** Process clicking of menu
     * 
     * (1) Bring up settings activity
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
	switch (item.getItemId()) 
	{
	case R.id.setting_menu:
	    Intent settingIntent = new Intent(this, Settings.class);
	    startActivity(settingIntent);
	    return true;
	default:
	    return super.onOptionsItemSelected(item);
	}
    }
}
