package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private ImageView mSandwichPicture;
    private TextView mOriginTextView;
    private TextView mAKATextView;
    private TextView mIngredientsTextView;
    private TextView mDescriptionTextView;
    private Sandwich mSandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mSandwichPicture = findViewById(R.id.image_iv);
        mOriginTextView = findViewById(R.id.origin_tv);
        mAKATextView = findViewById(R.id.also_known_tv);
        mIngredientsTextView = findViewById(R.id.ingredients_tv);
        mDescriptionTextView = findViewById(R.id.description_tv);


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
         mSandwich = null;
        try {
            mSandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (mSandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(mSandwich.getImage())
                .into(mSandwichPicture);

        setTitle(mSandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {
        mOriginTextView.setText(mSandwich.getPlaceOfOrigin());
        mAKATextView.setText((TextUtils.join(", ", mSandwich.getAlsoKnownAs())));
        mIngredientsTextView.setText(TextUtils.join(", ", mSandwich.getIngredients()));
        mDescriptionTextView.setText(mSandwich.getDescription());
    }
}
