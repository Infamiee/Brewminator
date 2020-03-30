package pl.nzi.brewminator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.digidemic.unitof.V;

import java.io.IOException;
import java.util.Objects;

import pl.nzi.brewminator.calculator.AbvCalculator;


public class HomeActivity extends AppCompatActivity {
    Button button;
    Button button1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo(R.drawable.cropped_logo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        button = (Button) findViewById(R.id.butt1);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AbvCalculatorActivity.class);
            startActivity(intent);

        });
        button1 = (Button) findViewById(R.id.butt2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, IbuCalculatorActivity.class);
                startActivity(intent);

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        System.out.println("xd2");
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        System.out.println("xd1");
        if (item.getItemId() == R.id.search_button){
            System.out.println("xd");
            Toast.makeText(getApplicationContext(),"xddddd",Toast.LENGTH_LONG).show();
        }

        return true;
    }
}
