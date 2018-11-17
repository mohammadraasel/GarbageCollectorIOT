package com.fahimshahrierrasel.collectionnotifier;

import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private Socket mSocket;

    /** Android Views **/
    EditText editTextName;
    EditText editTextTrigPin;
    EditText editTextEchoPin;
    EditText editTextNotifyLevel;
    Button buttonViewAll;
    Button buttonAddSensor;
    TextView textViewResult;
    /** Android Views **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CollectionNotifier app = (CollectionNotifier) getApplication();
        mSocket = app.getSocket();

        mSocket.connect();

        bindViews();

        buttonAddSensor.setOnClickListener(view -> {

            Map<String, String> data = new HashMap<>();
            data.put("name", editTextName.getText().toString());
            data.put("trig_pin", editTextTrigPin.getText().toString());
            data.put("echo_pin", editTextEchoPin.getText().toString());
            data.put("notify_level", editTextNotifyLevel.getText().toString());

            mSocket.emit("create_sensor", new JSONObject(data));
            Log.d(TAG, "Data Emitted "+ data.toString());
        });

        mSocket.on("sensor_created", args -> {
            runOnUiThread(() -> {
                JSONObject obj = (JSONObject) args[0];
                textViewResult.setText(obj.toString());
            });
        });

        buttonViewAll.setOnClickListener(view -> {
            mSocket.emit("get_all", new JSONObject());
        });

        mSocket.on("take_all", args -> {
            runOnUiThread(() -> {
                String obj = (String) args[0];
                textViewResult.setText(obj);
            });
        });
    }

    private void bindViews(){
        editTextName =  findViewById(R.id.editText_name);
        editTextTrigPin =  findViewById(R.id.editText_trig_pin);
        editTextEchoPin =  findViewById(R.id.editText_echo_pin);
        editTextNotifyLevel =  findViewById(R.id.editText_notify_level);

        buttonViewAll =  findViewById(R.id.button_view_all);
        buttonAddSensor =  findViewById(R.id.button_add_sensor);

        textViewResult = findViewById(R.id.textView_result);
    }
}
