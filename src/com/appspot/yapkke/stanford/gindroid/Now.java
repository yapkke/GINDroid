package com.appspot.yapkke.stanford.gindroid;

import android.app.*;
import android.os.Bundle;

import java.util.*;

/** Now activity
 *
 * @author ykk
 * @date Nov 2011
 */
public class Now
    extends GDActivity
{
    /** Starting now activity for showing the current room occupancy
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.now);

	WebGin.Listing listing = wg.briefListing();
	
    }
}
