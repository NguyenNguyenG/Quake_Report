package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nguyennguyen on 9/14/17.
 */

public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<Earthquake>> {
    private String mUrl;
    private static String LOG_TAG = EarthquakeLoader.class.getSimpleName();
    public EarthquakeLoader(Context context, String url)
    {
        super(context);
        mUrl = url;

    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }
    @Override
    public ArrayList<Earthquake> loadInBackground() {
        URL url = createURl(mUrl);
        if(url == null)
            return null;
        String jsonResponse = "";
        try {
             jsonResponse = makeHttpRequest(url);
        }catch (IOException e)
        {
            Log.e(LOG_TAG, "Error in getting a json response", e);
        }

        if(TextUtils.isEmpty(jsonResponse))
            return null;

        return QueryUtils.extractEarthquakes(jsonResponse);
    }

    private URL createURl(String url)
    {
        if(TextUtils.isEmpty(url))
            return null;
        URL resultUrl = null;
        try{
            resultUrl = new URL(url);
        }catch(MalformedURLException e)
        {
            Log.e(LOG_TAG, "Cannot create a URL with the string" + url, e);
        }

        return resultUrl;
    }

    private String readFromStream (InputStream inputStream) throws IOException
    {
        StringBuilder jsonResponse = new StringBuilder();
        if(inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                jsonResponse.append(line);
                line = reader.readLine();
            }
        }
        return jsonResponse.toString();

    }

    private String makeHttpRequest(URL url) throws IOException
    {
        String result = "";
        if(url == null)
            return result;

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode() == 200)
            {
                inputStream = urlConnection.getInputStream();
                result = readFromStream(inputStream);
            }
            else
            {
                Log.e(LOG_TAG, "Error in connecting with code" + urlConnection.getResponseCode());
            }
        }catch (IOException e) {
            Log.e(LOG_TAG, "Error from Input/Output", e);
        }finally
        {
            if(urlConnection != null)
            {
                urlConnection.disconnect();
            }
            if(inputStream != null)
            {
                inputStream.close();
            }
        }
        return result;
    }
}
