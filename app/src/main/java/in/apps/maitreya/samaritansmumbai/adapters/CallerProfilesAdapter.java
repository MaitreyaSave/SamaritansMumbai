package in.apps.maitreya.samaritansmumbai.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import in.apps.maitreya.samaritansmumbai.R;
import in.apps.maitreya.samaritansmumbai.activities.UpdateCallerProfile;
import in.apps.maitreya.samaritansmumbai.activities.ViewSingleCallerProfileActivity;
import in.apps.maitreya.samaritansmumbai.classes.CallerProfile;

/**
 * Created by Maitreya on 2/18/2018.
 *
 */

public class CallerProfilesAdapter extends RecyclerView.Adapter<CallerProfilesAdapter.ViewHolder>{
    private List<CallerProfile> mDataset;

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
    public CallerProfilesAdapter(List<CallerProfile> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public CallerProfilesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                               int viewType) {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.caller_profile_list_row, parent, false);
        // set the view's size, margins, paddings and layout parameters

        return new CallerProfilesAdapter.ViewHolder(v);
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull final CallerProfilesAdapter.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.mView.setText(mDataset[position]);
        //
        final Context ctx=holder.mView.getContext();
        CardView cardView = holder.mView.findViewById(R.id.card_view_caller_profile);
        TextView caller_profile_name;
        caller_profile_name = holder.mView.findViewById(R.id.card_view_caller_profile_name);

        caller_profile_name.setText(mDataset.get(position).getName());
        //
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                SharedPreferences pref = ctx.getSharedPreferences("MyPref", 0); // 0 - for private mode
                boolean update_CP = pref.getBoolean("update_CP",false);
                //
                Intent intent;
                if(update_CP){
                    //
                    SharedPreferences.Editor editor= pref.edit();
                    editor.putBoolean("update_CP",false);
                    editor.apply();
                    //
                    intent = new Intent(ctx, UpdateCallerProfile.class);
                    ((Activity)ctx).finish();
                }
                else {
                    intent = new Intent(ctx, ViewSingleCallerProfileActivity.class);
                }
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