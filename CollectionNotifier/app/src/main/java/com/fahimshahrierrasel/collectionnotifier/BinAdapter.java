package com.fahimshahrierrasel.collectionnotifier;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class BinAdapter extends RecyclerView.Adapter<BinAdapter.ViewHolder> {

    private List<Bin> bins;

    public BinAdapter(List<Bin> bins) {
        this.bins = bins;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_bin, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bind(bins.get(i));
    }

    @Override
    public int getItemCount() {
        return bins.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * Android Views
         **/
        ImageView imageViewBinLogo;
        TextView textViewBinName;
        TextView textViewBinCleanCount;
        TextView textViewBinStatus;

        /**
         * Android Views
         **/

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bindViews(itemView);
        }

        public void bind(Bin bin) {
            textViewBinName.setText(bin.getName());
            textViewBinCleanCount.setText(bin.getCount());
            textViewBinStatus.setText(bin.getStatus());
        }

        /**
         * Binds XML views
         * Call this function after layout is ready.
         **/
        private void bindViews(View rootView) {
            imageViewBinLogo = rootView.findViewById(R.id.image_view_bin_logo);
            textViewBinName = rootView.findViewById(R.id.text_view_bin_name);
            textViewBinCleanCount = rootView.findViewById(R.id.text_view_bin_clean_count);
            textViewBinStatus = rootView.findViewById(R.id.text_view_bin_status);
        }
    }
}
