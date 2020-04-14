package com.openclassrooms.entrevoisins.events;

import android.content.Intent;
import android.view.View;

import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.ui.neighbour_list.DetailNeighboursActivity;


public class DisplayDetailActivityEvent {

    public Neighbour neighbour;

    public DisplayDetailActivityEvent(Neighbour neighbour, View v){
        Intent intent = new Intent(v.getContext(), DetailNeighboursActivity.class);
        intent.putExtra("Neighbours", neighbour);
        v.getContext().startActivity(intent);
    }
}