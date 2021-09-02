package com.example.plannerapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    ArrayList<CategoryList> categoryList;

    public CategoryAdapter(ArrayList<CategoryList> categoryList) {
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category, parent, false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.categoryTitle.setText(categoryList.get(position).getTitle());
        holder.categoryDuration.setText(categoryList.get(position).getDuration() + " minutes");

        holder.category_viewholder_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), CategoryDetailActivity.class);
                intent.putExtra("object", categoryList.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView categoryTitle, categoryDuration;
        ConstraintLayout category_viewholder_Layout;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            category_viewholder_Layout = itemView.findViewById(R.id.category_viewholder_Layout);
            categoryTitle = itemView.findViewById(R.id.categoryTitle);
            categoryDuration = itemView.findViewById(R.id.categoryDuration);

        }
    }
}
