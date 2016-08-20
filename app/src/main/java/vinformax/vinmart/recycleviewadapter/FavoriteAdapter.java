package vinformax.vinmart.recycleviewadapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import vinformax.vinmart.R;
import vinformax.vinmart.database.VinMartSqlLite;
import vinformax.vinmart.model.ProductCart;
import vinformax.vinmart.product.Favorite;
import vinformax.vinmart.product.ProductScreen;

/**
 * Created by Mobtech-01 on 09-Aug-16.
 */
public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    Context homeContext;
    List<String> proNameList = new ArrayList<String>();
    List<String> proPriceList = new ArrayList<String>();
    List<String> prodiscPriceList = new ArrayList<String>();
    List<String> proTotal = new ArrayList<String>();
    List<String> proImageName = new ArrayList<String>();
    TextView empty;
    List<ProductCart> list = new ArrayList<ProductCart>();
    String[] _proName;
    String[] _proPrice;
    String[] _proDisPrice;
    String[] _proTotal;
    String[] _proImageName;
    String productname;

    Context context;
    LayoutInflater inflater;

    public FavoriteAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(this.context);
        call();
    }

    public void call() {

        VinMartSqlLite vinMartSqlLite = new VinMartSqlLite(context);
        list = vinMartSqlLite.getFavDetails();
        if(list.size()==0){

        }

        for (int i = 0; i < list.size(); i++) {
            Log.d("@@@@@", "Name" + list.get(i).getCartProductName() + "Account Name" + list.get(i).getCartActualPrice() + "Assign To" + list.get(i).getCartDiscountPrice());

            proNameList.add(list.get(i).getCartProductName());
            proPriceList.add(list.get(i).getCartActualPrice());

            _proName = proNameList.toArray(new String[proNameList.size()]);
            _proPrice = proPriceList.toArray(new String[proPriceList.size()]);
        }
    }

    private void layoutmethod() {

            empty.setVisibility(View.VISIBLE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.activity_cart_product_adapter, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
          productname=list.get(position).getCartProductName();
        holder.productNameCart.setText(list.get(position).getCartProductName());
        holder.productPriceCart.setText(list.get(position).getCartActualPrice());
        Picasso.with(context).load(list.get(position).getCartImage())
                .error(R.drawable.ic_launcher)
                .placeholder(R.drawable.ic_launcher)
                .into(holder.productCartId);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView productNameCart, productPriceCart,_productImageName;

        ImageView productCartId, removeProduct;
        FavoriteAdapter favoriteAdapter;
        int position;

        public ViewHolder(View itemView) {
            super(itemView);

            productNameCart = (TextView) itemView.findViewById(R.id.productNameCart);
            productPriceCart = (TextView) itemView.findViewById(R.id.productPriceCart);
            productCartId = (ImageView) itemView.findViewById(R.id.productCartId);
            removeProduct = (ImageView) itemView.findViewById(R.id.removeCartProduct);
            _productImageName = (TextView) itemView.findViewById(R.id.dealProductImageName);
            empty= (TextView) itemView.findViewById(R.id.nullmessage);


            productCartId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(v.getContext(), ProductScreen.class);
                    Bundle bundle = new Bundle();
                    list.clear();

                    bundle.putString("vPName", productNameCart.getText().toString());
                    bundle.putString("vpActualPrice", productPriceCart.getText().toString());

                    bundle.putString("vProductImage", String.valueOf(productCartId));
                    Log.d("first", String.valueOf(productCartId));
                    intent.putExtras(bundle);
                    v.getContext().startActivity(intent);
                    list.clear();
                }
            });

            removeProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteFav(productname);

                    Intent i = new Intent(v.getContext(), Favorite.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    v.getContext().startActivity(i);
                }
            });
        }

        private void deleteFav(String vProductName) {
            VinMartSqlLite vinMartSqlLite = new VinMartSqlLite(context);
            int result = vinMartSqlLite.deleteItem(vProductName);
            if (result == 1) {
                Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show();

               // vinMartSqlLite.deleteItem(vProductName);

            } else if (result == -1) {
                Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "try again", Toast.LENGTH_SHORT).show();
            }
        }
    }

}