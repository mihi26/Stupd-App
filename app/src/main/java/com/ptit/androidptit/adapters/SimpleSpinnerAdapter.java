package com.ptit.androidptit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ptit.androidptit.R;
import com.ptit.androidptit.model.SimpleSpinnerItem;

import java.util.List;

public class SimpleSpinnerAdapter extends BaseAdapter {
    private final Context context;
    private final List<SimpleSpinnerItem> itemList;

    public SimpleSpinnerAdapter(Context context, List<SimpleSpinnerItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public SimpleSpinnerItem getItem(int i) {
        return itemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater.from(context));
        view = inflater.inflate(R.layout.spinner_item, null);
        ImageView icon = view.findViewById(R.id.spinner_icon);
        TextView text = view.findViewById(R.id.spinner_text);
        icon.setImageResource(itemList.get(i).getIcon());
        text.setText(itemList.get(i).getText());
        return view;
    }
}
