package com.sravan.codetestapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.HashSet;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private DataAdapter dataAdapter;
    private Set<Hit> hits = new HashSet<>();
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;

    int currentPage=1;

    int nopages;
    LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mProgressBar=(ProgressBar)findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerView=(RecyclerView)findViewById(R.id.cars_list);
        mRecyclerView.setLayoutManager(layoutManager);

        hits = new HashSet<>();

        parseJson();
    }

    private void parseJson() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://hn.algolia.com/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        System.out.println("LOG: Current page new "+currentPage);
        Call<Example> call1 = request.getJson("story", currentPage);
        call1.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                mProgressBar.setVisibility(View.GONE);
                System.out.println("LOG:: Response:: " + response.body());
                if (response.isSuccessful() && response.body() != null) {
                    System.out.println("LOG: Page Num:: "+response.body().getPage());
                    hits.addAll(response.body().getHits());
                    dataAdapter = new DataAdapter(hits, MainActivity.this);
                    mRecyclerView.setAdapter(dataAdapter);
                    dataAdapter.notifyDataSetChanged();

                    nopages = response.body().getNbPages();

                    mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                            super.onScrollStateChanged(recyclerView, newState);
                        }

                        @Override
                        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            LinearLayoutManager layoutManager=LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
                            int totalItemCount = layoutManager.getItemCount();
                            int lastVisible = layoutManager.findLastVisibleItemPosition();

                            boolean endHasBeenReached = lastVisible + 1 >= totalItemCount;
                            if (totalItemCount > 1 && endHasBeenReached) {
                                //you have reached to the bottom of your recycler view
                                System.out.println("LOG: End has reached:: ");
                                currentPage = currentPage + 1;
                                System.out.println("LOG: Current page "+currentPage);
                                parseJson();
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                mProgressBar.setVisibility(View.GONE);
                System.out.println("LOG:: Response Body:: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Oops! Something went wrong!", Toast.LENGTH_SHORT).show();
            }

        });
    }
}