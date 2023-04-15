package com.ptit.androidptit.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ptit.androidptit.R;
import com.ptit.androidptit.adapters.ItemAdapter;

public class ListItemFragment extends Fragment {
    private ItemAdapter itemAdapter;

    public ListItemFragment() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_item, container, false);
        initComponents(view);
        return view;
    }

    private void initComponents(View view) {
        itemAdapter = new ItemAdapter(requireContext());
        RecyclerView recyclerView = view.findViewById(R.id.list_item);
        recyclerView.setAdapter(itemAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        itemAdapter.refreshItemList();
        view.findViewById(R.id.add_item).setOnClickListener((v) -> {
            FormFragment formFragment = FormFragment.newInstance(-1);
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, formFragment)
                    .addToBackStack(null)
                    .commit();

        });
    }
}