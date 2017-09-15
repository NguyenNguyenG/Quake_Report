package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by nguyennguyen on 9/12/17.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    public EarthquakeAdapter(Context context, ArrayList<Earthquake> earthquakesList)
    {

        //need context to inflate the list_item xml later
        super(context, 0, earthquakesList);

    }

    private int getMagColor(double mag)
    {
        int magColorResourceId;
        int magnitude = (int) Math.floor(mag);
        switch (magnitude)
        {
            case 0:
            case 1:
                magColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magColorResourceId = R.color.magnitude9;
                break;
            default:
                magColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magColorResourceId);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        View listViewItem = convertView;

        //create view if there is no view yet
        if(listViewItem == null)
        {
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        //find all the corresponding textfields in the list_item xml
        Earthquake currEarthquake = getItem(position);

        TextView mag = (TextView) listViewItem.findViewById(R.id.magnitude);
        TextView loc = (TextView) listViewItem.findViewById(R.id.location);
        TextView prox = (TextView) listViewItem.findViewById(R.id.proximity);
        TextView date = (TextView) listViewItem.findViewById(R.id.date);
        TextView time = (TextView) listViewItem.findViewById(R.id.time);

        //setting the value for the magnitude and location
        DecimalFormat formatter = new DecimalFormat("0.0");

        mag.setText(formatter.format(currEarthquake.getMagnitude()));

        //creating dateObject to use it with the 2 formats
        Date dateObject = new Date(currEarthquake.getDate());
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM DD, yyyy");

        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");

        //set TextView with the correct value with correct format
        date.setText(dateFormat.format(dateObject));
        time.setText(timeFormat.format(dateObject));

        String origLoc = currEarthquake.getLocation();
        int index = origLoc.indexOf("of");
        if( index != -1)
        {
            prox.setText(origLoc.substring(0,index + 2).trim());
            loc.setText(origLoc.substring(index + 2).trim());
        }
        else
        {
            prox.setText("Near the");
            loc.setText(origLoc.trim());
        }

        GradientDrawable gradientDrawable = (GradientDrawable) mag.getBackground();
        int color = getMagColor(currEarthquake.getMagnitude());

        gradientDrawable.setColor(color);


        return listViewItem;
    }


}
