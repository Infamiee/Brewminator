package pl.nzi.brewminator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.HardwarePropertiesManager;
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
    private Button calculate,back;
    private TextView result;
    private EditText batchSizeEditText,originalGravityEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ibu_calculator);

        back = findViewById(R.id.backfromibu);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

}