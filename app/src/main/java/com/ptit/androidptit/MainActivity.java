package com.ptit.androidptit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.ptit.androidptit.adapters.JobAdapter;
import com.ptit.androidptit.adapters.SimpleSpinnerAdapter;
import com.ptit.androidptit.model.Job;
import com.ptit.androidptit.model.SimpleSpinnerItem;

import java.sql.Date;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EditText inputName;
    private EditText searchInput;
    private EditText inputDescription;
    private TextView dateContent;
    private RadioButton optionMale;
    private RadioButton optionFemale;
    private Button addButton;
    private Button updateButton;
    private Spinner spinner;
    private JobAdapter itemAdapter;
    private ImageButton setDateButton;
    private DatePickerDialog datePickerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    private void initComponents() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        recyclerView = findViewById(R.id.list_item);
        inputName = findViewById(R.id.input_name);
        inputDescription = findViewById(R.id.input_description);
        dateContent = findViewById(R.id.date_text);
        optionMale = findViewById(R.id.options_male);
        optionFemale = findViewById(R.id.options_female);
        addButton = findViewById(R.id.add_btn);
        updateButton = findViewById(R.id.update_btn);
        searchInput = findViewById(R.id.search_input);
        spinner = findViewById(R.id.spinner);
        setDateButton = findViewById(R.id.btn_set_date);
        datePickerDialog = new DatePickerDialog(this, (datePicker, year, month, date) -> {
            dateContent.setText(date + "/" + month + "/" + year);
            datePickerDialog.hide();
        }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DATE));

        itemAdapter = new JobAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(itemAdapter);

        List<SimpleSpinnerItem> itemList = new ArrayList<>();
        itemList.add(new SimpleSpinnerItem("Male", R.drawable.male));
        itemList.add(new SimpleSpinnerItem("Female", R.drawable.female));
        SimpleSpinnerAdapter spinnerAdapter = new SimpleSpinnerAdapter(this, itemList);
        spinner.setAdapter(spinnerAdapter);

        optionMale.setChecked(true);

        optionMale.setOnClickListener(view -> optionFemale.setChecked(false));

        optionFemale.setOnClickListener(view -> optionMale.setChecked(false));

        addButton.setOnClickListener((view -> {
            // Handle add
            itemAdapter.addItem(getCurrentItem());
            Toast.makeText(this, "Item added", Toast.LENGTH_LONG).show();
        }));

        updateButton.setOnClickListener((view -> {
            // Handle update
            itemAdapter.updateItem(getCurrentItem());
            Toast.makeText(this, "Item updated", Toast.LENGTH_LONG).show();
        }));

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                itemAdapter.onSearch(searchInput.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        setDateButton.setOnClickListener((view) -> datePickerDialog.show());
    }

    private Job getCurrentItem() {
        return new Job(inputName.getText().toString(), inputDescription.getText().toString(), optionMale.isChecked(), dateContent.getText().toString());
    }

    public void onEdit(Job job) {
        inputName.setText(job.getName());
        inputDescription.setText(job.getDescription());
        optionMale.setChecked(job.getIsMale());
        optionFemale.setChecked(!job.getIsMale());
        dateContent.setText(job.getDate());
    }
}