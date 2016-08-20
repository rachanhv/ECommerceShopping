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
 * Created by Mobtech-03 on 7/1/2016.
 */
public class HotDealsAdapter extends RecyclerView.Adapter<HotDealsAdapter.MyViewHolder>{
    List<AssignVariable> assignVariables;
    ImageLoader imageLoader;
    Context homeAppContext;
    Context context;

    String productImageUrl="";
    public HotDealsAdapter(Context context) {
        this.context = context;
    }
    public HotDealsAdapter(List<AssignVariable> assignVariables){
        this.assignVariables = assignVariables;
    }
    public void addArticleFirst(List<AssignVariable> list) {
        assignVariables.addAll(0, list);
        notifyDataSetChanged();
    }

    // Add at the end of the list.

    public void addArticleLast(List<AssignVariable> list) {
        assignVariables.addAll(assignVariables.size(), list);
        notifyDataSetChanged();

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_tab3, parent, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(HotDealsAdapter.MyViewHolder holder, int i) {
        //final AssignVariable assignVariable =new AssignVariable();

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
        //holder.productImage.sse(assignVariables.get(position).getProductImage());
        //holder.productImage.setImageUrl(productImageUrl, imageLoader);
        holder.setIsRecyclable(false);
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
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cv;
        TextView _productName;
        TextView _productPrice;
        TextView _productDiscoutnPrice;
        TextView _productId;
        TextView _productImageName;
        ImageView _productImage;


        public MyViewHolder(View itemView) {
            super(itemView);

            cv = (CardView) itemView.findViewById(R.id.card_view);
            _productName = (TextView) itemView.findViewById(R.id.dealProductName);
            _productPrice = (TextView) itemView.findViewById(R.id.dealProductPrice);
            _productDiscoutnPrice = (TextView) itemView.findViewById(R.id.dealProductDiscountPrice);
            _productId = (TextView) itemView.findViewById(R.id.dealProductId);
            _productImageName = (TextView) itemView.findViewById(R.id.dealProductImageName);
            _productImage=(ImageView) itemView.findViewById(R.id.dealProductImage);

            //Imagename=(TextView) itemView.findViewById(R.id.txtSurname);
          cv.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {


            int position = (int) view.getTag();
            Toast.makeText(view.getContext(),Integer.toString(position), Toast.LENGTH_SHORT).show();

            try{
                Intent intent = new Intent(view.getContext(), ProductScreen.class);
                Bundle bundle = new Bundle();

                bundle.putString("vPName", _productName.getText().toString());
                bundle.putString("vPDiscountPrice", _productDiscoutnPrice.getText().toString());
                bundle.putString("vpActualPrice", _productPrice.getText().toString());
                bundle.putString("vProductImage", _productImageName.getText().toString());
                Log.d("first", productImageUrl);
                intent.putExtras(bundle);
                view.getContext().startActivity(intent);


            }catch (Exception e){
                e.getMessage();
                e.printStackTrace();
            }

        }


    }
}
