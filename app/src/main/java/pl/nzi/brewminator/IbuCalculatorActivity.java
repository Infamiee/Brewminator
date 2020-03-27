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
import pl.nzi.brewminator.model.Hop;

public class IbuCalculatorActivity extends AppCompatActivity {

    private List<EditText> weights,acids,times;
    Button calculate;
    private TextView result;
    private EditText batchSizeEditText,originalGravityEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ibu_calculator);

        result = findViewById(R.id.iburesult);
        calculate = findViewById(R.id.ibucalculatorbutton);

        calculate.setOnClickListener(this::calculate);

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

    private List<Hop> getHopList(){
        List<Hop> hops = new ArrayList<>();
        for (int i = 0; i<this.weights.size();i++){
            try {
                double weight = Double.parseDouble(this.weights.get(i).getText().toString());
                double acid = Double.parseDouble(this.acids.get(i).getText().toString());
                int time = Integer.parseInt(this.times.get(i).getText().toString());

                hops.add(new Hop(weight,acid,time));
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
            result.setText(String.format("%.3f",ibuCalculator.calculate()));
        }catch (Exception e){
            result.setText("Someting went wrong");
        }
    }

}
