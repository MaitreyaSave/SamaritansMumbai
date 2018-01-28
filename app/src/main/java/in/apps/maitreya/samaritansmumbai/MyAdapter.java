package in.apps.maitreya.samaritansmumbai;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Maitreya on 1/28/2018.
 *
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<LogEntry> mDataset;
    private Context ctx;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View mView;
        public ViewHolder(View v) {
            super(v);
            mView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<LogEntry> myDataset, Context context) {
        mDataset = myDataset;
        ctx = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.log_list_row, parent, false);
        // set the view's size, margins, paddings and layout parameters

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.mView.setText(mDataset[position]);
        TextView cardView_sams_name,cardView_srno,cardView_date,cardView_risk_level,cardView_caller_name,cardView_refer_PU,cardView_new_old,cardView_gender,cardView_suicide_qn,cardView_gist;
        //
        CardView cardView = (CardView) holder.mView.findViewById(R.id.card_view);
        cardView_sams_name = (TextView) holder.mView.findViewById(R.id.cardview_sams_name_tv);
        cardView_srno = (TextView) holder.mView.findViewById(R.id.cardview_sams_sr_no_val);
        cardView_date = (TextView) holder.mView.findViewById(R.id.cardview_sams_date_val);
        cardView_risk_level = (TextView) holder.mView.findViewById(R.id.cardview_risk_level_val);
        cardView_caller_name = (TextView) holder.mView.findViewById(R.id.cardview_caller_name_val);
        cardView_refer_PU = (TextView) holder.mView.findViewById(R.id.cardview_refer_PU_val);
        cardView_new_old = (TextView) holder.mView.findViewById(R.id.cardview_new_old_val);
        cardView_gender = (TextView) holder.mView.findViewById(R.id.cardview_gender_val);
        cardView_suicide_qn = (TextView) holder.mView.findViewById(R.id.cardview_suicide_qn_val);
        cardView_gist = (TextView) holder.mView.findViewById(R.id.cardview_gist_val);
        //
        cardView_sams_name.setText(mDataset.get(position).getSams_name());
        String temp =""+mDataset.get(position).getSr_no();
        cardView_srno.setText(temp);
        cardView_date.setText(mDataset.get(position).getDate());
        temp = "Risk Level: "+mDataset.get(position).getRisk_level();
        cardView_risk_level.setText(temp);
        temp = "Suicide Question: "+mDataset.get(position).getSuicide_qn();
        cardView_suicide_qn.setText(temp);
        temp = "Referred to PU: "+mDataset.get(position).getPU_referred();
        cardView_refer_PU.setText(temp);
        temp = "Gist: "+mDataset.get(position).getGist();
        cardView_gist.setText(temp);
        temp = "Caller Name: "+mDataset.get(position).getName();
        cardView_caller_name.setText(temp);
        temp = "New/Old: "+mDataset.get(position).getNew_old();
        cardView_new_old.setText(temp);
        temp = "Gender: "+mDataset.get(position).getGender();
        cardView_gender.setText(temp);
        //
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx,ViewSingleLogActivity.class);
                intent.putExtra("LogEntry", mDataset.get(holder.getAdapterPosition()));
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