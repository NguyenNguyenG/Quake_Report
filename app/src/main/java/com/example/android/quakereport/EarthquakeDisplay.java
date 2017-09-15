package com.example.android.quakereport;

/**
 * Created by nguyennguyen on 9/14/17.
 */

import android.app.LoaderManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;


public class EarthquakeDisplay extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Earthquake>>{

    public static final String LOG_TAG = EarthquakeDisplay.class.getName();
    private final int LoaderID = 0;
    private String url;
    private EarthquakeAdapter earthquakeAdapter;
    private TextView emptyView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_display_activity);

        Intent intent = getIntent();
        url = intent.getStringExtra(EarthquakeActivity.INTENT_DATA);

        Log.d("url", url);
        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);
        emptyView = (TextView) findViewById(R.id.emptyView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        // Create a new {@link ArrayAdapter} of earthquakes
        earthquakeAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(earthquakeAdapter);
        earthquakeListView.setEmptyView(emptyView);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(earthquakeAdapter.getItem(position).getUrl()));
                    startActivity(intent);
                }catch (ActivityNotFoundException e)
                {
                    Log.d(LOG_TAG, "Cannot Open the Url");
                    e.printStackTrace();
                }
            }
        });
        Context context = getApplicationContext();
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if(activeNetwork  != null && activeNetwork.isConnectedOrConnecting())
            getLoaderManager().initLoader(LoaderID, null, this);
        else {
            progressBar.setVisibility(View.GONE);
            emptyView.setText("No Internet Connection");
        }

    }


    @Override
    public Loader<ArrayList<Earthquake>> onCreateLoader(int id, Bundle args) {

        return new EarthquakeLoader(this,url);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Earthquake>> loader, ArrayList<Earthquake> data) {
        earthquakeAdapter.clear();
        Log.i("Loading", "onLoadFinished");
        progressBar.setVisibility(View.GONE);
        if(data != null && !data.isEmpty())
            earthquakeAdapter.addAll(data);
        emptyView.setText("No Earthquakes Found");
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Earthquake>> loader) {
        earthquakeAdapter.clear();
        emptyView.setText("");
    }
}
