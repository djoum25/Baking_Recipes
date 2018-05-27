package com.laurent_julien_nano_degree_project.bakingrecipes.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Recipes implements Parcelable {

    private int id;
    private String name;
    private List<Ingredient> ingredients = null;
    private List<Step> steps = null;
    private int servings;
    private String image;

    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients () {
        return ingredients;
    }

    public void setIngredients (List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps () {
        return steps;
    }

    public void setSteps (List<Step> steps) {
        this.steps = steps;
    }

    public int getServings () {
        return servings;
    }

    public void setServings (int servings) {
        this.servings = servings;
    }

    public String getImage () {
        return image;
    }

    public void setImage (String image) {
        this.image = image;
    }

    public boolean hasImages(){
        return image.length() > 0;
    }

    @Override
    public String toString () {
        return this.getName();
    }

    @Override
    public int describeContents () {
        return 0;
    }

    @Override
    public void writeToParcel (Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeTypedList(this.ingredients);
        dest.writeList(this.steps);
        dest.writeInt(this.servings);
        dest.writeString(this.image);
    }

    public Recipes () {
    }

    protected Recipes (Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.ingredients = in.createTypedArrayList(Ingredient.CREATOR);
        this.steps = new ArrayList<Step>();
        in.readList(this.steps, Step.class.getClassLoader());
        this.servings = in.readInt();
        this.image = in.readString();
    }

    public static final Parcelable.Creator<Recipes> CREATOR = new Parcelable.Creator<Recipes>() {
        @Override
        public Recipes createFromParcel (Parcel source) {
            return new Recipes(source);
        }

        @Override
        public Recipes[] newArray (int size) {
            return new Recipes[size];
        }
    };
}