package vinformax.vinmart.recycleviewadapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import vinformax.vinmart.R;


public class FeedListRowHolder extends RecyclerView.ViewHolder {
    public ImageView thumbnail;
    public TextView title;
    public TextView id;
    public TextView description;
    public TextView price;
    public View cardView;

    /*public FeedListRowHolder(LayoutInflater inflater, ViewGroup parent) {
        super(inflater.inflate(R.layout.item_list, parent, false));
        thumbnail = (ImageView) itemView.findViewById(R.id.list_avatar);
        title = (TextView) itemView.findViewById(R.id.list_title);
        id = (TextView) itemView.findViewById(R.id.list_desc);
       *//* itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_POSITION, getAdapterPosition());
                context.startActivity(intent);
            }
        });*//*
    }*/
    public FeedListRowHolder(View view) {
        super(view);
        //this.description=(TextView)view.findViewById(R.id.list_desc);
        //this.price=(TextView)view.findViewById(R.id.price);
        this.thumbnail = (ImageView) view.findViewById(R.id.tile_picture);
        this.title = (TextView) view.findViewById(R.id.tile_title);
       // this.id=(TextView)view.findViewById(R.id.list_desc);
        //this.cardView=(CardView)view.findViewById(R.id.card_view);
    }

}