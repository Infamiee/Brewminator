package pl.nzi.brewminator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.HardwarePropertiesManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


import pl.nzi.brewminator.calculator.IbuCalculator;
import pl.nzi.brewminator.model.HOP;

public class IbuCalculatorActivity extends AppCompatActivity {

    private List<EditText> weights,acids,times;
    private Button calculate;
    private TextView result;
    private EditText batchSizeEditText,originalGravityEditText;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ibu_calculator);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.cropped_logo);

        View logoView = getToolbarLogoIcon(toolbar);
        logoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(IbuCalculatorActivity.this,HomeActivity.class);
                startActivity(intent1);
                finish();

            }
        });

        weights = new ArrayList<>();
        acids = new ArrayList<>();
        times = new ArrayList<>();

        result = findViewById(R.id.iburesult);

        calculate = findViewById(R.id.ibucalculatorbutton);
        calculate.setOnClickListener(this::calculate);

        batchSizeEditText = findViewById(R.id.batchvolvalue);
        originalGravityEditText = findViewById(R.id.origgravityvalue);

        weights.add(findViewById(R.id.weight1));
        weights.add(findViewById(R.id.weight2));
        weights.add(findViewById(R.id.weight3));
        weights.add(findViewById(R.id.weight4));
        weights.add(findViewById(R.id.weight5));

        acids.add(findViewById(R.id.acid1));
        acids.add(findViewById(R.id.acid2));
        acids.add(findViewById(R.id.acid3));
        acids.add(findViewById(R.id.acid4));
        acids.add(findViewById(R.id.acid5));

        times.add(findViewById(R.id.time1));
        times.add(findViewById(R.id.time2));
        times.add(findViewById(R.id.time3));
        times.add(findViewById(R.id.time4));
        times.add(findViewById(R.id.time5));
    }

    private List<HOP> getHopList(){
        List<HOP> hops = new ArrayList<>();
        for (int i = 0; i<this.weights.size();i++){
            try {
                String weight = this.weights.get(i).getText().toString();
                String acid = this.acids.get(i).getText().toString();
                String time = this.times.get(i).getText().toString();
                if (weight.isEmpty() || acid.isEmpty() || time.isEmpty()){
                    continue;
                }
                hops.add(new HOP(weight,acid,time));
            }catch (NumberFormatException e){
                continue;
            }
        }
        return hops;
    }


    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void calculate(View view){
        try {
            double batchSize = Double.parseDouble(batchSizeEditText.getText().toString());
            double originalGravity = Double.parseDouble(originalGravityEditText.getText().toString());
            IbuCalculator ibuCalculator = new IbuCalculator(getHopList(),batchSize,originalGravity);
            result.setText(String.format("%.2f",ibuCalculator.calculate()));
        }catch (Exception e){
            e.printStackTrace();
            result.setText("Someting went wrong");
        }
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


    public static View getToolbarLogoIcon(Toolbar toolbar){
        //check if contentDescription previously was set
        boolean hadContentDescription = android.text.TextUtils.isEmpty(toolbar.getLogoDescription());
        String contentDescription = String.valueOf(!hadContentDescription ? toolbar.getLogoDescription() : "logoContentDescription");
        toolbar.setLogoDescription(contentDescription);
        ArrayList<View> potentialViews = new ArrayList<View>();
        //find the view based on it's content description, set programatically or with android:contentDescription
        toolbar.findViewsWithText(potentialViews,contentDescription, View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
        //Nav icon is always instantiated at this point because calling setLogoDescription ensures its existence
        View logoIcon = null;
        if(potentialViews.size() > 0){
            logoIcon = potentialViews.get(0);
        }
        //Clear content description if not previously present
        if(hadContentDescription)
            toolbar.setLogoDescription(null);
        return logoIcon;
    }

}