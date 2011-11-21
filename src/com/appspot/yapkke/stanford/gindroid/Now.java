package com.appspot.yapkke.stanford.gindroid;

import android.app.*;
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
{
    /** Listing
     */
    WebGin.Listing listing;
    
    /** Starting now activity for showing the current room occupancy
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.now);

	refresh_listing();

	Integer now = now();
	for (String c : listing.classrooms)
	{
	    WebGin.Event e = nextEvent(c);
	    if (e != null)
	    {
		if (e.start < now)
		    Log.d("Now", c+" "+e.title);
		else
		    Log.d("Now", c+" free till "+e.start);
	    }
	    else
	    {
		Log.d("Now", c+" free");
	    }
	}
    }

    public void refresh_listing()
    {
	listing = wg.briefListing();
    }

    public WebGin.Event nextEvent(String classroom)
    {
	WebGin.Event nEvent = null;
	
	Integer now = now();
	for (WebGin.Event e : listing.events)
	    if (e.classroom.compareTo(classroom) == 0)
	    {
		if ((e.end > now) && 
		    ((nEvent == null) || (e.start < nEvent.start)))
		    nEvent = e;
	    }

	return nEvent;
    }

    public Integer now()
    {
	Calendar c = Calendar.getInstance();
	return new Integer(c.get(Calendar.HOUR_OF_DAY)*100+c.get(Calendar.MINUTE));
    }
}
