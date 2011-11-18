package com.appspot.yapkke.stanford.gindroid;

import org.apache.http.client.*;
import org.apache.http.client.methods.*;
import org.apache.http.client.entity.*;
import org.apache.http.impl.client.*;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.message.*;
import org.apache.http.*;

import java.io.*;
import java.util.*;

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
    public void auth(String username, String password)
    {
	//Get form for authentication
	HttpGet request = new HttpGet("https://gin.stanford.edu");
	String response = httpRequest(request);

	//Parse from form
	List<NameValuePair> nameValList = new ArrayList<NameValuePair>();
	Document doc = Jsoup.parse(response);
	Elements forms = doc.body().getElementsByTag("form");
	for (Element form : forms)
	{
	    Elements usernameE = form.getElementsByAttributeValue("name", "username");
	    Elements passwordE = form.getElementsByAttributeValue("name", "password");
	    if ((usernameE.size() == 1) && (passwordE.size() == 1))
	    {
		Elements inputs = form.getElementsByTag("input");
		for (Element input : inputs)
		{
		    String n = input.attr("name");
		    String v = input.attr("value");
		    if (n.compareTo("username") == 0)
			v = username;
		    if (n.compareTo("password") == 0)
			v = password;
		    nameValList.add(new BasicNameValuePair(n, v));
		    Log.d(name,n+"="+v);
		}
	    }
	}
	
	//Post for WebAuth
	HttpPost httpPost = new HttpPost("https://gin.stanford.edu/login");
	String postResponse;
	try
	{
	    httpPost.setEntity(new UrlEncodedFormEntity(nameValList));
	    postResponse = httpRequest(httpPost);
	} catch (UnsupportedEncodingException e)
	{
	    postResponse = "";
	    Log.d(name, "Error:"+e.toString());
	}
	Log.d(name, postResponse);
    }

    /** 
     */
    public String httpRequest(HttpUriRequest request)
    {
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

	return response;
    }
}