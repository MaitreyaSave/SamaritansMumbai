package in.apps.maitreya.samaritansmumbai.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import in.apps.maitreya.samaritansmumbai.R;
import in.apps.maitreya.samaritansmumbai.classes.Call_Log;

public class CallerProfileUpdatesAdapter extends RecyclerView.Adapter<CallerProfileUpdatesAdapter.ViewHolder>{
    private List<Call_Log> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        View mView;
        ViewHolder(View v) {
            super(v);
            mView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CallerProfileUpdatesAdapter(List<Call_Log> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public CallerProfileUpdatesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                               int viewType) {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.caller_profile_updates_row, parent, false);
        // set the view's size, margins, paddings and layout parameters

        return new CallerProfileUpdatesAdapter.ViewHolder(v);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull final CallerProfileUpdatesAdapter.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.mView.setText(mDataset[position]);
        //
        //CardView cardView = holder.mView.findViewById(R.id.card_view_caller_profile_updates);

        TextView caller_profile_updates_ref_no = holder.mView.findViewById(R.id.card_view_caller_profile_ref);
        TextView caller_profile_updates_date= holder.mView.findViewById(R.id.card_view_caller_profile_date);
        TextView caller_profile_updates_details= holder.mView.findViewById(R.id.card_view_caller_profile_details_value);
        TextView caller_profile_updates_updates= holder.mView.findViewById(R.id.card_view_caller_profile_updates_value);

        String ref_no = "Ref No: "+mDataset.get(position).getRef_no();
        caller_profile_updates_ref_no.setText(ref_no);
        caller_profile_updates_date.setText(mDataset.get(position).getDate());

        caller_profile_updates_details.setText(mDataset.get(position).getNew_info());
        caller_profile_updates_updates.setText(mDataset.get(position).getUpdates());
        //


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if(mDataset!=null)
            return mDataset.size();
        else
            return 0;
    }
}
