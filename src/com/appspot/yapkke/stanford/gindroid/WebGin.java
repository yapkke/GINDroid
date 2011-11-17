package com.appspot.yapkke.stanford.gindroid;

import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.*;

import java.io.IOException;

import android.util.Log;

/** Class that interacts with gin.stanford.edu
 *
 * Done through HTTP client
 *
 * @author ykk
 * @date Nov 2011
 */
public class WebGin
{
    /** Name of class
     */
    private final String name = "WebGin";

    /** Reference to HTTP client
     */
    HttpClient httpClient;
    
    /** Constructor
     */
    public WebGin()
    {
	if (httpClient == null)
	{
	    httpClient = new DefaultHttpClient();
	    Log.d(name, "New HTTPClient");
	}
    }

    /** Authentication
     */
    public void auth()
    {
	HttpGet request = new HttpGet("https://gin.stanford.edu");
	ResponseHandler<String> responseHandler = new BasicResponseHandler();
	try
	{
	    String response = httpClient.execute(request, responseHandler);
	    
	    Log.d(name, response);
	} catch (IOException e)
	{
	    Log.d(name, "Error:"+e.toString());
	}
    }
}