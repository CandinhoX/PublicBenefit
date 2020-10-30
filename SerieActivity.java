package br.com.candinho.publicbenefit.activities;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import br.com.candinho.publicbenefit.R;

public class SerieActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serie);


        getSupportActionBar().hide();

        String name  = getIntent().getExtras().getString("serie_name");
        String category = getIntent().getExtras().getString("serie_category");
        String ano = getIntent().getExtras().getString("serie_ano");
        String description = getIntent().getExtras().getString("serie_description");
        String season = getIntent().getExtras().getString("serie_season");
        String image_url = getIntent().getExtras().getString("serie_img");

// ini views

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar_id);
        collapsingToolbarLayout.setTitleEnabled(true);

        TextView tv_name = findViewById(R.id.aa_serie_name);
        TextView tv_categorie = findViewById(R.id.aa_categorie);

        TextView tv_ano  = findViewById(R.id.aa_ano) ;
        TextView tv_description = findViewById(R.id.description);
        ImageView img = findViewById(R.id.aa_thumbnail);

// setting values to each view

        tv_name.setText(name);
        tv_categorie.setText(category);
        tv_description.setText(description);
        tv_ano.setText(ano);


        collapsingToolbarLayout.setTitle(name);


        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);


// set image using Glide
        Glide.with(this).load(image_url).apply(requestOptions).into(img);




    }
}
