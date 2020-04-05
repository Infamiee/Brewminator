package pl.nzi.brewminator.adapter;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pl.nzi.Brewminator;
import pl.nzi.brewminator.RecipeDatabaseHelper;
import pl.nzi.brewminator.R;
import pl.nzi.brewminator.model.RecipeSearch;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> implements Filterable {
    private List<RecipeSearch> recipeList;
    private List<RecipeSearch> recipeListFull;
    private RecipeDatabaseHelper db;
    private OnClickRecipeListener onClickRecipeListener;

    public RecipeAdapter(List<RecipeSearch> exampleList,OnClickRecipeListener onClickRecipeListener) {
        this.recipeList = exampleList;
        this.onClickRecipeListener = onClickRecipeListener;
        recipeListFull = new ArrayList<>(exampleList);
        db = new RecipeDatabaseHelper(Brewminator.getAppContext());

    }





    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnClickRecipeListener onClickRecipeListener;
        TextView name;
        TextView style;
        RecipeViewHolder(View itemView,OnClickRecipeListener onClickRecipeListener) {
            super(itemView);
            name = itemView.findViewById(R.id.textView);
            style = itemView.findViewById(R.id.textView2);
            this.onClickRecipeListener = onClickRecipeListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickRecipeListener.onRecipeClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_view,
                parent, false);
        return new RecipeViewHolder(v,onClickRecipeListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        String recipe = recipeList.get(position).getName();
        String style = recipeList.get(position).getStyle();
        holder.name.setText(recipe);
        holder.style.setText(style);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter(){

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<RecipeSearch> filteredList = new ArrayList<>();
            if (constraint == null || constraint.toString().trim().length()==0){
                filteredList.addAll(recipeListFull);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                Cursor cursor = db.getRecipesByKeyword(filterPattern);
                for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndex("RecipeName"));
                    int id = cursor.getInt(cursor.getColumnIndex("RecipeId"));
                    String style = cursor.getString(cursor.getColumnIndex("Style"));
                    RecipeSearch recipeSearch = new RecipeSearch(id,name,style);
                    filteredList.add(recipeSearch);
                }
            }
            FilterResults results = new FilterResults();
            Collections.sort(filteredList);
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            recipeList.clear();
            recipeList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public interface OnClickRecipeListener{
        void onRecipeClick(int position);
    }


}
