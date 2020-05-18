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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.nzi.brewminator.exception.NoCommentException;
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
    private List<String> names;
    private float current_note;


    float summedNote;


    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        setupToolbar();
        ImageButton addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(v -> addCommentDialog(false));
        Intent intent = getIntent();
        recipeId = intent.getIntExtra("id", -1);
        String recipeName = intent.getStringExtra("name");
        names = new ArrayList<>();
        macAddress = getMacAddr();
        connector = new ApiConnector(this);
        getComments();
        setRecipeName(recipeName);


    }

    private void setRecipeName(String name) {
        TextView view = findViewById(R.id.name_textview);
        view.setText(name);

    }
    private String getMacAddr() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(Integer.toHexString(b & 0xFF) + ":");
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            //handle exception
        }
        return "";
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





    @SuppressLint("ClickableViewAccessibility")
    private void addCommentDialog(boolean isEdit) {
        if (alreadyCommented && !isEdit) {
            Toast.makeText(this, "You already commented this recipe", Toast.LENGTH_LONG).show();
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        dialogView = getLayoutInflater().inflate(R.layout.add_comment_dialog, null);
        ImageView anim = dialogView.findViewById(R.id.anim);
        anim.setBackgroundResource(R.drawable.loading);
        loadingAnimation = (AnimationDrawable) anim.getBackground();
        EditText nameEditText = dialogView.findViewById(R.id.name_edittext);
        if (isEdit) {
            dialogView.findViewById(R.id.name_layout).setVisibility(View.GONE);
        }

        EditText commentEditText = dialogView.findViewById(R.id.comment_edittext);
        RelativeLayout noteLayout = dialogView.findViewById(R.id.note_slider);
        noteLayout.setOnTouchListener(touchHandler);

        builder.setView(dialogView);
        alertDialog = builder.create();
        alertDialog.show();
        noteLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                noteLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                current_note=6f;
                cutImage(noteLayout, (float) (noteLayout.getWidth() * 0.6));
            }
        });

        ImageButton addButton = dialogView.findViewById(R.id.add_button);
        addButton.setOnClickListener(v -> {
            String comment = commentEditText.getText().toString();
            if (comment.trim().isEmpty()) {
                Toast.makeText(this, "Comment cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!isEdit) {
                String name = nameEditText.getText().toString();

                if (name.trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Name", Toast.LENGTH_LONG).show();
                    return;
                }
                if (names.contains(name.trim())) {
                    Toast.makeText(this, "Someone else picked that name", Toast.LENGTH_LONG).show();
                    return;
                }
                addComment(comment,name,current_note);

            }
            else {

            }
            setRecipeNote();
            alertDialog.dismiss();
        });
        ImageButton imageButton = dialogView.findViewById(R.id.close_button);
        imageButton.setOnClickListener(v -> alertDialog.dismiss());

    }
    @SuppressLint("ClickableViewAccessibility")
    private void editCommentDialog(Comment comment){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        dialogView = getLayoutInflater().inflate(R.layout.add_comment_dialog, null);
        ImageView anim = dialogView.findViewById(R.id.anim);
        anim.setBackgroundResource(R.drawable.loading);
        loadingAnimation = (AnimationDrawable) anim.getBackground();
        dialogView.findViewById(R.id.name_layout).setVisibility(View.GONE);
        EditText commentEditText = dialogView.findViewById(R.id.comment_edittext);
        commentEditText.setText(comment.getComment());
        RelativeLayout noteLayout = dialogView.findViewById(R.id.note_slider);
        noteLayout.setOnTouchListener(touchHandler);
        builder.setView(dialogView);
        alertDialog = builder.create();
        alertDialog.show();
        noteLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                noteLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                current_note=comment.getNote().floatValue();
                cutImage(noteLayout, (float) (noteLayout.getWidth() * current_note/10));
            }
        });

        ImageButton addButton = dialogView.findViewById(R.id.add_button);
        addButton.setOnClickListener(v -> {
            String c= commentEditText.getText().toString();
            if (c.trim().isEmpty()) {
                Toast.makeText(this, "Comment cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            editComment(comment,current_note,c);
            setRecipeNote();
            alertDialog.dismiss();
        });
        ImageButton imageButton = dialogView.findViewById(R.id.close_button);
        imageButton.setOnClickListener(v -> alertDialog.dismiss());
    }


    private void cutImage(RelativeLayout view, float x) {
        float filteredX = x;
        ImageView img = view.findViewById(R.id.note_img);
        if (filteredX > view.getWidth()) {
            filteredX = view.getWidth();
        } else if (filteredX < 0.5) {
            img.setVisibility(View.INVISIBLE);
            filteredX = 0;
            float n = (filteredX / view.getWidth()) * 10;
            setNote(n);
            return;
        }
        float n = (filteredX / view.getWidth()) * 10;
        setNote(n);
        current_note = n;
        img.setVisibility(View.VISIBLE);
        Log.d(TAG, "cutImage: " + filteredX);
        Bitmap btm = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.note_gradient);
        Bitmap newBitmap = Bitmap.createBitmap(btm, 0, 0, Math.round(filteredX), view.getHeight(), null, false);

        img.setImageBitmap(newBitmap);


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


    private void setupCommentView(Comment comment) {
        LinearLayout layout = findViewById(R.id.comment_layout);
        View child = getLayoutInflater().inflate(R.layout.comment_view, null);
        Log.d(TAG, "setupCommentView: " + comment.getMacAddress());
        if (macAddress.equals(comment.getMacAddress())) {
            ImageButton edit = child.findViewById(R.id.edit_button);
            edit.setVisibility(View.VISIBLE);
            edit.setOnClickListener(v -> {
                editCommentDialog(comment);
            });
            ImageButton delete = child.findViewById(R.id.delete_button);
            delete.setVisibility(View.VISIBLE);
            delete.setOnClickListener(v -> deleteComment(comment));

        }
        TextView name = child.findViewById(R.id.name_textview);
        name.setText(comment.getName());
        Log.d(TAG, "setupCommentView: " + comment.getName());
        TextView noteTextView = child.findViewById(R.id.note_textview);
        noteTextView.setText(String.format("%.1f", comment.getNote()));
        TextView commentView = child.findViewById(R.id.comment_textview);
        commentView.setText(comment.getComment());
        comment.setView(child);
        layout.addView(child);
    }


    @SuppressLint("ClickableViewAccessibility")
    private View.OnTouchListener touchHandler = (v, event) -> {
        float x = event.getX();
        float y = event.getY();
        Log.d(TAG, "onTouch: " + x + "    " + y);
        cutImage((RelativeLayout) v, x);

        return true;
    };

    private void setRecipeNote(){
        TextView noteView = findViewById(R.id.note_textview);
        try {
            float n = calculateSummedNote();
            noteView.setText(String.format("%.1f",n));
            setNoteImage();
        }catch (NoCommentException e){
            ImageView img = findViewById(R.id.cropped_finnish);
            img.setVisibility(View.INVISIBLE);
            noteView.setText("not rated");
        }


    }

    private void setNoteImage() {
        float percent = summedNote / 10;
        ImageView img = findViewById(R.id.cropped_finnish);
        if (summedNote==0) {
            img.setVisibility(View.INVISIBLE);
            return;
        }

        Bitmap btm = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.finish_load_150px);
        Log.d(TAG, "setNoteImage: " + summedNote);
        Bitmap newBitmap = Bitmap.createBitmap(btm, 0, Math.round((btm.getHeight() * (1 - percent))), btm.getWidth(), Math.round(btm.getHeight() * percent), null, false);
        img.setVisibility(View.VISIBLE);
        img.setImageBitmap(newBitmap);
    }
    private float calculateSummedNote() throws NoCommentException {
        if (comments.getComments().size()==0){
            throw new NoCommentException("no notes");
        }
        float note = 0;
        for (Comment comment:comments.getComments()
             ) {
            note+=comment.getNote();
        }
        summedNote = note/comments.getComments().size();
        return note/comments.getComments().size();

    }

    private void setNote(float n) {
        TextView note = alertDialog.findViewById(R.id.note_num);
        float rounded = (float) (Math.round(n * 10.0) / 10.0);
        note.setText(String.format("%.1f", rounded));
    }

    private void getComments() {
        new Thread(() -> {
            connector.get("/comment/" + recipeId, null, Request.Method.GET, response ->
            {
                Gson gson = new Gson();
                comments = gson.fromJson(response, Comments.class);
                float sumNote = 0;
                for (Comment comment : comments.getComments()) {
                    setupCommentView(comment);
                    sumNote += comment.getNote();
                }
                float finalSumNote = sumNote;

                runOnUiThread(() ->
                {

                    TextView view = findViewById(R.id.note_textview);
                    summedNote=0;
                    if (finalSumNote!=0){
                        summedNote = finalSumNote / comments.getComments().size();
                    }
                    view.setText(String.format("%.1f", summedNote));
                    if (comments.getComments().size()==0){
                        view.setText("not rated");
                        return;
                    }

                    setNoteImage();

                });
            }, error -> {
                Toast.makeText(this, "Couldnt load comments", Toast.LENGTH_LONG).show();
            });
        }).start();
    }
    private void addComment(String comment,String name,float note){
        new Thread(() -> {
            JSONObject body = new JSONObject();
            body.put("mac",macAddress);
            body.put("comment",comment);
            body.put("name",name);
            body.put("note",note);
            body.put("recipe_id",recipeId);
            connector.get("/comment", body, Request.Method.POST, response ->
            {
                Comment c = new Comment();
                c.setComment(comment);
                c.setMacAddress(macAddress);
                c.setName(name);
                c.setNote((double) note);
                c.setRecipeId(recipeId);
                setupCommentView(c);
                List<Comment> commentList = comments.getComments();
                commentList.add(c);
                comments.setComments(commentList);
                setRecipeNote();

            }, error -> {
                Toast.makeText(this, "Couldnt load comments", Toast.LENGTH_LONG).show();
            });
        }).start();
    }

    private void deleteComment(Comment comment) {
        new Thread(() -> {
            JSONObject body = new JSONObject();
            body.put("recipe_id",recipeId);
            body.put("mac",macAddress);
            connector.get("/comment", body, Request.Method.PATCH, response ->
            {
                runOnUiThread(() -> {
                    LinearLayout layout = findViewById(R.id.comment_layout);
                    layout.removeView(comment.getView());
                    List<Comment> commentList = comments.getComments();
                    commentList.remove(comment);
                    comments.setComments(commentList);
                    setRecipeNote();
                });
            }, error -> {
                Toast.makeText(this, "Couldnt delete comment", Toast.LENGTH_LONG).show();
            });
        }).start();
    }

    private void editComment(Comment comment,float note,String newComm){
        //TODO
        new Thread(()->{
            JSONObject body = new JSONObject();
            body.put("mac",macAddress);
            body.put("comment",newComm);
            body.put("note",note);
            connector.get("/comment/edit",body,Request.Method.PATCH,response ->
            {
                int idx = comments.getComments().indexOf(comment);
                Comment edited= comments.getComments().get(idx);
                edited.setNote((double) note);
                edited.setComment(newComm);
                comments.getComments().set(idx,edited);
                TextView commentView = comment.getView().findViewById(R.id.comment_textview);
                commentView.setText(comment.getComment());
                TextView noteView = comment.getView().findViewById(R.id.note_textview);
                noteView.setText(String.format("%.1f",comment.getNote()));


                setRecipeNote();
            },error -> {Toast.makeText(FeedbackActivity.this,"couldn'edit comment",Toast.LENGTH_LONG).show();});
        }).start();
    }


}
