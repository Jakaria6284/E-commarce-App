package com.example.mymall;


import static com.example.mymall.fragment.DBquires.list;
import static com.example.mymall.fragment.DBquires.loadedcategoryName;
import static com.example.mymall.fragment.DBquires.setFragmentData;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    private RecyclerView category_recyclerview;
    HomePageAdaptar homePageAdaptar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String title =getIntent().getStringExtra("Categoryname");
        getSupportActionBar().setTitle(title);
        category_recyclerview=findViewById(R.id.categoryActivity_recyclerview);




        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decor = getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }






        ///testing recycler view
        LinearLayoutManager testingLayout=new LinearLayoutManager(this);
        testingLayout.setOrientation(LinearLayoutManager.VERTICAL);
        category_recyclerview.setLayoutManager(testingLayout);






        int listposition=0;
        for(int x=0;x<loadedcategoryName.size();x++)
        {
            if(loadedcategoryName.get(x).equals(title.toUpperCase()))
            {
             listposition=x;

            }
        }
        if(listposition==0)
        {
            loadedcategoryName.add(title.toUpperCase());
            list.add(new ArrayList<HomepageModel>());
            homePageAdaptar=new HomePageAdaptar(list.get(loadedcategoryName.size()-1));
            setFragmentData(category_recyclerview,this,loadedcategoryName.size()-1,title);
        }else
        {
            homePageAdaptar=new HomePageAdaptar(list.get(listposition));
        }

        category_recyclerview.setAdapter(homePageAdaptar);
        homePageAdaptar.notifyDataSetChanged();




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_icon,menu);
        return true;

        // return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.search)
        {
            Intent searchIntent=new Intent(this,searchActivity.class);
            startActivity(searchIntent);
            return true;
        }else if(id==android.R.id.home)
        {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}