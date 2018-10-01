package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    /**
     * Parse JSON string object with sandwich info.
     *
     * @param json is string object with sandwich info.
     * @return Sandwich object.
     */
    public static Sandwich parseSandwichJson(String json) {
        JSONObject sandwichJSON = null;
        JSONObject nameJSON = null;
        try {
            sandwichJSON = new JSONObject(json);
            nameJSON = sandwichJSON.getJSONObject("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String mainName = nameJSON.optString("mainName");
        JSONArray alsoKnownAsJSON = nameJSON.optJSONArray("alsoKnownAs");
        List<String> alsoKnownAs = new ArrayList<String>();
        for (int i = 0; i < alsoKnownAsJSON.length(); i++) {
            alsoKnownAs.add(alsoKnownAsJSON.optString(i));
        }

        String placeOfOrigin = sandwichJSON.optString("placeOfOrigin");
        String description = sandwichJSON.optString("description");
        String image = sandwichJSON.optString("image");
        JSONArray ingredientsJSON = sandwichJSON.optJSONArray("ingredients");
        List<String> ingredients = new ArrayList<String>();
        for (int i = 0; i < ingredientsJSON.length(); i++) {
            ingredients.add(ingredientsJSON.optString(i));
        }

        return new Sandwich(
                mainName,
                alsoKnownAs,
                placeOfOrigin,
                description,
                image,
                ingredients
        );
    }
}
