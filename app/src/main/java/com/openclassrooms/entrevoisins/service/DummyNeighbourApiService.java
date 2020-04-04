package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements  NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();
    private final List<Neighbour> neighbourIsFavorite = new ArrayList<>();
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    public List<Neighbour> getNeighbourIsFavorite(){
        return neighbourIsFavorite;
    }

    public void setNeighbours(List<Neighbour> neighbours) {
        this.neighbours = neighbours;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);
        if(neighbourIsFavorite != null && neighbour.isFavorite()) {
            neighbourIsFavorite.remove(neighbour);
        }
    }

    /**
     * {@inheritDoc}
     * @param neighbour
     */
    @Override
    public void createNeighbour(Neighbour neighbour) {
        neighbours.add(neighbour);
    }

    @Override
    public void replaceNeighbourByThisNeighbour(Neighbour neighbour) {
        int neighboursIndexToChange = neighbours.indexOf(neighbour);
        neighbours.set(neighboursIndexToChange, neighbour);
    }

    @Override
    public void favoriteNeighbourIsOrNotInFavoriteList(Neighbour neighbour) {
        if(!neighbourIsFavorite.contains(neighbour)){
            addToFavoriteList(neighbour);
        }
    }

    @Override
    public void removeFromFavoriteList(Neighbour neighbour) {
        neighbourIsFavorite.remove(neighbour);
    }

    public void addToFavoriteList(Neighbour neighbour) {
        neighbourIsFavorite.add(neighbour);
    }

}//fin class
