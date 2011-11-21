package com.appspot.yapkke.stanford.gindroid;

import android.app.*;
import android.widget.*;
import android.content.*;
import android.view.*;
import android.os.Bundle;

import android.util.Log;

import java.util.*;

/** Now activity
 *
 * @author ykk
 * @date Nov 2011
 */
public class Now
    extends GDListActivity
{
    /** Listing
     */
    WebGin.Listing listing;
    
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
		row = mInflater.inflate(R.layout.now, null);
	    } 
	    else 
	    {
		row = convertView;
	    }
 
	    CurrNextEvent cNEvent = (CurrNextEvent) getItem(position);
	    TextView tvClass = (TextView) row.findViewById(R.id.now_classroom);
	    TextView tvCurrent = (TextView) row.findViewById(R.id.now_current);
	    TextView tvNext = (TextView) row.findViewById(R.id.now_next);
	    tvClass.setText(cNEvent.classroom);
	    tvCurrent.setText("Testing");
	    tvNext.setText("Testing");
	    
	    return row;
	}
    }

    /** Starting now activity for showing the current room occupancy
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
	refresh_listing();

	setListAdapter(new EventAdapter<CurrNextEvent>(this, R.layout.now, currNextEvents()));
		       
	ListView lv = getListView();
	lv.setTextFilterEnabled(true);
    }

    public void refresh_listing()
    {
	listing = wg.briefListing();
    }

    class CurrNextEvent
    {
	public String classroom;
	public WebGin.Event currEvent = null;
	public WebGin.Event nextEvent = null;

	public String toString()
	{
	    if (currEvent != null)
		return currEvent.toString();
	    else
		return nextEvent.toString();
	}
    }

    /** Get current and next event
     */
    public CurrNextEvent[] currNextEvents()
    {
	CurrNextEvent cNEvent[] = new CurrNextEvent[listing.classrooms.size()];
	int cIndex = 0;
	for (String c : listing.classrooms)
	{
	    cNEvent[cIndex] = new CurrNextEvent();
	    cNEvent[cIndex].classroom = c;
	    cNEvent[cIndex].currEvent = currEvent(c);
	    cNEvent[cIndex].nextEvent = nextEvent(c);

	    cIndex++;
	}

	return cNEvent;
    }

    public WebGin.Event nextEvent(String classroom)
    {
	WebGin.Event nEvent = null;
	
	Integer now = now();
	for (WebGin.Event e : listing.events)
	    if (e.classroom.compareTo(classroom) == 0)
	    {
		if ((e.start > now) && 
		    ((nEvent == null) || (e.start < nEvent.start)))
		    nEvent = e;
	    }

	return nEvent;
    }

    public WebGin.Event currEvent(String classroom)
    {
	WebGin.Event cEvent = null;
	
	Integer now = now();
	for (WebGin.Event e : listing.events)
	    if (e.classroom.compareTo(classroom) == 0)
	    {
		if ((e.start <= now) && (e.end >= now))
		    cEvent = e;
	    }

	return cEvent;
    }

    public Integer now()
    {
	Calendar c = Calendar.getInstance();
	return new Integer(c.get(Calendar.HOUR_OF_DAY)*100+c.get(Calendar.MINUTE));
    }
}
