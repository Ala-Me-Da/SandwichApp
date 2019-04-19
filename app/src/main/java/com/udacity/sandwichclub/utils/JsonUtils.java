package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    /*
     * Converted String literal keys to constants to avoid typographical errors, code duplication,
     *  across app functionality, and easy reference.
     */
    public static final String NAME_KEY = "name";
    public static final String ALSO_KNOWN_AS_KEY = "alsoKnownAs";
    public static final String INGREDIENTS_KEY = "ingredients";
    public static final String MAIN_NAME_KEY = "mainName";
    public static final String PLACE_OF_ORIGIN_KEY = "placeOfOrigin";
    public static final String DESCRIPTION_KEY = "description";
    public static final String IMAGE_KEY = "image";

    /*
     * Converted getString(), getJSONObject(), etc to optString(), optJSONObject() to ensure that
     * an empty String is received when no value is returned from the API.
     */
    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;
        try {
            // Build JSONObject for entire sandwich item using json String
            JSONObject sandwichDetailsJson  = new JSONObject(json);

            // Create JSONObject and JSONArray(s) for parsing
            JSONObject   sandwichNameJson     = sandwichDetailsJson.optJSONObject(NAME_KEY);
            JSONArray    alsoKnownAsJson      = sandwichNameJson.optJSONArray(ALSO_KNOWN_AS_KEY);
            JSONArray    ingredientsJson      = sandwichDetailsJson.optJSONArray(INGREDIENTS_KEY);

            String       mainName             = sandwichNameJson.optString(MAIN_NAME_KEY);
            List<String> alsoKnownAs          = getStringList(alsoKnownAsJson);
            String       placeOfOrigin        = sandwichDetailsJson.optString(PLACE_OF_ORIGIN_KEY);
            String       description          = sandwichDetailsJson.optString(DESCRIPTION_KEY);
            String       image                = sandwichDetailsJson.optString(IMAGE_KEY);
            List<String> ingredients          = getStringList(ingredientsJson);

            sandwich                          = new Sandwich(mainName, alsoKnownAs, placeOfOrigin,
                                                                description, image, ingredients);

        } catch(JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }

    /**
     * Helper function to parse JSONArrays present in the Sandwich items in the strings.xml resource.
     *
     * @param {jsonArray}:  containing JSON to be parsed into a list.
     */
    private static List<String> getStringList(JSONArray jsonArray) {
        int sizeOfJsonArray = jsonArray.length();
        List<String> resultList = new ArrayList<String>();

        String jsonString = null;
        for(int i = 0; i < sizeOfJsonArray; i++) {
            jsonString = jsonArray.optString(i);
            resultList.add(jsonString);
        }

        return resultList;
    }
}
