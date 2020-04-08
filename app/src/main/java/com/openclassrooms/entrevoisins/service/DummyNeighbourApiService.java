package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements  NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }


    public List<Neighbour> getNeighbourIsFavorite() {
        List<Neighbour> favoriteNeighbours = new ArrayList<>();

        for (Neighbour neighbour : neighbours) {
            if (neighbour.isFavorite()){
                favoriteNeighbours.add(neighbour);
            }
        }
        return favoriteNeighbours;
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


}//fin class
