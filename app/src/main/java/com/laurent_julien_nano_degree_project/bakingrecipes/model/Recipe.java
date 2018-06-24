package com.laurent_julien_nano_degree_project.bakingrecipes.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Recipe implements Parcelable {

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel (Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray (int size) {
            return new Recipe[size];
        }
    };
    private int id;
    private String name;
    private List<Ingredient> ingredients = null;
    private List<Step> steps = null;
    private int servings;
    private String image;

    public Recipe () {
    }

    protected Recipe (Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.ingredients = in.createTypedArrayList(Ingredient.CREATOR);
        this.steps = new ArrayList<Step>();
        in.readList(this.steps, Step.class.getClassLoader());
        this.servings = in.readInt();
        this.image = in.readString();
    }

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

    public String servingSize () {
        return String.format(Locale.US, "Serving: %d", servings);
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

    public boolean hasImages () {
        return image.length() > 0;
    }

    @Override
    public String toString () {
        return this.getName();
    }

    public String setRecipeNameForIngredient (String recipe) {
        return String.format("%s %s", "List of ingredients to prepare a ", recipe);
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
}