package br.com.candinho.publicbenefit.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import br.com.candinho.publicbenefit.R;
import br.com.candinho.publicbenefit.model.Model;


public class MainActivity extends AppCompatActivity {

    ListView serieNameLV;
    SearchView serieNameSearchSV;
    CustomAdapter myadapter;
    private final String JSON_URL = "https://seriejson.firebaseio.com/seriebiblioteca.json";
    private JsonArrayRequest request;
    private RequestQueue resquestQueue;
    List<Model> lstModel;
    private AdView mAdView;
    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        lstModel = new ArrayList<>();
        serieNameLV = findViewById(R.id.recyclerviewid);
        serieNameSearchSV = findViewById(R.id.searchSerie);
        jsonrequest();

        MobileAds.initialize(this, "ca-app-pub-3940256099942544/6300978111");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        serieNameSearchSV.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myadapter.getFilter().filter(newText);
                return false;
            }

           }
        );

    }


    private void jsonrequest() {

        request = new JsonArrayRequest(JSON_URL, new com.android.volley.Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;

                for (int i = 0 ; i < response.length(); i++) {

                    try {
                        jsonObject = response.getJSONObject(i);
                        Model model = new Model();
                        model.setName(jsonObject.getString("name"));
                        model.setAno(jsonObject.getString("ano"));
                        model.setCategorie(jsonObject.getString("categorie"));
                        model.setImage_url(jsonObject.getString("img"));
                        model.setDescription(jsonObject.getString("description"));
                        lstModel.add(model);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                setuprecyclerview(lstModel);

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        resquestQueue = Volley.newRequestQueue(MainActivity.this);
        resquestQueue.add(request);
    }

    private void setuprecyclerview(List<Model> lstModel) {

        myadapter = new CustomAdapter(getApplicationContext(),R.layout.serie_row_item, (ArrayList<Model>) lstModel);
        serieNameLV.setAdapter(myadapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_share:

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBodyText = "Hello this is my new App!" + "\nhttps://play.google.com/store/apps/details?id=br.com.candinho.publicbenefit";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"https://play.google.com/store/apps/details?id=br.com.candinho.publicbenefit");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
                startActivity(Intent.createChooser(sharingIntent, "Shearing Option"));
                return true;

            case R.id.report_problem:

                Intent sharinProblem = new Intent(android.content.Intent.ACTION_SEND);
                sharinProblem.setType("text/email");
                String shareText = "I found a bug";
                sharinProblem.putExtra(Intent.EXTRA_EMAIL,new String[] {"publicbenefit.net@gmail.com"});
                startActivity(Intent.createChooser(sharinProblem, "Shearing Option"));
                return true;

            default:

                return super.onOptionsItemSelected(item);
        }

    }

    class CustomAdapter extends ArrayAdapter<Model> {
        public ArrayList<Model> originalList;
        private ArrayList<Model> series;
        Context context;
        int resource;
        RequestOptions option;

        public CustomAdapter(Context context, int resource, ArrayList<Model> series) {
            super(context, resource, series);
            this.series = series;
            this.context = context;
            this.resource = resource;
            originalList = series;
            option =  new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);
        }

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.toast_layout));


        @Override
        public int getCount() {
            return series.size();
        }

        @Override
        public Model getItem(int position) {
            return series.get(position);
        }

        @Override
        public long getItemId(int position) {
            return series.indexOf(getItem(position));
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            if(convertView == null){
                LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.serie_row_item, null, true);
            }



            ImageView imageView = (ImageView)convertView.findViewById(R.id.thumbnail);
            Glide.with(context).load(series.get(position).getImage_url()).apply(option).into(imageView);

            TextView serie_name = (TextView)convertView.findViewById(R.id.serie_name);
            TextView serie_ano = (TextView)convertView.findViewById(R.id.ano_serie);
            TextView serie_categoria = (TextView)convertView.findViewById(R.id.categorie);

            serie_name.setText(series.get(position).getName());
            serie_ano.setText(series.get(position).getAno());
            serie_categoria.setText(series.get(position).getCategorie());

            serieNameLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();


                    Intent i = new Intent(MainActivity.this, SerieActivity.class);
                    i.putExtra("serie_name",series.get(position).getName());
                    i.putExtra("serie_category", series.get(position).getCategorie());
                    i.putExtra("serie_ano", series.get(position).getAno());
                    i.putExtra("serie_description", series.get(position).getDescription());
                    i.putExtra("serie_img", series.get(position).getImage_url());

                    startActivity(i);

                }
            });


            return convertView;

        }



        @Override
        public Filter getFilter() {

            return new Filter() {

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    // TODO Auto-generated method stub
                    if (results.count == 0) {
                        notifyDataSetInvalidated();
                    }else{
                        series = (ArrayList<Model>) results.values;
                        notifyDataSetChanged();
                    }
                }

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    // TODO Auto-generated method stub
                    FilterResults results = new FilterResults();

                    if (constraint == null || constraint.length() == 0) {
                        results.values = originalList;
                        results.count = originalList.size();
                    }else{
                        ArrayList<Model> filter_items = new ArrayList<>();
                        for (Model item : originalList) {
                            if (item.getName().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                                filter_items.add(item);
                            }
                        }
                        results.values =  filter_items ;
                        results.count = filter_items.size();
                    }
                    return results;
                }
            };
        }

    }


}
