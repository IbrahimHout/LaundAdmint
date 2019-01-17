package dev.ibrahhout.shinystoreadmin;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.ibrahhout.shinystoreadmin.Adapters.ItemsAdapter;
import dev.ibrahhout.shinystoreadmin.Models.Product;
import dev.ibrahhout.shinystoreadmin.Utils.Constants;

import static android.content.ContentValues.TAG;

public class ManageProductActivity extends AppCompatActivity {

    @BindView(R.id.titleConfirm)
    TextView titleConfirm;
    @BindView(R.id.categoriesRecyclerView)
    RecyclerView categoriesRecyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.noItemsTV)
    TextView noItemsTV;
    @BindView(R.id.addCatFAB)
    FloatingActionButton addCatFAB;
    @BindView(R.id.addCategory)
    Button addCategory;
    @BindView(R.id.addProducts)
    Button addProducts;

    ArrayList<Product> products;
    ItemsAdapter itemsAdapter;
    String catId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_categories);
        ButterKnife.bind(this);


        products = new ArrayList<>();
        itemsAdapter = new ItemsAdapter(this,products,catId);
        categoriesRecyclerView.setAdapter(itemsAdapter);
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));

        addCatFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManageProductActivity.this,AddProductActivity.class);
                intent.putExtra(Constants.EXTRA_CATEGORY_ID,catId);
                startActivity(intent);
            }
        });


        catId = getIntent().getStringExtra(Constants.EXTRA_CATEGORY_ID);

    }

    private void callItems(String catId) {

        FirebaseDatabase.getInstance().getReference().child(Constants.PRODUCTS).child(catId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        progressBar.setVisibility(View.VISIBLE);


                        products.clear();
                        for (DataSnapshot catSnap : dataSnapshot.getChildren()) {

                            Product cat = catSnap.getValue(Product.class);
                            cat.setId(catSnap.getKey());

                            Log.d(TAG, "onDataChange: ");

                            products.add(cat);
                        }


                        Collections.reverse(products);
                        updateAdapter(products);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(ManageProductActivity.this, "Error Loading Data check your connection please,", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (catId!=null)
        callItems(catId);


    }



    private void updateAdapter(ArrayList<Product> categories) {
        if(categories.size()==0){
            progressBar.setVisibility(View.GONE);
            noItemsTV.setVisibility(View.VISIBLE);
        }else {
            progressBar.setVisibility(View.GONE);
            noItemsTV.setVisibility(View.GONE);


            itemsAdapter.notifyDataSetChanged();
        }
    }
}
