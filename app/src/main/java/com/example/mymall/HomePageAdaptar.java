package com.example.mymall;

import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomePageAdaptar extends RecyclerView.Adapter {
    private List<HomepageModel> homepageModelList;
    private RecyclerView.RecycledViewPool recycledViewPool;
    private FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    public HomePageAdaptar(List<HomepageModel> homepageModelList) {
        this.homepageModelList = homepageModelList;
        recycledViewPool=new RecyclerView.RecycledViewPool();
    }

    @Override
    public int getItemViewType(int position) {
       switch (homepageModelList.get(position).getType())
       {
           case 0:
               return HomepageModel.Banner_Slider;
           case 1:
               return  HomepageModel.Strip_Add;
           case 2:
               return  HomepageModel.Horizental_product_view;
           case 3:
              return  HomepageModel.Grid_product_view;
           default:


               return -1;

       }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case HomepageModel.Banner_Slider:
            View Banner_slider_view= LayoutInflater.from(parent.getContext()).inflate(R.layout.sliding_add_layout,parent,false);
            return new BannerSliderViewHolder(Banner_slider_view);
            case  HomepageModel.Strip_Add:
                View Strip_Add_view= LayoutInflater.from(parent.getContext()).inflate(R.layout.strip_add_layout,parent,false);
                return new StripAddViewHolder(Strip_Add_view);
            case  HomepageModel.Horizental_product_view:
                View HorizentalProductview=LayoutInflater.from(parent.getContext()).inflate(R.layout.horizental_scroll_layout,parent,false);
                return new HorizentalProductViewHolder(HorizentalProductview);
            case  HomepageModel.Grid_product_view:
                View gridProductview=LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_product_view,parent,false);
                return new GridProductViewHolder (gridProductview);



            default:
                return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (homepageModelList.get(position).getType())
        {
            case  HomepageModel.Banner_Slider:

                List<sliderModel> sliderModelList=homepageModelList.get(position).getSliderModelList();
                BannerSliderViewHolder bannerSliderViewHolder = (BannerSliderViewHolder) holder;
                (( BannerSliderViewHolder)holder).setBannerSliderViewPager(sliderModelList);
                Animation animation = AnimationUtils.loadAnimation(bannerSliderViewHolder.itemView.getContext(), R.anim.left);
                bannerSliderViewHolder.itemView.startAnimation(animation);

                break;
                case HomepageModel.Strip_Add:
                    String resource=homepageModelList.get(position).getResource();
                    ((StripAddViewHolder)holder).setStripAdd(resource);
                    break;
                    case  HomepageModel.Horizental_product_view:
                       String Title=homepageModelList.get(position).getTitle();
                      // String backgroundColor=homepageModelList.get(position).getBackground();
                       List<horizental_scroll_product_model> horizental_scroll_product_modelList=homepageModelList.get(position).getHorizentalScrollProductModelList();
                       List<wishlistModel>wishlistModelList=homepageModelList.get(position).getViewallproduct();
                        ((HorizentalProductViewHolder)holder).setHorizental_layout(horizental_scroll_product_modelList,Title,wishlistModelList);
                        break;
            case  HomepageModel.Grid_product_view:
                String title=homepageModelList.get(position).getTitle();
                List<horizental_scroll_product_model> gridProductmoderList=homepageModelList.get(position).getHorizentalScrollProductModelList();

                    ((GridProductViewHolder)holder).setGridLayout(gridProductmoderList, title);


                break;





            default:
                return;
        }


    }

    @Override
    public int getItemCount() {
        return homepageModelList.size();
    }

    public class BannerSliderViewHolder extends RecyclerView.ViewHolder {
        private ViewPager bannerSliderViewPager;

        private sliderAdapter sliderAdaptar;
        private int currentpager ;
        private Timer timer;
        final private  long delaytime=2000;
        final private  long priodtime=2000;
        private List<sliderModel> arrangedList;

        public BannerSliderViewHolder(@NonNull View itemView) {
            super(itemView);
            bannerSliderViewPager = itemView.findViewById(R.id.baner_slider_viewPager);



        }

        private void pagelooper(List<sliderModel> sliderModelList) {
            if (currentpager == sliderModelList.size() - 2) {
                currentpager = 2;
                bannerSliderViewPager.setCurrentItem(currentpager, false);

            }
            if (currentpager == 1) {
                currentpager = sliderModelList.size() - 3;
                bannerSliderViewPager.setCurrentItem(currentpager, false);

            }

        }

        private void startbannerSlideShow(List<sliderModel> sliderModelList) {
            Handler handler = new Handler();
            Runnable update = new Runnable() {
                @Override
                public void run() {
                    if (currentpager >= sliderModelList.size()) {
                        currentpager = 1;
                    }
                    bannerSliderViewPager.setCurrentItem(currentpager++, true);

                }
            };
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(update);
                }
            }, delaytime, priodtime);
        }
        private void setBannerSliderViewPager(List<sliderModel> sliderModelList) {
            currentpager=2;
            if(timer!=null)
            {
                timer.cancel();
            }
            arrangedList=new ArrayList<>();
            for(int x=0;x<sliderModelList.size();x++)
            {
                arrangedList.add(x,sliderModelList.get(x));
            }
            arrangedList.add(0,sliderModelList.get(sliderModelList.size()-2));
            arrangedList.add(1,sliderModelList.get(sliderModelList.size()-1));
            arrangedList.add(sliderModelList.get(0));
            arrangedList.add(sliderModelList.get(1));

            sliderAdaptar = new sliderAdapter(arrangedList);
            bannerSliderViewPager.setAdapter(sliderAdaptar);
            bannerSliderViewPager.setClipToPadding(false);
            bannerSliderViewPager.setPageMargin(20);
            bannerSliderViewPager.setCurrentItem(currentpager);
            ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    currentpager = position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if (state == ViewPager.SCROLL_STATE_IDLE) {
                        pagelooper(arrangedList);
                    }

                }
            };
            bannerSliderViewPager.addOnPageChangeListener(onPageChangeListener);
            startbannerSlideShow(arrangedList);

          /*  bannerSliderViewPager.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    pagelooper(sliderModelList);
                    stopbannerSlidShow();
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                        startbannerSlideShow(sliderModelList);
                    return false;
                }
            });*/

        }

        private void stopbannerSlidShow() {
            timer.cancel();
        }

    }




    public class StripAddViewHolder extends RecyclerView.ViewHolder{
        private ImageView stripImage;
        private ConstraintLayout stripAddContainer;

        public StripAddViewHolder(@NonNull View itemView) {
            super(itemView);
            stripImage= itemView.findViewById(R.id.strip_add_image);
            stripAddContainer=itemView.findViewById(R.id.strip_add_container);
        }
        private void setStripAdd(String resource)
        {
            Glide.with(itemView.getContext()).load(resource).apply(new RequestOptions().placeholder(R.drawable.image)).into(stripImage);
        }
    }
    public class HorizentalProductViewHolder extends RecyclerView.ViewHolder{
        private TextView Horizental_layoutTitle;
        private Button Horizental_layoutviewAllBtn;
        private RecyclerView horzentalrecyclerView;
        private ConstraintLayout background;

        public HorizentalProductViewHolder(@NonNull View itemView) {
            super(itemView);
            Horizental_layoutTitle=itemView.findViewById(R.id.horizental_scroll_layout_title)  ;
            Horizental_layoutviewAllBtn=itemView.findViewById(R.id.horizental_scroll_layout_button);
            horzentalrecyclerView=itemView.findViewById(R.id.horizental_scroll_layout_recyclerView);
            horzentalrecyclerView.setRecycledViewPool(recycledViewPool);
           // background=itemView.findViewById(R.id.container_horizental);
        }
        private void setHorizental_layout(List<horizental_scroll_product_model> horizental_scroll_product_modelList,String Title,List<wishlistModel>viewallproduct)
        {
            Horizental_layoutTitle.setText(Title);
          //  background.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(backgroundcolor)));

            for(horizental_scroll_product_model model:horizental_scroll_product_modelList)
            {
               if(model.getProduct_id() != null && model.getProductTitle() != null && !model.getProduct_id().isEmpty() && model.getProductTitle().isEmpty())
               {
                   firebaseFirestore.collection("PRODUCTS")
                           .document(model.getProduct_id())
                           .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                               @Override
                               public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                   if(task.isSuccessful())
                                   {
                                       model.setProductTitle(task.getResult().getString("product_title"));
                                       model.setProductImage(task.getResult().getString("product_img_1"));
                                       model.setProductPrice(task.getResult().getString("product_price"));

                                       wishlistModel wishlistModell=viewallproduct
                                               .get(horizental_scroll_product_modelList.indexOf(model));
                                       wishlistModell.setRating(task.getResult().getString("avg_rating"));
                                       Long totalRating = task.getResult().getLong("total_rating");
                                       if (totalRating != null) {
                                           wishlistModell.setTotalRating(totalRating);
                                       }
                                       wishlistModell.setWish_product_title(task.getResult().getString("product_title"));
                                       wishlistModell.setProductPrice(task.getResult().getString("product_price"));
                                       wishlistModell.setCuttedprice(task.getResult().getString("cutted_price"));
                                       wishlistModell.setWish_product_image(task.getResult().getString("product_img_1"));
                                       Long freeCoupen = task.getResult().getLong("free_coupen");
                                       if (freeCoupen != null) {
                                           wishlistModell.setWish_cupon(freeCoupen);
                                       }
                                       Boolean codValue = task.getResult().getBoolean("COD");
                                       if (codValue != null) {
                                           wishlistModell.setCOD(codValue);
                                       }

                                       Long stockQtyValue = task.getResult().getLong("stock_qty");
                                       if (stockQtyValue != null) {
                                           wishlistModell.setIn_stock(stockQtyValue > 0);
                                       }

                                       if(horizental_scroll_product_modelList.indexOf(model)==horizental_scroll_product_modelList.size()-1)
                                       {
                                           if(horzentalrecyclerView.getAdapter()!=null)
                                           {
                                               horzentalrecyclerView.getAdapter().notifyDataSetChanged();
                                           }
                                       }

                                   }
                               }
                           });
               }
            }

            if(horizental_scroll_product_modelList.size()>8)
            {
                Horizental_layoutviewAllBtn.setVisibility(View.VISIBLE);
                Horizental_layoutviewAllBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ViewAllActivity.wishlistModelList=viewallproduct;
                        Intent intent=new Intent(itemView.getContext(),ViewAllActivity.class);
                        intent.putExtra("layout_code",0);
                        intent.putExtra("title",Title);
                        itemView.getContext().startActivity(intent);


                    }
                });
            }else
            {
                Horizental_layoutviewAllBtn.setVisibility(View.INVISIBLE);
            }

            horizental_product_scroll_Adaptar horizental_product_scroll_adaptar=new horizental_product_scroll_Adaptar(horizental_scroll_product_modelList);
            LinearLayoutManager layoutManager=new LinearLayoutManager(itemView.getContext());
            horzentalrecyclerView.setLayoutManager(layoutManager);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            horzentalrecyclerView.setAdapter(horizental_product_scroll_adaptar);
        }
    }
    public class GridProductViewHolder extends RecyclerView.ViewHolder{
      private TextView gridLayoutTitle;
      private Button gridLayoutButton;
      private GridLayout gridLayoutView;

        public GridProductViewHolder(@NonNull View itemView) {
            super(itemView);
             gridLayoutTitle=itemView.findViewById(R.id.grid_product_layout_title);
             gridLayoutButton=itemView.findViewById(R.id.grid_product_layout_button);
            gridLayoutView=itemView.findViewById(R.id.grid_product_layout_gridView);
        }
        private void setGridLayout(List<horizental_scroll_product_model> horizental_scroll_product_modelList,String Title) {
            gridLayoutTitle.setText(Title);
            for (horizental_scroll_product_model model : horizental_scroll_product_modelList) {
                if (!model.getProduct_id().isEmpty() && model.getProductTitle().isEmpty()) {
                    firebaseFirestore.collection("PRODUCTS")
                            .document(model.getProduct_id())
                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        model.setProductTitle(task.getResult().getString("product_title"));
                                        model.setProductImage(task.getResult().getString("product_img_1"));
                                        model.setProductPrice(task.getResult().getString("product_price"));


                                        if (horizental_scroll_product_modelList.indexOf(model) == horizental_scroll_product_modelList.size() - 1) {
                                            setGridData(Title,horizental_scroll_product_modelList);

                                                gridLayoutButton.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        gridLayoutButton.setOnClickListener(new View.OnClickListener() {
                                                            @Override
                                                            public void onClick(View view) {
                                                                ViewAllActivity.horizental_scroll_product_modelList = horizental_scroll_product_modelList;

                                                                Intent intent = new Intent(itemView.getContext(), ViewAllActivity.class);
                                                                intent.putExtra("layout_code", 1);
                                                                intent.putExtra("title", Title);
                                                                itemView.getContext().startActivity(intent);


                                                            }
                                                        });
                                                    }
                                                });
                                            }



                                    }
                                }
                            });
                }

            }

           setGridData(Title,horizental_scroll_product_modelList);

        }

        private void setGridData(String Title,List<horizental_scroll_product_model>horizental_scroll_product_modelList)
        {
            for (float x = 0; x < 4; x++) {
                ImageView productImage = gridLayoutView.getChildAt((int) x).findViewById(R.id.hhs_image);
                TextView productTitle = gridLayoutView.getChildAt((int) x).findViewById(R.id.hhs_product_title);
                TextView productDescription = gridLayoutView.getChildAt((int) x).findViewById(R.id.hhs_product_description);
                TextView productPrice = gridLayoutView.getChildAt((int) x).findViewById(R.id.hhs_product_price);
                //productImage.setImageResource(horizental_scroll_product_modelList.get(x).getProductImage());
                Glide.with(itemView.getContext()).load(horizental_scroll_product_modelList.get((int) x).getProductImage()).apply(new RequestOptions().placeholder(R.drawable.image)).into(productImage);
                productTitle.setText(horizental_scroll_product_modelList.get((int) x).getProductTitle());
                productDescription.setText(horizental_scroll_product_modelList.get((int) x).getProductDescription());
                productPrice.setText(horizental_scroll_product_modelList.get((int) x).getProductPrice());

                    float finalX = x;
                    gridLayoutView.getChildAt((int) x).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(itemView.getContext(), ProductDetailsActivity.class);
                            intent.putExtra("product_id", horizental_scroll_product_modelList.get((int) finalX).getProduct_id());
                            itemView.getContext().startActivity(intent);


                        }
                    });


            }

        }
    }







}
