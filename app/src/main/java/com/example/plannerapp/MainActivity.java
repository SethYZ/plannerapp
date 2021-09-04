package com.example.plannerapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewCategoryList;
    ArrayList<CategoryList> categoryList = new ArrayList<>();
    private ExtendedFloatingActionButton extendedFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewCategory();
        bottomNavigation();
        fabNavigation();

    }

    private void fabNavigation() {
        extendedFloatingActionButton = findViewById(R.id.fab_addTask);

        extendedFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTaskDialogBox();
            }
        });
    }

    private void addTaskDialogBox() {
        LinearLayout layout = new LinearLayout(MainActivity.this);
        layout.setOrientation(LinearLayout.VERTICAL);

        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Adding Task");
        alertDialog.setIcon(R.drawable.ic_add_task);

        //Adding Editable text into the dialog
        EditText inputName = new EditText(MainActivity.this);
        inputName.setHint("Task Name");
        inputName.setPadding(25,50,25,50);
        layout.addView(inputName);

        EditText inputDuration = new EditText(MainActivity.this);
        inputDuration.setHint("Duration");
        inputDuration.setPadding(25,50,25,50);
        layout.addView(inputDuration);
        alertDialog.setView(layout);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String mInputName = inputName.getText().toString();
                int mInputDuration = Integer.parseInt(inputDuration.getText().toString());
                categoryList.add(new CategoryList(mInputName, mInputDuration));
                adapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Task added", Toast.LENGTH_SHORT).show();
            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }

    private void bottomNavigation() {
        LinearLayout homeBtn=findViewById(R.id.homeBtn);

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,MainActivity.class));
            }
        });
    }

    private void recyclerViewCategory() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategoryList = findViewById(R.id.recycler_view1);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);

        categoryList.add(new CategoryList("Reading", 1));
        categoryList.add(new CategoryList("Exercise", 60));
        categoryList.add(new CategoryList("Working", 120));
        categoryList.add(new CategoryList("Playing games", 30));

        adapter = new CategoryAdapter(categoryList);
        recyclerViewCategoryList.setAdapter(adapter);

    }
}