package com.fahimshahrierrasel.collectionnotifier.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.fahimshahrierrasel.collectionnotifier.CollectionNotifier;
import com.fahimshahrierrasel.collectionnotifier.R;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CreateBinFragment extends Fragment {
    /** Android Views **/
    EditText editTextName;
    EditText editTextTrigPin;
    EditText editTextEchoPin;
    EditText editTextNotifyLevel;
    Button buttonViewAll;
    Button buttonAddSensor;
    /** Android Views **/

    private Socket mSocket;
    private String TAG = getClass().getSimpleName();

    public CreateBinFragment() {

    }

    public static CreateBinFragment newInstance() {
        return new CreateBinFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_create_bin, container, false);

        CollectionNotifier app = (CollectionNotifier) getActivity().getApplication();
        mSocket = app.getSocket();

        mSocket.connect();

        bindViews(root);

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
            getActivity().runOnUiThread(() -> {
                JSONObject obj = (JSONObject) args[0];

            });
        });

        buttonViewAll.setOnClickListener(view -> {
            mSocket.emit("get_all", new JSONObject());
        });

        mSocket.on("take_all", args -> {
            getActivity().runOnUiThread(() -> {
                String obj = (String) args[0];

            });
        });


        return root;
    }

    private void bindViews(View rootView){
        editTextName = rootView.findViewById(R.id.editText_name);
        editTextTrigPin = rootView.findViewById(R.id.editText_trig_pin);
        editTextEchoPin = rootView.findViewById(R.id.editText_echo_pin);
        editTextNotifyLevel = rootView.findViewById(R.id.editText_notify_level);
        buttonViewAll = rootView.findViewById(R.id.button_view_all);
        buttonAddSensor = rootView.findViewById(R.id.button_add_sensor);
    }
}
