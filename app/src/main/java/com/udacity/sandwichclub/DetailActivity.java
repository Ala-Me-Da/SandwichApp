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
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

// import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    /* Avoid slow reflection by using ButterKnife to generate code for view look ups */
    @BindView(R.id.also_known_tv) TextView mAlsoKnownAsTextView;
    @BindView(R.id.origin_tv) TextView mPlaceOfOriginTextView;
    @BindView(R.id.description_tv) TextView mDescriptionTextView;

    ImageView ingredientsIv;

    @BindView(R.id.ingredients_tv) TextView mIngredientsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        ingredientsIv = findViewById(R.id.image_iv);

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
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .error(R.drawable.error)
                .placeholder(R.drawable.loading)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        mPlaceOfOriginTextView.setText(" " + sandwich.getPlaceOfOrigin());
        mDescriptionTextView.setText(" " + sandwich.getDescription());
        List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();
        List<String> ingredientsList = sandwich.getIngredients();
        setTextForLists(mAlsoKnownAsTextView, alsoKnownAsList);
        setTextForLists(mIngredientsTextView, ingredientsList);
    }

    /*
     * Helper function to process and set a List of Strings as text for a TextView
     * @textView: TextView object recieving Strings
     * @list: List of String objects to append to textView.
     */
    private void setTextForLists(TextView textView, List<String> list) {
        int count = 0;
        textView.append(" ");
        for(String s : list) {
            textView.append(s);
            if(count != list.size() - 1) {
                textView.append(", ");
            }
            count++;
        }
    }
}
