package com.example.cocktailsdb;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class searchRes extends AppCompatActivity implements View.OnClickListener {

    String searchInput = "";
    EditText eText;
    ArrayList<Drinks> lstCocktail = null;
    RecyclerView rv;
    Button btnSearch;
    ApiService apiService;
    rvAdapter adapter;
    private static final String TAG = "Realm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_res);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle("Search");

        eText = findViewById(R.id.eText);
        rv = findViewById(R.id.rv);
        btnSearch = findViewById(R.id.btnSearch);
        rv.setLayoutManager(new GridLayoutManager(this, 2));
        btnSearch.setOnClickListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSearch:
                searchInput = eText.getText().toString().trim();
                if (searchInput.length() != 0) {
                    apiService = RetrofitClient.getClient().create(ApiService.class);
                    Call<JSONResponse> call = apiService.getCocktail(searchInput);
                    call.enqueue(new Callback<JSONResponse>() {
                        @Override
                        public void onResponse(@NonNull Call<JSONResponse> call, @NonNull Response<JSONResponse> response) {
                            JSONResponse jsonResponse = response.body();
                            if (response.isSuccessful()) {
                                try {
                                    if (response.body() != null) {
                                        lstCocktail = new ArrayList<>(Arrays.asList(jsonResponse.getDrinks()));
                                        setAdapter();
                                    }
                                } catch (NullPointerException e) {
                                    e.printStackTrace();
                                    Toast.makeText(searchRes.this, "Cocktails not found", Toast.LENGTH_SHORT).show();
                                    rv.setAdapter(null);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<JSONResponse> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                } else {
                    Toast.makeText(this, "Enter text to start search", Toast.LENGTH_SHORT).show();
                }
                    break;
                }

        }

        private void setAdapter () {
            adapter = new rvAdapter(lstCocktail, getApplicationContext());
            rv.setAdapter(adapter);
        }
    }




