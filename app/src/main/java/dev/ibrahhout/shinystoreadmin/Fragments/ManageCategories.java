package dev.ibrahhout.shinystoreadmin.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dev.ibrahhout.shinystoreadmin.Adapters.ItemsAdapter;
import dev.ibrahhout.shinystoreadmin.AddCategoryActivity;
import dev.ibrahhout.shinystoreadmin.Models.Category;
import dev.ibrahhout.shinystoreadmin.R;
import dev.ibrahhout.shinystoreadmin.Utils.Constants;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class ManageCategories extends Fragment {


    @BindView(R.id.titleConfirm)
    TextView titleConfirm;
    @BindView(R.id.categoriesRecyclerView)
    RecyclerView categoriesRecyclerView;
    @BindView(R.id.addCatFAB)
    FloatingActionButton addCatFAB;
    @BindView(R.id.addCategory)
    Button addCategory;
    @BindView(R.id.addProducts)
    Button addProducts;
    Unbinder unbinder;


    ArrayList<Category> categories;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.noItemsTV)
    TextView noItemsTV;

    ItemsAdapter itemsAdapter ;
    public ManageCategories() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        unbinder = ButterKnife.bind(this, view);

        categories = new ArrayList<>();
        itemsAdapter = new ItemsAdapter(getContext(),categories);
        categoriesRecyclerView.setAdapter(itemsAdapter);
        categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        addCatFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),AddCategoryActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();


        FirebaseDatabase.getInstance().getReference().child(Constants.CATEGORIES)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        progressBar.setVisibility(View.VISIBLE);


                        categories.clear();
                        for (DataSnapshot catSnap : dataSnapshot.getChildren()) {

                            Category cat = catSnap.getValue(Category.class);
                            cat.setId(catSnap.getKey());

                            Log.d(TAG, "onDataChange: ");

                            categories.add(cat);
                        }


                        Collections.reverse(categories);
                        updateAdapter(categories);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Error Loading Data check your connection please,", Toast.LENGTH_SHORT).show();

                    }
                });


    }

    private void updateAdapter(ArrayList<Category> categories) {
        if(categories.size()==0){
            progressBar.setVisibility(View.GONE);
            noItemsTV.setVisibility(View.VISIBLE);
        }else {
            progressBar.setVisibility(View.GONE);
            noItemsTV.setVisibility(View.GONE);


            itemsAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
