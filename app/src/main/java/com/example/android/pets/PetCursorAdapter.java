package com.example.android.pets;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import static com.example.android.pets.data.PetContract.PetEntry.COLUMN_PET_BREED;
import static com.example.android.pets.data.PetContract.PetEntry.COLUMN_PET_GENDER;
import static com.example.android.pets.data.PetContract.PetEntry.COLUMN_PET_NAME;
import static com.example.android.pets.data.PetContract.PetEntry.COLUMN_PET_WEIGHT;
import static com.example.android.pets.data.PetContract.PetEntry.GENDER_FEMALE;
import static com.example.android.pets.data.PetContract.PetEntry.GENDER_MALE;

public class PetCursorAdapter extends CursorAdapter {

    public PetCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView summaryTextView = (TextView) view.findViewById(R.id.summary);
        TextView genderTextView = (TextView) view.findViewById(R.id.gender);
        String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PET_NAME));
        String breed = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PET_BREED));
        int gender = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PET_GENDER));
        int weight = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PET_WEIGHT));

        if (TextUtils.isEmpty(breed)) {
            breed = context.getString(R.string.unknown_breed);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            genderTextView.setTextAppearance(R.style.NameText);
        } else {
            genderTextView.setTextAppearance(context, R.style.NameText);
        }

        // TODO: remove 2 logs, textsize is small only, does not change to medium, if gender is not unknown

        Log.i("MALEorFEMALE", String.valueOf(genderTextView.getTextSize()));

        String genderToDisplay;
        switch (gender) {
            case GENDER_MALE:
                genderToDisplay = context.getString(R.string.gender_male_sign);
                break;
            case GENDER_FEMALE:
                genderToDisplay = context.getString(R.string.gender_female_sign);
                break;
            default:
                genderToDisplay = context.getString(R.string.unknown_gender);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    genderTextView.setTextAppearance(R.style.SummaryText);
                } else {
                    genderTextView.setTextAppearance(context, R.style.SummaryText);
                }
                Log.i("defaultGender", String.valueOf(genderTextView.getTextSize()));
        }

        String summary =
                breed
                + " - "
                + weight
                + " "
                + context.getString(R.string.kg_s);

        nameTextView.setText(name);
        summaryTextView.setText(summary);
        genderTextView.setText(genderToDisplay);
    }
}