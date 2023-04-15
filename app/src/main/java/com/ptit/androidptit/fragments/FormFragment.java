package com.ptit.androidptit.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.opengl.EGLExt;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.ptit.androidptit.MainActivity;
import com.ptit.androidptit.R;
import com.ptit.androidptit.Utils.Database;
import com.ptit.androidptit.model.Item;

import java.util.Calendar;
import java.util.Objects;

public class FormFragment extends Fragment {
    EditText inputName;
    EditText inputDescription;
    EditText inputDate;
    Spinner inputStatus;
    CheckBox inputParty;
    private static final String ITEM_ID_PARAM = "itemId";

    private String itemId;

    public FormFragment() {
    }

    public static FormFragment newInstance(int itemId) {
        FormFragment fragment = new FormFragment();
        Bundle args = new Bundle();
        args.putString(ITEM_ID_PARAM, String.valueOf(itemId));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            itemId = getArguments().getString(ITEM_ID_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_form, container, false);
        inputName = view.findViewById(R.id.input_name);
        inputDescription = view.findViewById(R.id.input_description);
        inputDate = view.findViewById(R.id.input_date);
        inputStatus = view.findViewById(R.id.input_status);
        inputStatus.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, new String[]{"Chưa thực hiện", "Đang thực hiện", "Hoàn thành"}));
        inputParty = view.findViewById(R.id.input_party);
        inputDate.setOnClickListener((v) -> new DatePickerDialog(getActivity(), (datePicker, year, month, date) -> inputDate.setText(String.format("%02d/%02d/%04d", date, month + 1, year)), Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DATE))
                .show());
        Button saveButton = view.findViewById(R.id.button_save);
        saveButton.setOnClickListener((v) -> {
            try {
                Item item = getModelFromForm();
                if (item.getId() == null) {
                    Database.of(requireContext()).addItem(item);
                    Snackbar.make(v, "Added item successfully", Snackbar.LENGTH_SHORT).show();
                    getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new ListItemFragment()).commit();
                } else {
                    Database.of(requireContext()).updateItem(item);
                    Snackbar.make(v, "Updated item successfully", Snackbar.LENGTH_SHORT).show();
                    getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new ListItemFragment()).commit();
                }
            } catch (Exception e) {
                Snackbar.make(v, Objects.requireNonNull(e.getMessage()), Snackbar.LENGTH_SHORT).show();
            }

        });
        if (itemId != null && !itemId.equals("-1")) {
            Item item = Database.of(requireContext()).getItemById(Integer.parseInt(itemId));
            inputName.setText(item.getName());
            inputDescription.setText(item.getDescription());
            inputDate.setText(item.getDeadline());
            inputStatus.setSelection(((ArrayAdapter<String>) inputStatus.getAdapter()).getPosition(item.getStatus()));
            inputParty.setChecked(item.getParty());
            TextView title = view.findViewById(R.id.form_title);
            title.setText("Cập nhật công việc " + item.getName());
        }
        return view;
    }

    private Item getModelFromForm() {
        Item item = new Item();
        if (itemId != null && !itemId.equals("-1")) {
            item.setId(Integer.parseInt(itemId));
        }
        item.setName(inputName.getText().toString());
        item.setDescription(inputDescription.getText().toString());
        item.setDeadline(inputDate.getText().toString());
        item.setStatus((String) inputStatus.getSelectedItem());
        item.setParty(inputParty.isChecked());
        return item;
    }
}