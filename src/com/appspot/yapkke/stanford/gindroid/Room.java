package com.appspot.yapkke.stanford.gindroid;

import android.app.*;
import android.widget.*;
import android.content.*;
import android.R.layout.*;
import android.os.Bundle;

import android.util.Log;

/** Room activity
 *
 * @author ykk
 * @date Nov 2011
 */
public class Room
    extends GDActivity
{
    /** Starting activity for room today
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_main);

	refresh_listing();

	//Get intent
	String room = getIntent().getStringExtra("Room");
	if (room != null)
	    Log.d("Room", room);
	
	//Create spinner
	Spinner spin = (Spinner) findViewById(R.id.room_spinner);
	ArrayAdapter adapter = new ArrayAdapter<String>(this, 
							android.R.layout.simple_spinner_item,
							classrooms());
	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	spin.setAdapter(adapter);
    }

    public String[] classrooms()
    {
	String[] cs = new String[listing.classrooms.size()];
	int index = 0;
	for (String c: listing.classrooms)
	{
	    cs[index] = c;
	    index++;
	}

	return cs;
    }
}
