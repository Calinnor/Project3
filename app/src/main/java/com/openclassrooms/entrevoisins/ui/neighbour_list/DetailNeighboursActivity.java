package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.DisplayDetailActivityEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailNeighboursActivity extends AppCompatActivity {

    @BindView(R.id.detail_mail) TextView mail;
    @BindView(R.id.detail_phone) TextView phone;
    @BindView(R.id.detail_adress) TextView adress;
    @BindView(R.id.detail_about_me) TextView aboutMe;
    @BindView(R.id.detail_firstname) TextView firstname;
    @BindView(R.id.detail_avatar_view) ImageView avatar;
    @BindView(R.id.detail_firstname_on_avatar_image) TextView firstnameOnavatar;
    @BindView(R.id.detail_add_favorite_neighbour_floating_button) FloatingActionButton mFavoriteButton;

    private NeighbourApiService mApiService= DI.getNeighbourApiService();
    private Neighbour neighbour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_neighbours);
        ButterKnife.bind(this);
        getNeighbourIntent();
        isAlreadyFavorite();
        updateNeighbour();
        clickFavoriteButton();
        clickBackButton();
    }//fin oncreate

    private void updateNeighbour(){
        //String getAvatar = neighbour.getAvatarUrl();
        //ImageView avatar = findViewById(R.id.detail_avatar_view);
        Glide.with(this)
                .load(neighbour.getAvatarUrl())
                .into(avatar);

//        String getMail = neighbour.getName();
//        TextView mail = findViewById(R.id.detail_mail);
//        mail.setText("www.facebook.com/"+getMail);

        mail.setText("www.facebook.com/"+neighbour.getName());
        phone.setText(neighbour.getPhoneNumber());
        adress.setText(neighbour.getAddress());
        aboutMe.setText(neighbour.getAboutMe());
        firstname.setText(neighbour.getName());
        firstnameOnavatar.setText(neighbour.getName());
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

        @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onDisplayDetailActivity(DisplayDetailActivityEvent event){
        Intent intent = new Intent(this, DetailNeighboursActivity.class);
        startActivity(intent);
    }
}
























