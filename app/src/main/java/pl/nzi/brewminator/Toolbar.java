package pl.nzi.brewminator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Toolbar extends AppCompatActivity {
    private Button search_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        search_button = (Button) findViewById(R.id.searchButton);

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Toolbar.this,SearchActivity.class);
                startActivity(intent);
            }
        });

    }

}
