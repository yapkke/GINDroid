package com.appspot.yapkke.stanford.gindroid;

import android.app.*;
import android.view.*;
import android.view.View.*;
import android.content.*;
import android.widget.*;
import android.os.Bundle;

/** Settings activity
 *
 * @author ykk
 * @date Nov 2011
 */
public class Settings 
    extends Activity
    implements OnClickListener
{
    /** Starting setting activity for recording/changing settings.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

	//Set listeners
	Button okButton = (Button) findViewById(R.id.setting_ok);
	okButton.setOnClickListener(this);	

	//Populate saved content if any

    }

    /** Deal with clicking of the OK button
     *
     * Save settings and return to main view
     */
    public void onClick(View v) 
    {
	//Save settings
	

	//Move back to main view
	Intent mainIntent = new Intent(this, GINDroid.class);
	startActivity(mainIntent);
    }
}
