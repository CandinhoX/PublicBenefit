package br.com.candinho.publicbenefit.activities;

import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import br.com.candinho.publicbenefit.R;

public class ActivitiyHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitiy_home);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mySuperIntent = new Intent(ActivitiyHome.this, MainActivity.class);
                startActivity(mySuperIntent);
          
                finish();
            }
        }, 3000);
    }
}
