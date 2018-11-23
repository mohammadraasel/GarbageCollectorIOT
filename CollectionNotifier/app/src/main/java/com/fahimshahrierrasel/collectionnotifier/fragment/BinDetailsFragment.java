package com.fahimshahrierrasel.collectionnotifier.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.fahimshahrierrasel.collectionnotifier.R;
import com.fahimshahrierrasel.collectionnotifier.adapter.OptionAdapter;
import com.fahimshahrierrasel.collectionnotifier.model.Option;

import java.util.ArrayList;
import java.util.List;

public class BinDetailsFragment extends Fragment {
    /**
     * Android Views
     **/
    ProgressBar progressBarBin;
    TextView textViewBinStatusText;
    TextView buttonEditBin;
    Switch switchBinStatus;
    TextView textViewSwitchBinStatusText;
    RecyclerView recyclerViewOptions;

    public BinDetailsFragment() {
    }

    public static BinDetailsFragment newInstance() {
        return new BinDetailsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_bin_details, container, false);
        bindViews(root);

        List<Option> options = new ArrayList<>();
        options.add(new Option(R.drawable.ic_location, "Location", "Middle Badda"));
        options.add(new Option(R.drawable.ic_access_time, "Total Cleaned", "10 Times"));
        options.add(new Option(R.drawable.ic_trigger, "Trigger Pin", "5"));
        options.add(new Option(R.drawable.ic_location, "Echo Pin", "8"));
        options.add(new Option(R.drawable.ic_level, "Notification Level", "88%"));
        options.add(new Option(R.drawable.ic_status, "Status", "Active"));


        OptionAdapter optionAdapter = new OptionAdapter(options);
        recyclerViewOptions.setAdapter(optionAdapter);
        recyclerViewOptions.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));

        switchBinStatus.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked)
                textViewSwitchBinStatusText.setText("Active");
            else
                textViewSwitchBinStatusText.setText("Inactive");
        });

        return root;
    }

    private void bindViews(View rootView) {
        progressBarBin = rootView.findViewById(R.id.progress_bar_bin);
        textViewBinStatusText = rootView.findViewById(R.id.text_view_bin_status_text);
        buttonEditBin = rootView.findViewById(R.id.button_edit_bin);
        switchBinStatus = rootView.findViewById(R.id.switch_bin_status);
        textViewSwitchBinStatusText = rootView.findViewById(R.id.text_view_switch_bin_status_text);
        recyclerViewOptions = rootView.findViewById(R.id.recycler_view_options);
    }
}
