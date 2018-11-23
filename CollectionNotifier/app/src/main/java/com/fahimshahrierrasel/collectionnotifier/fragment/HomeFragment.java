package com.fahimshahrierrasel.collectionnotifier.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fahimshahrierrasel.collectionnotifier.R;
import com.fahimshahrierrasel.collectionnotifier.adapter.BinAdapter;
import com.fahimshahrierrasel.collectionnotifier.model.Bin;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    RecyclerView recyclerViewBins;
    FloatingActionButton fabCreateBin;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        bindViews(root);

        List<Bin> bins = new ArrayList<>();
        bins.add(new Bin("Bin One", "Cleaned 1 Times", "Status: Active"));
        bins.add(new Bin("Bin Two", "Cleaned 2 Times", "Status: Active"));
        bins.add(new Bin("Bin Three", "Cleaned 3 Times", "Status: Inactive"));

        BinAdapter binAdapter = new BinAdapter(bins, this::onItemClick);
        recyclerViewBins.setAdapter(binAdapter);
        recyclerViewBins.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));

        fabCreateBin.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_placeholder, CreateBinFragment.newInstance())
                    .addToBackStack(null)
                    .commit();
        });

        return root;
    }

    public void onItemClick(Bin bin) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_placeholder, BinDetailsFragment.newInstance())
                .addToBackStack(null)
                .commit();
    }

    private void bindViews(View rootView) {
        recyclerViewBins = rootView.findViewById(R.id.recycler_view_bins);
        fabCreateBin = rootView.findViewById(R.id.fab_create_bin);
    }
}
