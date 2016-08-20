package vinformax.vinmart.recycleviewadapter;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import vinformax.vinmart.R;
import vinformax.vinmart.model.AssignVariable;


/**
 * Created by Mobtech-03 on 6/30/2016.
 */
public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewAdapter> {

    private Context mContext;
    private List<AssignVariable> albumList;
    @Override
    public MyViewAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card, parent, false);

        return new MyViewAdapter(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewAdapter holder, int position) {
        AssignVariable assignVariable = albumList.get(position);
        holder.title.setText(assignVariable.getProductModelName());
        holder.count.setText(assignVariable.getProductModelActualPrice() + " songs");

        // loading album cover using Glide library
        Glide.with(mContext).load(assignVariable.getProductImage()).into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });
    }
    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewAdapter extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, overflow;

        public MyViewAdapter(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }


    private class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }
    public AlbumsAdapter(Context mContext, List<AssignVariable> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }
}
