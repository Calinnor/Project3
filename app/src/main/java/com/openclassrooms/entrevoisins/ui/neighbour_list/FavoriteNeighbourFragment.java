package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteNeighbourFragment extends Fragment {


        private NeighbourApiService mApiService;
        private RecyclerView mRecyclerView;


        public static FavoriteNeighbourFragment newInstance() {
            return new FavoriteNeighbourFragment();
        }


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mApiService = DI.getNeighbourApiService();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_favorite_neighbour, container, false);
            Context context = view.getContext();
            mRecyclerView = (RecyclerView) view;
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            mRecyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL));
            return view;
        }

        private void initList() {
            List<Neighbour> mNeighbours = mApiService.getNeighbourIsFavorite();
            mRecyclerView.setAdapter(new MyNeighbourRecyclerViewAdapter(mNeighbours));
        }

        @Override
        public void onResume() {
            super.onResume();
            initList();
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

        /**
         * Fired if the user clicks on a delete button
         *
         * @param event delete
         */
        @Subscribe
        public void onDeleteNeighbour(DeleteNeighbourEvent event) {
            mApiService.deleteNeighbour(event.neighbour);
            initList();
        }
    }

