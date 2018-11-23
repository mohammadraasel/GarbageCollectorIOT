package com.fahimshahrierrasel.collectionnotifier;

import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private Socket mSocket;

    RecyclerView recyclerViewBins;
    /** Android Views **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        List<Bin> bins = new ArrayList<>();
        bins.add(new Bin("Bin One", "Cleaned 1 Times", "Status: Active"));
        bins.add(new Bin("Bin Two", "Cleaned 2 Times", "Status: Active"));
        bins.add(new Bin("Bin Three", "Cleaned 3 Times", "Status: Inactive"));

        BinAdapter binAdapter = new BinAdapter(bins);
        recyclerViewBins.setAdapter(binAdapter);
        recyclerViewBins.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    }

    /** Android Views **/


    /**
     * Binds XML views
     * Call this function after setContentView() in onCreate().
     **/
    private void bindViews(){
        recyclerViewBins = (android.support.v7.widget.RecyclerView) findViewById(R.id.recycler_view_bins);
    }

}
