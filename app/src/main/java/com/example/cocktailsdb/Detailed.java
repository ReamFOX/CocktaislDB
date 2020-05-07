package com.example.cocktailsdb;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.cocktailsdb.model.RealmDrink;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;


public class Detailed extends AppCompatActivity {

    private TextView tvName, tvAlcoholic, tvGlass, tvInstruction;
    private ImageView ivThumbnail;
    private TableLayout tl;
    private int i = 1;
    Realm realm;
    private static final String TAG = "Realm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        tvName = findViewById(R.id.tvName);
        tvAlcoholic = findViewById(R.id.tvAlcoholic);
        tvGlass = findViewById(R.id.tvGlass);
        ivThumbnail = (ImageView) findViewById(R.id.ivThumbnail);
        tvInstruction = findViewById(R.id.tvInstruction);
        tl = findViewById(R.id.tl);

        Intent intent = getIntent();

        String name = intent.getExtras().getString("Name");
        String alcoholic = intent.getExtras().getString("Alcoholic");
        String glass = intent.getExtras().getString("Glass");
        String instruction = intent.getExtras().getString("Instruction");
        String ingredient, measure;

        while (intent.getExtras().getString("Ingredient" + i) != null ) {

                TableRow tr = new TableRow(this);
                TextView tv1 = new TextView(this);
                TextView tv2 = new TextView(this);
                tl.addView(tr);
                tr.addView(tv1);
                tv1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1f));
                tr.addView(tv2);
            tv2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1f));
                measure = intent.getExtras().getString("Measure" +i);
                ingredient =  i + "." + intent.getExtras().getString("Ingredient" + i);
                tv1.setText(ingredient);
                tv2.setText(measure);
                i++;
        }


        setTitle(name);

        tvName.setText(name);
        tvAlcoholic.setText(alcoholic);
        tvGlass.setText(glass);
        tvInstruction.setText(instruction);

        Glide
                .with(Detailed.this)
                .load(intent.getExtras().getString("Thumbnail"))
                .into(ivThumbnail);

        realm = Realm.getDefaultInstance();
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
}
