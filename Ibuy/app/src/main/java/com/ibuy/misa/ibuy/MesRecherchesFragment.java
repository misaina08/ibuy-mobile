package com.ibuy.misa.ibuy;


import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import async.client.MesRecherchesAsync;


/**
 * A simple {@link Fragment} subclass.
 */
public class MesRecherchesFragment extends Fragment {
    private Paint paint = new Paint();
    public SwipeRefreshLayout swipeRefresh;
    RecyclerView recyclerView;

    public MesRecherchesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mes_recherches, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) getView().findViewById(R.id.recListRecherche);
        swipeRefresh = (SwipeRefreshLayout) getView().findViewById(R.id.swipeRefresh);

        swipeRefresh.setRefreshing(true);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                init();
            }
        });
        init();
        initSwipe();
    }

    public void init() {
        MesRecherchesAsync mesRecherchesAsync = new MesRecherchesAsync();
        mesRecherchesAsync.setActivity(this.getActivity());
        mesRecherchesAsync.execute();
        swipeRefresh.setRefreshing(false);
    }

    private void initSwipe() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.LEFT) {
                    try {
                        System.out.println("left");
                        System.out.println("huhu"+viewHolder.getAdapterPosition());
//                        CommandeProduitLibelleDao commandeProduitLibelleDao = new CommandeProduitLibelleDao(cf.getActivity());
//                        commandeProduitLibelleDao.enlever(listePdtPanier.get(viewHolder.getAdapterPosition()).getIdproduit());
//                        initData();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else {
                    System.out.println("right");
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if (dX > 0) {
                        paint.setColor(Color.parseColor("#388E3C"));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
                        c.drawRect(background, paint);
                        Drawable d = getResources().getDrawable(R.drawable.ic_menu_send);
                        icon = Bitmap.createBitmap((int)width, (int)height, Bitmap.Config.ARGB_8888);
                        c = new Canvas(icon);
                        d.setBounds((int) itemView.getRight() - 2*(int)width , (int) itemView.getTop() + (int)(int)width, (int) itemView.getRight() - (int)width, (int)itemView.getBottom() - (int)width);
                        d.draw(c);
                    } else {
                        paint.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, paint);
                        Drawable d = getResources().getDrawable(R.drawable.ic_menu_send);
                        icon = Bitmap.createBitmap((int)width, (int)height, Bitmap.Config.ARGB_8888);
                        c = new Canvas(icon);
                        d.setBounds((int) itemView.getRight() - 2*(int)width , (int) itemView.getTop() + (int)(int)width, (int) itemView.getRight() - (int)width, (int)itemView.getBottom() - (int)width);
                        d.draw(c);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

}
