package vinformax.vinmart.recycleviewadapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.squareup.picasso.Picasso;

import java.util.List;

import vinformax.vinmart.model.AssignVariable;
import vinformax.vinmart.service.CustomVolleyRequest;
import vinformax.vinmart.common.MainActivity;
import vinformax.vinmart.product.ProductScreen;
import vinformax.vinmart.R;
import vinformax.vinmart.service.Allurls;

/**
 * Created by Mobtech-03 on 7/5/2016.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.Holder> {


    List<AssignVariable> assignVariables;
    ImageLoader imageLoader;
    Context homeAppContext;
    Context context;

    String productImageUrl="";
    public CategoryAdapter(List<AssignVariable> mAssignVariableList4) {
    }

    @Override
    public CategoryAdapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_tab3, parent, false);
        Holder h = new Holder(v);
        return h;
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.Holder holder, int position) {

        final AssignVariable assignVariable =new AssignVariable();
        homeAppContext = MainActivity.conApplication;
        imageLoader = CustomVolleyRequest.getInstance(homeAppContext).getImageLoader();

        holder.cv.setTag(position);
        holder.Title.setText(assignVariables.get(position).productModelName);
        holder.gridname.setText(String.valueOf(assignVariables.get(position).productModelActualPrice));
        // holder.Imagename.setText(String.valueOf(assignVariables.get(position).age));
        productImageUrl = Allurls.url_allimagedirectory+ assignVariables.get(position).getProductImage();
        //holder.productImage.sse(assignVariables.get(position).getProductImage());
        //holder.productImage.setImageUrl(productImageUrl, imageLoader);
        Picasso.with(homeAppContext).load( assignVariables.get(position).getProductImage())
                .error(R.drawable.shapp360)
                .placeholder(R.drawable.shapp360)
                .into(holder.gridimage);
        Log.d("!!!!", String.valueOf(Allurls.url_allimagedirectory + assignVariables.get(position).getProductImage()));
        Log.d("IMAGES",toString());

    }

    @Override
    public int getItemCount() {
        if (assignVariables != null) {
            return assignVariables.size();
        }
        return 0;
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{

        CardView cv;
        TextView Title;
        TextView gridname;
        public ImageView gridimage;
        public Holder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.card_view);
            Title = (TextView) itemView.findViewById(R.id.title);
            gridname = (TextView) itemView.findViewById(R.id.grdname);
            gridimage=(ImageView) itemView.findViewById(R.id.grdimage);
            //Imagename=(TextView) itemView.findViewById(R.id.txtSurname);

            cv.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int position = (int) view.getTag();
            Toast.makeText(view.getContext(),Integer.toString(position), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(view.getContext(), ProductScreen.class);
            Bundle bundle = new Bundle();
            assignVariables.clear();

            bundle.putString("vPName", this.Title.getText().toString());
            // bundle.putString("vPDiscountPrice", this.vPDiscoutnPrice.getText().toString());
            // bundle.putString("vpActualPrice", this.vPActualPrice.getText().toString());
            bundle.putString("vProductImage", productImageUrl);
            // bundle.putString("vPDiscountPercentage", this.vPDiscountPercentage.getText().toString());
            Log.d("first", productImageUrl);
            intent.putExtras(bundle);
            view.getContext().startActivity(intent);

        }
    }
}
