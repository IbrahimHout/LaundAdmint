package dev.ibrahhout.shinystoreadmin.Adapters;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import dev.ibrahhout.shinystoreadmin.Models.Category;
import dev.ibrahhout.shinystoreadmin.Models.Product;
import dev.ibrahhout.shinystoreadmin.R;
import dev.ibrahhout.shinystoreadmin.ManageProductActivity;
import dev.ibrahhout.shinystoreadmin.Utils.Constants;

import static android.content.ContentValues.TAG;
import static dev.ibrahhout.shinystoreadmin.Utils.Constants.CATEGORIES;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.CategoryViewHolder> {


    private Context context;
    private ArrayList items;
    private String catId;


    public ItemsAdapter(Context context, ArrayList items) {
        this.context = context;
        this.items = items;
    }

    public ItemsAdapter(Context context, ArrayList items, String catId) {
        this.context = context;
        this.items = items;
        this.catId = catId;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.cell_category, parent, false);


        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Object item = items.get(position) instanceof Category ? (Category) items.get(position) : (Product) items.get(position);

        if (item instanceof Category) {


            // it is category


            final Object finalItem2 = item;
            holder.itemCard.setOnClickListener(view -> {

                Intent intent = new Intent(context,ManageProductActivity.class);
                String id = ((Category) finalItem2).getId();
                intent.putExtra(Constants.EXTRA_CATEGORY_ID,id);
                Log.d(TAG, "onClick: card id is "+id);
                context.startActivity(intent);
            });
            if (!((Category) item).getName().isEmpty())
            holder.itemName.setText(((Category) item).getName());

            if (!((Category) item).getDescription().isEmpty())
            holder.itemDescription.setText(((Category) item).getDescription());

            if ( ((Category) item).getImageLink()!=null &&!((Category) item).getImageLink().isEmpty() )
            {

                Picasso.get().load(((Category) item).getImageLink()).placeholder(R.drawable.ic_loading_24dp).error(R.drawable.ic_error_black_24dp).into(holder.itemImage);

                Log.d(TAG, "onBindViewHolder: "+ ((Category) item).getImageLink());




            }




            final Object finalItem = item;
            final Object finalItem1 = item;
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Are you sure you want to delete "+((Category) finalItem).getName()+" ?")
                            .setContentText("Won't be able to recover this file!")
                            .setConfirmText("Yes,delete it!")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    String key = ((Category) finalItem1).getId();
                                    FirebaseDatabase.getInstance().getReference().child(CATEGORIES).child(key).removeValue();
                                    items.remove(finalItem1);
                                    notifyDataSetChanged();
                                }
                            })
                            .setCancelText("No")
                            .show();
                }
            });





        } else {
            //it is a product
            // it is category
            if (!((Product) item).getName().isEmpty())
                holder.itemName.setText(((Product) item).getName());

            if (!((Product) item).getDescription().isEmpty())
                holder.itemDescription.setText(((Product) item).getDescription());

            if (!((Product) item).getPrice().isEmpty())
                holder.itemPrice.setText(((Product) item).getPrice());

            if (!((Product) item).getImageURL().isEmpty())
            {

                Picasso.get().load(((Product) item).getImageURL()).placeholder(R.drawable.ic_loading_24dp).error(R.drawable.ic_error_black_24dp).into(holder.itemImage);

            }

            final Object finalItem = item;
            final Object finalItem1 = item;
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Are you sure you want to delete "+((Product) finalItem).getName()+" ?")
                            .setContentText("Won't be able to recover this file!")
                            .setConfirmText("Yes,delete it!")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    String key = ((Product) finalItem1).getId();
                                    FirebaseDatabase.getInstance().getReference().child(Constants.PRODUCTS).child(catId).child(key).removeValue();
                                    items.remove(finalItem1);
                                    notifyDataSetChanged();
                                }
                            })
                            .setCancelText("No")
                            .show();
                }
            });





        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.itemName)
        TextView itemName;
        @BindView(R.id.itemPrice)
        TextView itemPrice;
        @BindView(R.id.itemDescription)
        TextView itemDescription;
        @BindView(R.id.delete)
        ImageButton delete;
        @BindView(R.id.itemCard)
        CardView itemCard;
        @BindView(R.id.itemImage)
        ImageView itemImage;


        public CategoryViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }


}
