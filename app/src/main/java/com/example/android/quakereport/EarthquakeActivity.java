/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class EarthquakeActivity extends AppCompatActivity {
    private String baseQueryURL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&";
    public static final String INTENT_DATA = EarthquakeActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        final EditText minMag = (EditText) findViewById(R.id.minMag);
        final EditText orderBy = (EditText) findViewById(R.id.orderBy);
        final EditText limit = (EditText) findViewById(R.id.limit);
        final EditText eventType = (EditText) findViewById(R.id.eventype);
        Button button = (Button) findViewById(R.id.submit);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String magnitude = minMag.getText().toString();
                String order = orderBy.getText().toString();
                String lim = limit.getText().toString();
                String event = eventType.getText().toString();

                StringBuilder builder = new StringBuilder();
                builder.append(baseQueryURL);
                if(!TextUtils.isEmpty(magnitude)) {
                    builder.append(getString(R.string.minmag)).append("=").append(magnitude);
                }
                if(!TextUtils.isEmpty(order)) {
                    builder.append("&").append(getString(R.string.orderby)).append("=").append(order);
                }
                if(!TextUtils.isEmpty(lim)) {
                    builder.append("&").append(getString(R.string.limit)).append("=").append(lim);
                }
                if(!TextUtils.isEmpty(event)) {
                    builder.append("&").append(getString(R.string.eventtype)).append("=").append(event);
                }

                String message = builder.toString();
                Intent intent = new Intent(EarthquakeActivity.this, EarthquakeDisplay.class);
                intent.putExtra(INTENT_DATA, message);
                startActivity(intent);
            }
        });

    }
}
