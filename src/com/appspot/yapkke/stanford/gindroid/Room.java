package com.appspot.yapkke.stanford.gindroid;

import android.app.*;
import android.os.Bundle;

/** Room activity
 *
 * @author ykk
 * @date Nov 2011
 */
public class Room
    extends Activity
{
    /** Starting activity for room today
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room);
    }
}
