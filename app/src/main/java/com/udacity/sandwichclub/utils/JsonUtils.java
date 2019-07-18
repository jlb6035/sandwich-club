package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        Sandwich sandwich = new Sandwich();
        JSONObject details = new JSONObject(json);
        JSONObject sandwichName = details.getJSONObject("name");
        List<String> akaList = new ArrayList<>();
        List<String> ingredientsList = new ArrayList<>();

        JSONArray alsoKnowAs = sandwichName.getJSONArray("alsoKnownAs");
        for (int i = 0; i < alsoKnowAs.length(); i++){
            akaList.add(alsoKnowAs.getString(i));
        }

        JSONArray ingredients = details.getJSONArray("ingredients");
        for (int i = 0; i < ingredients.length(); i++){
            ingredientsList.add(ingredients.getString(i));
        }

        sandwich.setMainName(sandwichName.getString("mainName"));
        sandwich.setAlsoKnownAs(akaList);
        sandwich.setPlaceOfOrigin(details.getString("placeOfOrigin"));
        sandwich.setDescription(details.getString("description"));
        sandwich.setImage(details.getString("image"));
        sandwich.setIngredients(ingredientsList);

        return sandwich;
    }
}
