package com.sqlitedemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sqlitedemo.DBHelper.DataBase;
import com.sqlitedemo.Model.ThemelModel;
import com.sqlitedemo.R;
import com.sqlitedemo.adapter.ThemeAdapter;

import java.util.ArrayList;

public class AllDataListActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private ArrayList<ThemelModel> themelModellist;
    private ThemeAdapter themeAdapter;
    private DataBase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_data_list);

        themelModellist = new ArrayList<>();
        db = new DataBase(AllDataListActivity.this);

        recyclerView = findViewById(R.id.recyclerview);

        themelModellist.addAll(db.getAllUser());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //this.recyclerView.addItemDecoration(new ItemOffsetDecoration(getApplicationContext(), 5));
        recyclerView.setNestedScrollingEnabled(false);
        themeAdapter = new ThemeAdapter(this, themelModellist);
        recyclerView.setAdapter(themeAdapter);
    }
}
