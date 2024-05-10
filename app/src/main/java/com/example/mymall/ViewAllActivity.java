package com.example.mymall;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewAllActivity extends AppCompatActivity {
    private RecyclerView viewall_recyclerview;
    private GridView gridView;
    public static List<wishlistModel> wishlistModelList ;
    public static  List<horizental_scroll_product_model> horizental_scroll_product_modelList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));

        gridView=findViewById(R.id.viewall_gridview);
        viewall_recyclerview=findViewById(R.id.viewall_recyclerview);
        viewall_recyclerview.setVisibility(View.VISIBLE);

        int layout_code=getIntent().getIntExtra("layout_code",-1);
        if(layout_code==0) {
            LinearLayoutManager layoutManager=new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            viewall_recyclerview.setLayoutManager(layoutManager);
            wishlistAdapter adapter = new wishlistAdapter(wishlistModelList, false);
            viewall_recyclerview.setAdapter(adapter);
            adapter.notifyDataSetChanged();


        }else if(layout_code==1) {


            gridView.setVisibility(View.VISIBLE);
            GridProductViewAdaptar gridProductViewAdaptar = new GridProductViewAdaptar(horizental_scroll_product_modelList);
            gridView.setAdapter(gridProductViewAdaptar);
            gridProductViewAdaptar.notifyDataSetChanged();
        }


    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        if(id==android.R.id.home)
        {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}