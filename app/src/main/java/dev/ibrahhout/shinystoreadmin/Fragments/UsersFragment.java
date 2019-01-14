package dev.ibrahhout.shinystoreadmin.Fragments;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dev.ibrahhout.shinystoreadmin.Adapters.UsersAdapter;
import dev.ibrahhout.shinystoreadmin.Models.UserModel;
import dev.ibrahhout.shinystoreadmin.R;
import dev.ibrahhout.shinystoreadmin.Utils.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsersFragment extends Fragment {


    @BindView(R.id.usersProgress)
    ProgressBar usersProgress;
    Unbinder unbinder;
    @BindView(R.id.usersRecycler)
    RecyclerView usersRecycler;
    ArrayList<UserModel> userModels;

    UsersAdapter adapter;

    public UsersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        unbinder = ButterKnife.bind(this, view);


        userModels = new ArrayList<>();
        adapter = new UsersAdapter(getContext(), userModels);

        usersRecycler.setAdapter(adapter);
        usersRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_USERS_NODE).keepSynced(true);
        // Inflate the layout for this fragment
        FirebaseDatabase.getInstance().getReference().child(Constants.FIREBASE_USERS_NODE)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        userModels.add(dataSnapshot.getValue(UserModel.class));
                        adapter.notifyDataSetChanged();
                        if (usersProgress!= null) {

                            usersProgress.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
