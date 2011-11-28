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

/** Now activity
 *
 * @author ykk
 * @date Nov 2011
 */
public class Now
    extends GDActivity
    implements OnItemClickListener
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
	    if (cNEvent.currEvent == null)
	    {
		tvCurrent.setText("Free");
		tvClass.setTextColor(Color.parseColor("#00ff00"));
	    }
	    else
	    {
		tvCurrent.setText(cNEvent.currEvent.timeTitle());
		tvClass.setTextColor(Color.parseColor("#ff0000"));		
	    }
	    if (cNEvent.nextEvent == null)
		tvNext.setText("Free thereafter");
	    else
		tvNext.setText(cNEvent.nextEvent.timeTitle());
	    
	    return row;
	}
    }

    /** Starting now activity for showing the current room occupancy
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
	setContentView(R.layout.now_main);
        
	new UpdateListing().execute(wg);
    }
    
    @Override
	public void refresh_listing()
    {
	ListView lv = (ListView) findViewById(R.id.now_listview);
	lv.setAdapter(new EventAdapter<CurrNextEvent>(this, R.layout.now, currNextEvents()));
	lv.setOnItemClickListener(this);
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
    {
	Intent intent = new Intent(this, GINDroid.class);
	intent.putExtra("Room",
			((TextView) view.findViewById(R.id.now_classroom)).getText());
	startActivity(intent);
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
