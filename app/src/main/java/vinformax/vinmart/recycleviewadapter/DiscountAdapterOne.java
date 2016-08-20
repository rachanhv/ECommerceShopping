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

import vinformax.vinmart.R;
import vinformax.vinmart.common.MainActivity;
import vinformax.vinmart.model.AssignVariable;
import vinformax.vinmart.product.ProductScreen;
import vinformax.vinmart.service.CustomVolleyRequest;

/**
 * Created by Rachan on 6/27/2016.
 *
 * This is an class to for Recycler Adapter, It inflates an Xml called recycler_view_adapter.xml
 * Use of this xml we can display the homescreen data.
 */

public class DiscountAdapterOne extends RecyclerView.Adapter<DiscountAdapterOne.PersonViewHolder> {

    List<AssignVariable> assignVariables;
    ImageLoader imageLoader;
    Context homeAppContext;
    Context context;

    String productImageUrl="";

    public DiscountAdapterOne(Context context) {
        this.context = context;
    }

 public DiscountAdapterOne(List<AssignVariable> assignVariables){
        this.assignVariables = assignVariables;
    }
    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_adapter, parent, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int i) {

        homeAppContext = MainActivity.conApplication;
        imageLoader = CustomVolleyRequest.getInstance(homeAppContext).getImageLoader();
        holder.setIsRecyclable(false);
        holder.cv.setTag(i);

        holder._productImage.setImageDrawable(null);
        holder._productName.setText(assignVariables.get(i).getProductModelName());
        holder._productPrice.setText(String.valueOf(assignVariables.get(i).getProductModelActualPrice()));
        holder._productDiscoutnPrice.setText(String.valueOf(assignVariables.get(i).getProductModelDiscountPrice()));
        holder._productImageName.setText(String.valueOf(assignVariables.get(i).getProductImage()));
        productImageUrl =assignVariables.get(i).getProductImage();

        Picasso.with(homeAppContext).load( assignVariables.get(i).getProductImage())
                .error(R.drawable.shapp360)
                .placeholder(R.drawable.shapp360)
                .into(holder._productImage);
        Log.d("!!!!", String.valueOf(assignVariables.get(i).getProductImage()));
        Log.d("IMAGES",toString());
    }

    @Override
    public int getItemCount() {
        if (assignVariables != null) {
            return assignVariables.size();
        }
        return 0;
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public class PersonViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        CardView cv;
        TextView _productName;
        TextView _productPrice;
        TextView _productDiscoutnPrice;
        TextView _productId;
        ImageView _productImage;
        TextView _productImageName;

       public PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.card_view);
            _productName = (TextView) itemView.findViewById(R.id.productTitle);
            _productPrice = (TextView) itemView.findViewById(R.id.productPrice);
           _productDiscoutnPrice = (TextView) itemView.findViewById(R.id.dealProductDiscountPrice);
            _productImageName = (TextView) itemView.findViewById(R.id.dealProductImageName);
            _productImage=(ImageView) itemView.findViewById(R.id.productImage);
            cv.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // here you use position
            int position = (int) view.getTag();
            Toast.makeText(view.getContext(),Integer.toString(position), Toast.LENGTH_SHORT).show();
            try {
                Intent intent = new Intent(view.getContext(), ProductScreen.class);
                Bundle bundle = new Bundle();
               // assignVariables.clear();

                bundle.putString("vPName", _productName.getText().toString());
               bundle.putString("vPDiscountPrice",_productDiscoutnPrice.getText().toString());
                bundle.putString("vpActualPrice", _productPrice.getText().toString());
                bundle.putString("vProductImage", _productImageName.getText().toString());
                Log.d("first", productImageUrl);
                //System.out.println(_productName.getText().toString());
                //System.out.println(_productId);

                //System.out.println(_productDiscoutnPrice);
                //System.out.println(_productPrice);
               // Log.d("first", productImageUrl);
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);
            }catch (Exception e){
                e.printStackTrace();
                e.getMessage();
            }


            // notifyDataSetChanged();

            //assignVariables.clear();
        }
    }


}
