package com.appspot.yapkke.stanford.gindroid;

import android.app.*;
import android.view.*;
import android.view.View.*;
import android.content.*;
import android.widget.*;
import android.preference.*;
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
    /** Reference to EditText with username
     */
    EditText userEditText;
    /** Reference to EditText with password
     */
    EditText passwordEditText;

    /** Starting setting activity for recording/changing settings.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

	//Get reference to edit text
	userEditText = (EditText) findViewById(R.id.usernameentry);
	passwordEditText = (EditText) findViewById(R.id.password_entry);

	//Set listeners
	Button okButton = (Button) findViewById(R.id.setting_ok);
	okButton.setOnClickListener(this);	

	//Populate saved content if any
	SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
	userEditText.setText(settings.getString("username", ""));
	passwordEditText.setText(settings.getString("password", ""));	
    }

    /** Deal with clicking of the OK button
     *
     * Save settings and return to main view
     */
    public void onClick(View v) 
    {
	//Save settings
	SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
	SharedPreferences.Editor editor = settings.edit();
	editor.putString("username", userEditText.getText().toString());
	editor.putString("password", passwordEditText.getText().toString());
	editor.commit();

	Toast.makeText(this, "Settings saved", Toast.LENGTH_SHORT).show();

	//Move back to main view
	Intent mainIntent = new Intent(this, GINDroid.class);
	startActivity(mainIntent);
    }
}
