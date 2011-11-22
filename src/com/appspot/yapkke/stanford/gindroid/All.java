package com.appspot.yapkke.stanford.gindroid;

import android.app.*;
import android.widget.*;
import android.widget.AdapterView.*;
import android.content.*;
import android.view.*;
import android.graphics.*;
import android.os.Bundle;

import android.util.Log;

import java.util.*;

/** All activity
 *
 * @author ykk
 * @date Nov 2011
 */
public class All
    extends GDActivity
{    
    public class EventAdapter<T>
	extends ArrayAdapter<T>
    {
	public EventAdapter(Context context, int textViewResourceId, T[] objects) 
	{
            super(context, textViewResourceId, objects);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
	    View row;
	    
	    if (null == convertView)
	    {
		LayoutInflater mInflater = (LayoutInflater) 
		    getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		row = mInflater.inflate(R.layout.all, null);
	    } 
	    else 
	    {
		row = convertView;
	    }
 
	    WebGin.Event e = (WebGin.Event) getItem(position);
	    TextView tvTime = (TextView) row.findViewById(R.id.all_time);
	    TextView tvTitle = (TextView) row.findViewById(R.id.all_title);
	    TextView tvClass = (TextView) row.findViewById(R.id.all_classroom);
	    tvClass.setText(e.classroom);
	    tvTitle.setText(e.title);
	    tvTime.setText(e.time());
	    
	    return row;
	}
    }

    /** Starting now activity for showing the current room occupancy
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
	setContentView(R.layout.all_main);
        
	refresh_listing();

	ListView lv = (ListView) findViewById(R.id.all_listview);
	lv.setAdapter(new EventAdapter<WebGin.Event>(this, R.layout.all, allEvents()));
    }
    
    public WebGin.Event[] allEvents()
    {
	WebGin.Event[] events = new WebGin.Event[listing.events.size()];

	for (int i = 0; i < listing.events.size(); i++)
	    events[i] = (WebGin.Event) listing.events.get(i);
	return events;
    }    
}
