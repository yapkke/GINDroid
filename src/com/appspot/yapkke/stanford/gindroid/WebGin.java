package com.appspot.yapkke.stanford.gindroid;

import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.*;

import java.io.IOException;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

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
	//Get form for authentication
	HttpGet request = new HttpGet("https://gin.stanford.edu");
	ResponseHandler<String> responseHandler = new BasicResponseHandler();
	String response;
	try
	{
	    response = httpClient.execute(request, responseHandler);   
	} catch (IOException e)
	{
	    response = "";
	    Log.d(name, "Error:"+e.toString());
	}

	//Parse for form
	Document doc = Jsoup.parse(response);
	Log.d(name, doc.body().getElementsByTag("form").html());
	
    }
}