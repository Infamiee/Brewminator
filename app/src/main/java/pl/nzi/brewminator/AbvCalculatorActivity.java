package pl.nzi.brewminator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import pl.nzi.brewminator.calculator.AbvCalculator;
import pl.nzi.brewminator.exception.WrongGravitiesException;

public class AbvCalculatorActivity extends AppCompatActivity {
    TextView og;
    TextView fg;
    Button calculate;
    AbvCalculator abvCalculator = new AbvCalculator();
    TextView result;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abv_calculator);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.drawable.cropped_logo);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);



        og = (TextView) findViewById(R.id.og);
        fg = (TextView) findViewById(R.id.fg);
        calculate = (Button) findViewById(R.id.calculate);
        result = (TextView) findViewById(R.id.result);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                try {
                    double res = abvCalculator.calculate(Double.parseDouble(og.getText().toString()),Double.parseDouble(fg.getText().toString()));
                    result.setText(String.format("%.2f",res)+" %");
                }catch (WrongGravitiesException e){
                    result.setText(e.getMessage());
                }


            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        if (item.getItemId()==R.id.search_button){
            Intent intent = new Intent(this,SearchActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
