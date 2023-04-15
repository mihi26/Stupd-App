package com.ptit.androidptit.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.ptit.androidptit.MainActivity;
import com.ptit.androidptit.R;
import com.ptit.androidptit.Utils.Database;
import com.ptit.androidptit.Utils.StateManagement;
import com.ptit.androidptit.fragments.FormFragment;
import com.ptit.androidptit.model.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    Context context;

    private List<Item> itemList;

    public ItemAdapter(Context context) {
        this.context = context;
    }



    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView itemName;
        TextView itemDeadline;
        TextView itemStatus;
        ImageButton editButton;
        ImageButton deleteButton;

        public ItemViewHolder(@NonNull View view) {
            super(view);
            initView(view);
        }

        private void initView(View view) {
            itemName = view.findViewById(R.id.item_name);
            itemDeadline = view.findViewById(R.id.item_deadline);
            itemStatus = view.findViewById(R.id.item_status);
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
        Item item = itemList.get(p);
        holder.itemName.setText(item.getName());
        holder.itemDeadline.setText(item.getDeadline());
        holder.itemStatus.setText(item.getStatus());

        holder.editButton.setOnClickListener((v) -> {
            FormFragment formFragment = FormFragment.newInstance(itemList.get(position).getId());
            FragmentManager fragmentManager = ((MainActivity) context).getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, formFragment).addToBackStack(null).commit();
        });

        holder.deleteButton.setOnClickListener((v) -> {
            int id = itemList.get(position).getId();
            Database.of(v.getContext()).deleteItem(id);
            Snackbar.make(v, "Deleted item #" + id, Snackbar.LENGTH_SHORT).show();
            refreshItemList();
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void refreshItemList() {
        this.itemList = Database.of(context).getAllItems();
        Log.d("ItemAdapter", "refreshItemList: " + itemList.size());
        notifyDataSetChanged();
    }
}
