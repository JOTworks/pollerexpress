package thePollerExpress.views.setup.SetupViewAdapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import android.view.ViewGroup;
import android.widget.TextView;
import com.shared.models.Player;

import java.util.List;

import thePollerExpress.models.ClientData;
import cs340.pollerexpress.R;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.MyViewHolder> {
    private List<Player> playerArray;


    // Provide a suitable constructor (depends on the kind of dataset)
    public PlayerAdapter(List<Player> myDataset)
    {
        playerArray = myDataset;
    }

    @Override
    public int getItemViewType(int position)
    {
        return position;
    }
    // Create new views (invoked by the layout manager)
    @Override
    public PlayerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType)
    {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.spinner_poller_theme, parent, false);
        //...
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        assert(playerArray == ClientData.getInstance().getGame().getPlayers());
        try
        {
            String str = playerArray.get(position).getName();
            holder.playerName.setText(str);
        }
        catch(NullPointerException e)
        {
            holder.playerName.setText("open");
        }
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        // each data item is just a string in this case
        public TextView playerName;

        public MyViewHolder(TextView v) {
            super(v);

            playerName = v.findViewById(R.id.poller_text_view);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return playerArray.size();
    }
}