package pl.nzi.brewminator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
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
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abv_calculator);
        og = (TextView) findViewById(R.id.og);
        fg = (TextView) findViewById(R.id.fg);
        calculate = (Button) findViewById(R.id.calculate);
        result = (TextView) findViewById(R.id.result);
        back = (Button) findViewById(R.id.back_arrow);
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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
