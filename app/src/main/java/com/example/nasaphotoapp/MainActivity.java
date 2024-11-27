package com.example.nasaphotoapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://api.nasa.gov/";
    private static final String API_KEY = "stXSOIJUB57qJ7EWXsF0y7ljKDLj5UP8jncrZyJb";

    private ImageView imageView;
    private TextView titleTextView;
    private TextView descriptionTextView;
    private EditText dateEditText;
    private Button fetchByDateButton;
    private Button todayButton;

    private NasaApiService nasaApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        titleTextView = findViewById(R.id.titleTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        dateEditText = findViewById(R.id.dateEditText);
        fetchByDateButton = findViewById(R.id.fetchByDateButton);
        todayButton = findViewById(R.id.todayButton);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        nasaApiService = retrofit.create(NasaApiService.class);
        todayButton.setOnClickListener(v -> fetchPhotoOfTheDay(null));
        fetchByDateButton.setOnClickListener(v -> {
            String date = dateEditText.getText().toString();
            if (date.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please enter a valid date!", Toast.LENGTH_SHORT).show();
            } else {
                fetchPhotoOfTheDay(date);
            }
        });
    }

    private void fetchPhotoOfTheDay(String date) {
        Call<NasaPhoto> call = nasaApiService.getPhotoOfTheDay(API_KEY, date);
        call.enqueue(new Callback<NasaPhoto>() {
            @Override
            public void onResponse(Call<NasaPhoto> call, Response<NasaPhoto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    NasaPhoto photo = response.body();
                    titleTextView.setText(photo.getTitle());
                    descriptionTextView.setText(photo.getExplanation());
                    Picasso.get().load(photo.getUrl()).into(imageView);
                } else {
                    Toast.makeText(MainActivity.this, "Failed to load photo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NasaPhoto> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
