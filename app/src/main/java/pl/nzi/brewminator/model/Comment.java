
package pl.nzi.brewminator.model;

import android.view.View;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comment {

    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("mac_address")
    @Expose
    private String macAddress;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("note")
    @Expose
    private Double note;
    @SerializedName("recipe_id")
    @Expose
    private Integer recipeId;

    View view;

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getNote() {
        return note;
    }

    public void setNote(Double note) {
        this.note = note;
    }

    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId) {
        this.recipeId = recipeId;
    }

}
