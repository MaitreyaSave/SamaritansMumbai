package in.apps.maitreya.samaritansmumbai.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import in.apps.maitreya.samaritansmumbai.R;
import in.apps.maitreya.samaritansmumbai.activities.ReceiveNotificationActivity;
import in.apps.maitreya.samaritansmumbai.activities.ViewSingleCallerProfileActivity;
import in.apps.maitreya.samaritansmumbai.activities.ViewSingleLogActivity;
import in.apps.maitreya.samaritansmumbai.classes.CallerProfile;
import in.apps.maitreya.samaritansmumbai.classes.NotificationMessage;

/**
 * Created by Maitreya on 2/18/2018.
 *
 */

public class CallerProfilesAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder>{
    private List<CallerProfile> mDataset;
    private Context ctx;

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
    public CallerProfilesAdapter(List<CallerProfile> myDataset, Context context) {
        mDataset = myDataset;
        ctx = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public NotificationsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.caller_profile_list_row, parent, false);
        // set the view's size, margins, paddings and layout parameters

        return new NotificationsAdapter.ViewHolder(v);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final NotificationsAdapter.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.mView.setText(mDataset[position]);
        //
        CardView cardView = holder.mView.findViewById(R.id.card_view_caller_profile);
        TextView caller_profile_name;
        caller_profile_name = holder.mView.findViewById(R.id.card_view_caller_profile_name);

        caller_profile_name.setText(mDataset.get(position).getName());
        //
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx,ViewSingleCallerProfileActivity.class);
                intent.putExtra("CallerProfile", mDataset.get(holder.getAdapterPosition()));
                ctx.startActivity(intent);
            }
        });


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}