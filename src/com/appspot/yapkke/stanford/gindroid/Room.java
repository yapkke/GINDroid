package com.appspot.yapkke.stanford.gindroid;

import android.app.*;
import android.widget.*;
import android.widget.AdapterView.*;
import android.content.*;
import android.view.*;
import android.R.layout.*;
import android.os.Bundle;

import android.util.Log;

import java.util.*;

/** Room activity
 *
 * @author ykk
 * @date Nov 2011
 */
public class Room
    extends GDActivity
    implements OnItemSelectedListener
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
		row = mInflater.inflate(R.layout.room, null);
	    } 
	    else 
	    {
		row = convertView;
	    }
 
	    WebGin.Event e = (WebGin.Event) getItem(position);
	    TextView tvTime = (TextView) row.findViewById(R.id.room_time);
	    TextView tvTitle = (TextView) row.findViewById(R.id.room_title);
	    tvTitle.setText(e.title);
	    tvTime.setText(e.time());
	    
	    return row;
	}
    }

    /** Starting activity for room today
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_main);

	refresh_listing();
	
	//Create spinner
	Spinner spin = (Spinner) findViewById(R.id.room_spinner);
	ArrayAdapter adapter = new ArrayAdapter<String>(this, 
							android.R.layout.simple_spinner_item,
							classrooms());
	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	spin.setAdapter(adapter);
	spin.setOnItemSelectedListener(this);

	//Set pre-set room
	String room = getIntent().getStringExtra("Room");
	if (room != null)
	    for (int i = 0; i < spin.getCount(); i++)
		if (room.compareTo((String) spin.getItemAtPosition(i)) == 0)
		    spin.setSelection(i);
    }

    @Override
    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) 
    {
	//Set list view
	Spinner spin = (Spinner) findViewById(R.id.room_spinner);
	ListView lv = (ListView) findViewById(R.id.room_listview);
	lv.setAdapter(new EventAdapter<WebGin.Event>(this, R.layout.room,
						     roomEvents((String) spin.getSelectedItem())));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parentView) 
    { }
    
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

    public WebGin.Event[] roomEvents(String room)
    {
	Vector<WebGin.Event> es = new Vector<WebGin.Event>();
	for (WebGin.Event e : listing.events)
	    if (e.classroom.compareTo(room) == 0)
		es.add(e);   

	WebGin.Event[] events = new WebGin.Event[es.size()];
	for (int i = 0; i < es.size(); i++)
	    events[i] = (WebGin.Event) es.get(i);;

	return events;
    }    

}
