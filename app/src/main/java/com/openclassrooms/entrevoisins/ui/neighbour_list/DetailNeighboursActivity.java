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

    private NeighbourApiService mApiService= DI.getNeighbourApiService();
    private Neighbour neighbour;
    private FloatingActionButton mFavoriteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_neighbours);
        mFavoriteButton = findViewById(R.id.detail_add_favorite_neighbour_floating_button);

        getNeighbourIntent();
        isAlreadyFavorite();
        updateNeighbour();
        clickFavoriteButton();
        clickBackButton();

    }//fin oncreate

    private void updateNeighbour(){
        String getAvatar = neighbour.getAvatarUrl();
        ImageView avatar = findViewById(R.id.detail_avatar_view);
        Glide.with(this)
                .load(getAvatar)
                .into(avatar);

        String getMail = neighbour.getName();
        TextView mail = findViewById(R.id.detail_mail);
        mail.setText("www.facebook.com/"+getMail);

        String getPhone = neighbour.getPhoneNumber();
        TextView phone = findViewById(R.id.detail_phone);
        phone.setText(getPhone);

        String getAdress = neighbour.getAddress();
        TextView adress = findViewById(R.id.detail_adress);
        adress.setText(getAdress);

        String getAboutMe = neighbour.getAboutMe();
        TextView aboutMe = findViewById(R.id.detail_about_me);
        aboutMe.setText(getAboutMe);

        String getFirstname =neighbour.getName();
        TextView firstname = findViewById(R.id.detail_firstname);
        firstname.setText(getFirstname);

        TextView nameOnAvatar = findViewById(R.id.detail_firstname_on_avatar_image);
        nameOnAvatar.setText(getFirstname);
    }

    private void clickFavoriteButton(){
        mFavoriteButton.setOnClickListener(v -> {
            if (neighbour.isFavorite()) {
                neighbour.setFavorite(false);
                mApiService.replaceNeighbourByThisNeighbour(neighbour);
                mFavoriteButton.setImageResource(R.drawable.ic_star_grey_24dp);
            }
            else {
                this.neighbour.setFavorite(true);
                mApiService.replaceNeighbourByThisNeighbour(neighbour);
                mFavoriteButton.setImageResource(R.drawable.ic_star_gold_24dp);
            }
        });
    }

    private void isAlreadyFavorite(){
        int favoriteImage = neighbour.isFavorite() ? R.drawable.ic_star_gold_24dp : R.drawable.ic_star_grey_24dp;
        mFavoriteButton.setImageResource(favoriteImage);
    }

    private  void getNeighbourIntent(){
        Intent intent = getIntent();
        neighbour = intent.getParcelableExtra("Neighbours");
    }

    private void clickBackButton(){
        ImageButton mbackButton = findViewById(R.id.back_button);
        mbackButton.setOnClickListener(v -> DetailNeighboursActivity.this.finish());
    }
}
























