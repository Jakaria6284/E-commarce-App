package com.example.mymall;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class searchActivity extends AppCompatActivity {
    private Toolbar toolbar2;
    private TextView noproductfound;
    private SearchView searchView;
    private RecyclerView searchRecyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchRecyclerview=findViewById(R.id.searchrecyclerview);
        searchView=findViewById(R.id.searchview);
        noproductfound=findViewById(R.id.noproductfound);
        toolbar2=findViewById(R.id.toolbar2);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.md_red_50));
        }


        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        searchRecyclerview.setLayoutManager(layoutManager);
        List<wishlistModel> list=new ArrayList<>();
        List<String> ids=new ArrayList<>();
        Adapter adapter=new Adapter(list,false);
        searchRecyclerview.setAdapter(adapter);
        adapter.setFromsearch(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                list.clear();
                ids.clear();
                String []tags=s.toLowerCase().split("");
                for(String tag:tags)
                {
                    tag.trim();
                    FirebaseFirestore.getInstance().collection("PRODUCTS").whereArrayContains("tags",tag)
                            .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if(task.isSuccessful())
                                    {
                                        for(DocumentSnapshot documentSnapshot:task.getResult().getDocuments())
                                        {
                                            wishlistModel model  =new wishlistModel(documentSnapshot.getId(), documentSnapshot.getString("product_img_1")
                                                    , documentSnapshot.getString("product_title")
                                                    , documentSnapshot.getLong("free_coupen")
                                                    , documentSnapshot.getString("avg_rating")
                                                    , documentSnapshot.getLong("total_rating")
                                                    , documentSnapshot.getString("product_price")
                                                    , documentSnapshot.getString("cutted_price")
                                                    , documentSnapshot.getBoolean("COD")
                                                    , true);
                                            model.setTags((ArrayList<String>) documentSnapshot.get("tags"));
                                            if(!ids.contains(model.getProductID()))
                                            {
                                                list.add(model);
                                                ids.add(model.getProductID());
                                            }
                                        }
                                        if(tag.equals(tags[tags.length-1]))
                                        {
                                            if(list.size()==0)
                                            {
                                                noproductfound.setVisibility(View.VISIBLE);
                                                searchRecyclerview.setVisibility(View.GONE);
                                            }else
                                            {
                                                noproductfound.setVisibility(View.GONE);
                                            }
                                            adapter.getFilter().filter(s);
                                        }

                                    }else
                                    {
                                        String error=task.getException().getMessage();
                                        Toast.makeText(searchActivity.this, ""+error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


    }

    class Adapter extends wishlistAdapter implements Filterable{

        List<wishlistModel>originalist;
        public Adapter(List<wishlistModel> wishlistModelList, Boolean wishlistboolean) {
            super(wishlistModelList, wishlistboolean);
            originalist=wishlistModelList;
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {

                    return null;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    notifyDataSetChanged();
                }
            };
        }
    }
}