package com.example.user8.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class AddTaskAdapter extends RecyclerView.Adapter<AddTaskAdapter.Taskviewholder> {

    public int curPos = -1;

    //    FirebaseAuth auth;
//    FirebaseUser user;
    private List<AddTask> list;
    private Context mContext;

    public AddTaskAdapter(List<AddTask> list, Context context) {
        this.list = list;
        this.mContext = context;
    }


    @Override
    public Taskviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.view_item, parent, false);
        final Taskviewholder vHolder = new Taskviewholder(v);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(final Taskviewholder holder, final int position) {
        AddTask myTask = list.get(position);

        holder.txtTaskName.setText(myTask.taskname);
        holder.txtTime.setText(myTask.tasktime);
        holder.txtDate.setText(myTask.taskdate);
        holder.txtTaskDetails.setText(myTask.taskdetails);
        holder.layoutFinishdate.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Taskviewholder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        TextView txtTaskName, txtTaskDetails, txtDate, txtTime;
        LinearLayout layoutFinishdate;

        public Taskviewholder(View itemView) {
            super(itemView);
            txtTaskName = itemView.findViewById(R.id.text_taskname);
            txtTaskDetails = itemView.findViewById(R.id.text_taskdetails);
            txtDate = itemView.findViewById(R.id.text_date);
            txtTime = itemView.findViewById(R.id.text_time);
            layoutFinishdate = itemView.findViewById(R.id.linearfinishdate);

            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    curPos = getAdapterPosition();
                }
            });
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            curPos = getAdapterPosition();
            menu.setHeaderTitle("Finish?");
            menu.add(0, 0, 0, "Yes");
            menu.add(0, 1, 0, "No");
        }

    }
}
