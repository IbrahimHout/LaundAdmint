package dev.ibrahhout.shinystoreadmin.Fragments;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import dev.ibrahhout.shinystoreadmin.Adapters.ServicesHistoryAdapter;
import dev.ibrahhout.shinystoreadmin.Models.OrderModel;
import dev.ibrahhout.shinystoreadmin.R;
import dev.ibrahhout.shinystoreadmin.Utils.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersHistoryFargment extends Fragment {

    ProgressBar progressBar;

    private static final String TAG = "OrdersHistoryFargment";
    ArrayList<OrderModel> orders;
    ServicesHistoryAdapter servicesHistoryAdapter;

    public OrdersHistoryFargment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_orders, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.ordersRecyclerView);
        progressBar = view.findViewById(R.id.progress);


        orders = new ArrayList<>();


        servicesHistoryAdapter = new ServicesHistoryAdapter(getContext(), orders);
        recyclerView.setAdapter(servicesHistoryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));


//
//        FirebaseDatabase.getInstance().getReference().child(Constants.ORDERS_NODE)
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                        for (final DataSnapshot usersSnap : dataSnapshot.getChildren()) {
//
//                            final String userID = usersSnap.getKey();
//
//                            FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_USERS_NODE)
//                                    .child(userID).child(Constants.FIREBASE_USERS_USERNAME)
//                                    .addListenerForSingleValueEvent(new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                                            String userName = dataSnapshot.getValue(String.class);
//
//                                            for (DataSnapshot orderSnap : usersSnap.getChildren()) {
//
//                                                ArrayList<HashMap<String, Long>> details = (ArrayList<HashMap<String, Long>>) orderSnap.child(Constants.ORDER_NODE_ORDER_DETAILS).getValue();
//                                                OrderModel orderModel = orderSnap.getValue(OrderModel.class);
//
//                                                orderModel.setUserName(userName);
//                                                orderModel.setOrdDetials(details);
//                                                orderModel.setUserID(userID);
//
//                                                if (!orders.contains(orderModel))
//                                                    orders.add(orderModel);
//                                                else {
//                                                    orders.remove(orders.indexOf(orderModel));
//                                                    orders.add(orderModel);
//
//
//
//                                                }
//                                                Collections.sort(orders, new Comparator<OrderModel>() {
//                                                    @Override
//                                                    public int compare(OrderModel o1, OrderModel o2) {
//                                                        return (int) (Long.parseLong(o1.getOrderDate())- Long.parseLong(o2.getOrderDate()));
//                                                    }
//                                                });
//
//                                                // TODO: 7/24/2018 Notify adapter that you add new object
//                                                servicesHistoryAdapter.notifyDataSetChanged();
//                                                Collections.sort(orders, new Comparator<OrderModel>() {
//                                                    @Override
//                                                    public int compare(OrderModel o1, OrderModel o2) {
//                                                        return (int) (Long.parseLong(o1.getOrderDate())- Long.parseLong(o2.getOrderDate()));
//                                                    }
//                                                });
//                                                if (progressBar != null) {
//
//                                                    progressBar.setVisibility(View.GONE);
//                                                }
//
//                                            }
//
//
//                                        }
//
//                                        @Override
//                                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                            progressBar.setVisibility(View.GONE);
//                                            Toast.makeText(getContext(), "Error Loading Data check your connection please,", Toast.LENGTH_SHORT).show();
//
//                                        }
//                                    });
//                        }
//
////
////                        for (DataSnapshot snap:dataSnapshot.getChildren()) {
////
////                             snap.child(    )
////
////
////                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();

        updateData();
    }

    private void updateData() {



        FirebaseDatabase.getInstance().getReference().child(Constants.ORDERS_NODE)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (final DataSnapshot usersSnap : dataSnapshot.getChildren()) {

                            final String userID = usersSnap.getKey();

                            FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_USERS_NODE)
                                    .child(userID).child(Constants.FIREBASE_USERS_USERNAME)
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                            String userName = dataSnapshot.getValue(String.class);

                                            for (DataSnapshot orderSnap : usersSnap.getChildren()) {

                                                ArrayList<HashMap<String, Long>> details = (ArrayList<HashMap<String, Long>>) orderSnap.child(Constants.ORDER_NODE_ORDER_DETAILS).getValue();
                                                OrderModel orderModel = orderSnap.getValue(OrderModel.class);

                                                orderModel.setUserName(userName);
                                                orderModel.setOrdDetials(details);
                                                orderModel.setUserID(userID);

                                                if (!orders.contains(orderModel))
                                                    orders.add(orderModel);
                                                else {
                                                    orders.remove(orders.indexOf(orderModel));
                                                    orders.add(orderModel);



                                                }
                                                Collections.sort(orders, new Comparator<OrderModel>() {
                                                    @Override
                                                    public int compare(OrderModel o1, OrderModel o2) {
                                                        return (int) (Long.parseLong(o1.getOrderDate())- Long.parseLong(o2.getOrderDate()));
                                                    }
                                                });

                                                // TODO: 7/24/2018 Notify adapter that you add new object
                                                servicesHistoryAdapter.notifyDataSetChanged();
                                                Collections.sort(orders, new Comparator<OrderModel>() {
                                                    @Override
                                                    public int compare(OrderModel o1, OrderModel o2) {
                                                        return (int) (Long.parseLong(o1.getOrderDate())- Long.parseLong(o2.getOrderDate()));
                                                    }
                                                });
                                                if (progressBar != null) {

                                                    progressBar.setVisibility(View.GONE);
                                                }

                                            }


                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(getContext(), "Error Loading Data check your connection please,", Toast.LENGTH_SHORT).show();

                                        }
                                    });
                        }

//
//                        for (DataSnapshot snap:dataSnapshot.getChildren()) {
//
//                             snap.child(    )
//
//
//                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
