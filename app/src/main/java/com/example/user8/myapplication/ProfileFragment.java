package com.example.user8.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private RecyclerView recyclerView;
    private TextView txtEmpty;
    private List<AddTask> result;
    private ProfileAdapter adapter;

    private FirebaseDatabase database;
    private DatabaseReference ref;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("IDOIDO");

        result = new ArrayList<>();

        recyclerView = v.findViewById(R.id.food_list);
        txtEmpty = v.findViewById(R.id.txtEmpty);

        recyclerView.setHasFixedSize(false);
        LinearLayoutManager lim = new LinearLayoutManager(getContext());
        lim.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(lim);

        adapter = new ProfileAdapter(result, getContext());
        recyclerView.setAdapter(adapter);

        updateList();
        checkIfEmpty();

        return v;
    }

    private void checkIfEmpty() {
        if (result.size() == 0) {
            recyclerView.setVisibility(View.INVISIBLE);
            txtEmpty.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            txtEmpty.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                if (adapter.curPos > -1) {
                    deleteTask(adapter.curPos);
                }
                break;

            case 1:
                if (adapter.curPos > -1) {
                    closeMenu(adapter.curPos);
                }
                break;

        }
        return super.onContextItemSelected(item);
    }

    private int getItemIndex(AddTask food) {
        int index = -1;
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i).taskkey.equals(food.taskkey)) {
                index = i;
                break;
            }

        }
        return index;
    }

    private void updateList() {

        ref.orderByChild("taskstatus").equalTo("FINISH").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                result.add(dataSnapshot.getValue(AddTask.class));
                adapter.notifyDataSetChanged();
                checkIfEmpty();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                AddTask model = dataSnapshot.getValue(AddTask.class);

                int index = getItemIndex(model);

                result.set(index, model);
                adapter.notifyItemChanged(index);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                AddTask model = dataSnapshot.getValue(AddTask.class);

                int index = getItemIndex(model);

                result.remove(index);
                adapter.notifyItemRemoved(index);
                checkIfEmpty();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void deleteTask(int pos) {
        ref.child(result.get(pos).taskkey).removeValue();
    }

    private void closeMenu(int pos) {
        ((Activity) getContext()).closeContextMenu();
    }

}

