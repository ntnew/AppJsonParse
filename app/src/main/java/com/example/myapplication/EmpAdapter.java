package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EmpAdapter extends RecyclerView.Adapter<EmpAdapter.EmpViewHolder> {
    ArrayList<Employe> employees;
    public EmpAdapter(ArrayList<Employe> employees){
        this.employees = employees;
    }

    public class EmpViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView phone_number;
        TextView skills;
        public EmpViewHolder (View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            phone_number = (TextView) view.findViewById(R.id.phone_number);
            skills = (TextView) view.findViewById(R.id.skills);
        }
    }
    @NonNull
    @Override
    public EmpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list, parent, false);
        return new EmpViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmpViewHolder holder, int position){
        holder.name.setText(employees.get(position).getName());
        holder.phone_number.setText(employees.get(position).getPhone());
        holder.skills.setText(employees.get(position).getSkills());
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }
}
