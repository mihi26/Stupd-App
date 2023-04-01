package com.ptit.androidptit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ptit.androidptit.MainActivity;
import com.ptit.androidptit.R;
import com.ptit.androidptit.model.Job;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.ItemViewHolder> {
    Context context;

    private final List<Job> unFilteredItemList;

    private List<Job> itemList;

    private int current = -1;

    public JobAdapter(Context context) {
        this.context = context;
        this.itemList = new ArrayList<>();
        this.unFilteredItemList = new ArrayList<>();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView itemIcon;
        TextView itemText;
        ImageButton editButton;
        ImageButton deleteButton;

        public ItemViewHolder(@NonNull View view) {
            super(view);
            initView(view);
        }

        private void initView(View view) {
            itemIcon = view.findViewById(R.id.item_image);
            itemText = view.findViewById(R.id.item_text);
            editButton = view.findViewById(R.id.btn_edit);
            deleteButton = view.findViewById(R.id.btn_delete);
        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        int p = holder.getAdapterPosition();
        Job item = itemList.get(p);
        holder.itemText.setText(item.getName() + " - " + item.getDate());
        holder.itemIcon.setImageResource(item.getIsMale() ? R.drawable.male : R.drawable.female);

        holder.editButton.setOnClickListener((v) -> {
            current = p;
            ((MainActivity) context).onEdit(itemList.get(p));
        });

        holder.deleteButton.setOnClickListener((v) -> {
            unFilteredItemList.remove(itemList.get(p));
            onSearch();
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void addItem(Job item) {
        item.setId(unFilteredItemList.size());
        unFilteredItemList.add(item);
        onSearch();
    }

    public void updateItem(Job _item) {
        if (current < 0) return;
        Job item = itemList.get(current);
        if (item != null) {
            item.update(_item);
            onSearch();
        }
    }

    public void onSearch(String text) {
        itemList = unFilteredItemList.stream().filter((job) -> job.matched(text)).collect(Collectors.toList());
        current = -1;
        notifyDataSetChanged();
    }

    public void onSearch() {
        onSearch("");
    }

}
