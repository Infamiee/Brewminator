package pl.nzi.brewminator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.digidemic.unitof.G;
import com.google.gson.Gson;

import org.json.simple.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.nzi.brewminator.model.Comment;
import pl.nzi.brewminator.model.Comments;
import pl.nzi.brewminator.model.Note;
import pl.nzi.brewminator.service.ApiConnector;

public class FeedbackActivity extends AppCompatActivity {
    private static final String TAG = "Feedback";

    private int recipeId;
    private Comments comments;
    private String macAddress;
    private View dialogView;
    private AnimationDrawable loadingAnimation;
    private AlertDialog alertDialog;
    boolean alreadyCommented;
    private ApiConnector connector;
    List<String> names;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_layout);
        setupToolbar();
        Intent intent = getIntent();
        recipeId = intent.getIntExtra("id", -1);
        startLoadingAnim();
        names = new ArrayList<>();
        WifiManager manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        macAddress = info.getMacAddress();
        connector = new ApiConnector(this);
        new LoadComments().execute(recipeId);

    }

    private void startLoadingAnim() {
        ImageView imageView = findViewById(R.id.loading);
        imageView.setBackgroundResource(R.drawable.loading);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();

    }


    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setIcon(R.drawable.cropped_logo);

        View logoView = getToolbarLogoIcon(toolbar);
        logoView.setOnClickListener(v -> {
            Intent intent1 = new Intent(FeedbackActivity.this, HomeActivity.class);
            startActivity(intent1);
            finish();
        });
    }

    private void deleteComment(Comment comment) {

        new DeleteComment(comment).execute();
    }

    private void editComment(Comment comment) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        dialogView = getLayoutInflater().inflate(R.layout.add_comment_dialog, null);
        ImageView anim = dialogView.findViewById(R.id.anim);

        anim.setBackgroundResource(R.drawable.loading);
        loadingAnimation = (AnimationDrawable) anim.getBackground();
        LinearLayout layout = dialogView.findViewById(R.id.name_layout);
        layout.setVisibility(View.GONE);

        EditText commentEditText = dialogView.findViewById(R.id.comment_edittext);

        builder.setView(dialogView);
        alertDialog = builder.create();
        alertDialog.show();
        ImageButton addButton = dialogView.findViewById(R.id.add_button);
        addButton.setOnClickListener(v -> {
            String commenttext = commentEditText.getText().toString();
            if (commenttext.trim().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Couldn't be empty", Toast.LENGTH_LONG).show();
                return;
            }

            new EditComment(comment).execute(commenttext);
        });
        ImageButton imageButton = dialogView.findViewById(R.id.close_button);
        imageButton.setOnClickListener(v -> alertDialog.dismiss());

    }

    private void setupView() {
        setContentView(R.layout.activity_feedback);
        new GetNotes().execute();
        setupToolbar();
        ImageButton addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(v -> addCommentDialog());
    }

    private void setRating(float rating) {

        TextView r = findViewById(R.id.note_textview);
        r.setText(String.format("%.1f",rating));
        ImageView image = findViewById(R.id.cropped_finnish);
        if (rating == 0) {
            image.setImageResource(0);
            return;
        }
        Bitmap btm = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.finish_load_150px);
        int height = btm.getHeight();
        int h = Math.round((float) height * rating / 10);
        Log.d(TAG, "setRating: "+rating);
        Bitmap newBitmap = Bitmap.createBitmap(btm, 0, height - h, btm.getWidth(), h, null, false);


        image.setImageBitmap(newBitmap);
    }

    private void addCommentDialog() {
        if (alreadyCommented) {
            Toast.makeText(this, "You already commented this recipe", Toast.LENGTH_LONG).show();
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        dialogView = getLayoutInflater().inflate(R.layout.add_comment_dialog, null);
        ImageView anim = dialogView.findViewById(R.id.anim);

        anim.setBackgroundResource(R.drawable.loading);
        loadingAnimation = (AnimationDrawable) anim.getBackground();
        EditText nameEditText = dialogView.findViewById(R.id.name_edittext);

        EditText commentEditText = dialogView.findViewById(R.id.comment_edittext);
        Spinner spinner = dialogView.findViewById(R.id.spinner);

        builder.setView(dialogView);
        alertDialog = builder.create();
        alertDialog.show();
        ImageButton addButton = dialogView.findViewById(R.id.add_button);
        addButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString();
            String comment = commentEditText.getText().toString();
            String selected = spinner.getSelectedItem().toString();
            if (!selected.equals("----")) {
                new SendNote().execute(Integer.parseInt(selected));
            }
            if (name.trim().isEmpty() || comment.trim().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Couldn't be empty", Toast.LENGTH_LONG).show();
                return;
            }
            if (names.contains(name.trim())) {
                Toast.makeText(this, "Someone else picked that name", Toast.LENGTH_LONG).show();
                return;
            }
            new AddComment().execute(name, comment);
        });
        ImageButton imageButton = dialogView.findViewById(R.id.close_button);
        imageButton.setOnClickListener(v -> alertDialog.dismiss());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        if (item.getItemId() == R.id.search_button) {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static View getToolbarLogoIcon(Toolbar toolbar) {
        //check if contentDescription previously was set
        boolean hadContentDescription = android.text.TextUtils.isEmpty(toolbar.getLogoDescription());
        String contentDescription = String.valueOf(!hadContentDescription ? toolbar.getLogoDescription() : "logoContentDescription");
        toolbar.setLogoDescription(contentDescription);
        ArrayList<View> potentialViews = new ArrayList<View>();
        //find the view based on it's content description, set programatically or with android:contentDescription
        toolbar.findViewsWithText(potentialViews, contentDescription, View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
        //Nav icon is always instantiated at this point because calling setLogoDescription ensures its existence
        View logoIcon = null;
        if (potentialViews.size() > 0) {
            logoIcon = potentialViews.get(0);
        }
        //Clear content description if not previously present
        if (hadContentDescription)
            toolbar.setLogoDescription(null);
        return logoIcon;
    }

    class LoadComments extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer... integers) {

            int id = integers[0];


            connector.get("/comment/" + String.valueOf(recipeId), null, Request.Method.GET, response -> {
                setupView();

                Gson gson = new Gson();
                try {
                    comments = gson.fromJson(response, Comments.class);
                    List<Comment> commentList = comments.getComments();
                    for (Comment comment : commentList) {
                        if (comment.getMacAddress().equals(macAddress)) {
                            alreadyCommented = true;
                        }
                        names.add(comment.getName().trim());
                        setupCommentView(comment);
                    }

                } catch (Exception e) {
                    Log.d(TAG, "doInBackground: " + e.getMessage());
                }
            }, error -> {
                Log.d(TAG, "doInBackground: error " + error.getMessage());
            });

            return null;
        }

        private void setupCommentView(Comment comment) {
            LinearLayout layout = findViewById(R.id.comment_layout);
            View child = getLayoutInflater().inflate(R.layout.comment_view, null);
            if (macAddress.equals(comment.getMacAddress())) {
                ImageButton edit = child.findViewById(R.id.edit_button);
                edit.setVisibility(View.VISIBLE);
                edit.setOnClickListener(v -> {
                    editComment(comment);
                });
                ImageButton delete = child.findViewById(R.id.delete_button);
                delete.setVisibility(View.VISIBLE);
                delete.setOnClickListener(v -> deleteComment(comment));
            }
            TextView name = child.findViewById(R.id.name_textview);
            name.setText(comment.getName());
            Log.d(TAG, "setupCommentView: " + comment.getName());
            TextView commentView = child.findViewById(R.id.comment_textview);
            commentView.setText(comment.getComment());
            comment.setView(child);
            layout.addView(child);
        }
    }

    class GetNotes extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... floats) {
            connector.get("/note/" + recipeId, null, Request.Method.GET, response ->
                    {


                        Gson gson = new Gson();
                        Note note = gson.fromJson(response, Note.class);
                        Log.d(TAG, "doInBackground: "+note.getNote());
                        setRating(note.getNote());
                    },

                    error -> {
                        Toast.makeText(FeedbackActivity.this, "Couldn't load note", Toast.LENGTH_LONG).show();
                    });
            return null;
        }

    }

    class SendNote extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer... integers) {
            int note = integers[0];
            JSONObject body = new JSONObject();
            body.put("note", note);
            body.put("mac", macAddress);
            body.put("recipe_id", recipeId);
            connector.get("/note", body, Request.Method.POST, response ->
                    {
                        new GetNotes().execute();
                    },

                    error -> {
                        Toast.makeText(FeedbackActivity.this, "Couldn't send note", Toast.LENGTH_LONG).show();
                    });
            return null;
        }

    }

    class AddComment extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {


            runOnUiThread(() ->
            {
                ImageView anim = dialogView.findViewById(R.id.anim);
                anim.setVisibility(View.VISIBLE);
                loadingAnimation.start();
            });

            String name = strings[0];
            String comment = strings[1];
            JSONObject bodyObj = new JSONObject();
            Log.d(TAG, "doInBackground:");
            bodyObj.put("name", name);
            bodyObj.put("comment", comment);
            bodyObj.put("recipe_id", recipeId);
            bodyObj.put("mac", macAddress);


            connector.get("/comment", bodyObj, Request.Method.POST, response -> {
                runOnUiThread(() ->
                {
                    ImageView anim = dialogView.findViewById(R.id.anim);
                    loadingAnimation.stop();
                    anim.setVisibility(View.INVISIBLE);
                    alertDialog.dismiss();
                    LinearLayout layout = findViewById(R.id.comment_layout);
                    layout.removeAllViews();
                    new LoadComments().execute(recipeId);
                });


            }, error -> {


                Toast.makeText(FeedbackActivity.this, "Couldn't add comment", Toast.LENGTH_LONG).show();
            });


            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            runOnUiThread(() ->
            {
                ImageView anim = dialogView.findViewById(R.id.anim);
                loadingAnimation.stop();
                anim.setVisibility(View.INVISIBLE);
                alertDialog.dismiss();
            });
        }
    }

    class DeleteComment extends AsyncTask<Void, Void, Void> {
        private Comment comment;


        public DeleteComment(Comment comment) {
            this.comment = comment;

        }

        @Override
        protected Void doInBackground(Void... voids) {
            JSONObject bodyObj = new JSONObject();
            Log.d(TAG, "doInBackground:" + comment.getId() + "   " + macAddress);
            bodyObj.put("comment_id", comment.getId());
            bodyObj.put("mac", macAddress);


            connector.get("/comment", bodyObj, Request.Method.PATCH, response -> {
                LinearLayout layout = findViewById(R.id.comment_layout);
                layout.removeView(comment.getView());
                Toast.makeText(FeedbackActivity.this, "Deleted", Toast.LENGTH_LONG).show();
                alreadyCommented = false;
                new DeleteNote().execute();
            }, error -> {


                Toast.makeText(FeedbackActivity.this, "Couldn't delete comment", Toast.LENGTH_LONG).show();
            });
            return null;
        }


    }

    class DeleteNote extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            JSONObject body = new JSONObject();
            body.put("recipe_id", recipeId);
            body.put("mac", macAddress);
            connector.get("/note", body, Request.Method.PATCH, response -> {
                        setRating(getNote(response).getNote());
                    },
                    error -> {
                        Toast.makeText(FeedbackActivity.this, "Couldn't delete note", Toast.LENGTH_LONG).show();
                    });
            return null;
        }
    }

    class EditComment extends AsyncTask<String, Void, Void> {
        private Comment comment;


        public EditComment(Comment comment) {
            this.comment = comment;

        }

        @Override
        protected Void doInBackground(String... strings) {

            runOnUiThread(() ->
            {
                ImageView anim = dialogView.findViewById(R.id.anim);
                anim.setVisibility(View.VISIBLE);
                loadingAnimation.start();
            });

            String commenttext = strings[0];
            JSONObject bodyObj = new JSONObject();
            Log.d(TAG, "doInBackground:" + comment.getId() + "   " + macAddress);
            bodyObj.put("comment", commenttext);
            bodyObj.put("comment_id", comment.getId());
            bodyObj.put("mac", macAddress);

            connector.get("/comment/edit", bodyObj, Request.Method.PATCH, response -> {
                runOnUiThread(() ->
                {
                    ImageView anim = dialogView.findViewById(R.id.anim);
                    loadingAnimation.stop();
                    anim.setVisibility(View.INVISIBLE);

                    Spinner spinner = alertDialog.findViewById(R.id.spinner);
                    String note = spinner.getSelectedItem().toString();
                    if (!note.equals("----")) {
                        new EditNote().execute(Integer.parseInt(note));

                    }
                    alertDialog.dismiss();
                    TextView view = this.comment.getView().findViewById(R.id.comment_textview);
                    view.setText(commenttext);


                });

            }, error -> {
                runOnUiThread(() ->
                {
                    ImageView anim = dialogView.findViewById(R.id.anim);
                    loadingAnimation.stop();
                    anim.setVisibility(View.INVISIBLE);
                });

                Toast.makeText(FeedbackActivity.this, "Couldn't edit comment", Toast.LENGTH_LONG).show();
            });


            return null;
        }

    }

    class EditNote extends AsyncTask<Integer, Void, Void> {


        @Override
        protected Void doInBackground(Integer... integers) {
            int note = integers[0];
            JSONObject body = new JSONObject();
            body.put("recipe_id", recipeId);
            body.put("mac", macAddress);
            body.put("note", note);
            connector.get("/note", body, Request.Method.PUT, response -> {
                setRating(getNote(response).getNote());
            }, error ->
                    Toast.makeText(FeedbackActivity.this, "Couldn't edit note", Toast.LENGTH_LONG).show());
            return null;
        }

    }

    private Note getNote(String response){
        Gson gson = new Gson();
        Note note = gson.fromJson(response,Note.class);
        return note;
    }


}
