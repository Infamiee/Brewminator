package pl.nzi.brewminator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import pl.nzi.brewminator.model.Recipe;

public class RecipeView extends AppCompatActivity {
    private Recipe recipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id",0);


        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.168.1.18:5000/recipe?id="+String.valueOf(id);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    System.out.println(response);
                }, Throwable::printStackTrace);

        queue.add(stringRequest);
    }
}
