package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.Arrays;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Populate the UI after parsing JSON.
     *
     * @param sandwich is contain sandwich info.
     */
    private void populateUI(Sandwich sandwich) {
        TextView alsoKnownAs_text_view = findViewById(R.id.also_known_tv);
        String alsoString = "";
        for (String str : sandwich.getAlsoKnownAs()) {
            alsoString += str + "\n";
        }
        alsoKnownAs_text_view.setText(alsoString);

        TextView placeOfOrigin_text_view = findViewById(R.id.origin_tv);
        placeOfOrigin_text_view.setText(sandwich.getPlaceOfOrigin());

        TextView description_text_view = findViewById(R.id.description_tv);
        description_text_view.setText(sandwich.getDescription());

        TextView ingredients_text_view = findViewById(R.id.ingredients_tv);
        String ingredientsString = "";
        for (String str : sandwich.getIngredients()) {
            ingredientsString += str + "\n";
        }
        ingredients_text_view.setText(ingredientsString);
    }
}
