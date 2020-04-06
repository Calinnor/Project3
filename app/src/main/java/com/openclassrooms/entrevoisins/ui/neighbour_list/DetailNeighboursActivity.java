package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.DummyNeighbourApiService;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import java.util.List;

public class DetailNeighboursActivity extends AppCompatActivity {

    private NeighbourApiService mApiService;

    private boolean mIsFavorite;

    private ImageButton mbackButton;

    private FloatingActionButton mFavoriteButton;


    private TextView phone;
    private TextView adress;
    private TextView aboutMe;
    private TextView firstname;
    private TextView nameOnAvatar;
    private TextView mail;
    private ImageView avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_neighbours);

        mApiService = DI.getNeighbourApiService();
        mFavoriteButton = findViewById(R.id.detail_add_favorite_neighbour_floating_button);
        mbackButton = findViewById(R.id.back_button);
        mbackButton.setOnClickListener(v -> DetailNeighboursActivity.this.finish());

        Intent intent = getIntent();
        Neighbour neighbour = intent.getParcelableExtra("Neighbours");

        mIsFavorite = neighbour.isFavorite();
        int favoriteImage = mIsFavorite ? R.drawable.ic_star_gold_24dp : R.drawable.ic_star_grey_24dp;
        mFavoriteButton.setImageResource(favoriteImage);

        String getPhone = neighbour.getPhoneNumber();
        phone = findViewById(R.id.detail_phone);
        phone.setText(getPhone);

        String getAdress = neighbour.getAddress();
        adress = findViewById(R.id.detail_adress);
        adress.setText(getAdress);

        String getAboutMe = neighbour.getAboutMe();
        aboutMe = findViewById(R.id.detail_about_me);
        aboutMe.setText(getAboutMe);

        String getFirstname =neighbour.getName();
        firstname = findViewById(R.id.detail_firstname);
        firstname.setText(getFirstname);

        nameOnAvatar = findViewById(R.id.detail_firstname_on_avatar_image);
        nameOnAvatar.setText(getFirstname);

        String getMail = neighbour.getName();
        mail = findViewById(R.id.detail_mail);
        mail.setText("www.facebook.com/"+getMail);

        String getAvatar = neighbour.getAvatarUrl();
        avatar = findViewById(R.id.detail_avatar_view);
        Glide.with(this)
                .load(getAvatar)
                .into(avatar);

        mFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mIsFavorite) {
                    neighbour.setFavorite(false);
                    mApiService.replaceNeighbourByThisNeighbour(neighbour);
                    mFavoriteButton.setImageResource(R.drawable.ic_star_grey_24dp);
                }
                else {
                    neighbour.setFavorite(true);
                    mApiService.replaceNeighbourByThisNeighbour(neighbour);
                    mFavoriteButton.setImageResource(R.drawable.ic_star_gold_24dp);
                }
            }
        });

    }//fin oncreate
}
























