package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Neighbour service
 */
@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    /**
     *service.getNeighbours() return neighbours list
     *service.getNeighbourIsFavorite() return a list of favorite neighbours
     */


    /**
     * GETNEIGHBOURS TESTS
     *
     * getNeighbour musnt return null value for expectedNeighbour
     */
    @Test
    public void getNeighboursWithSuccessNotNull() {
        List<Neighbour> neighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        Neighbour expectedNeighbour = service.getNeighbours().get(0);
        assertNotNull(expectedNeighbour);
    }

    /**
     * get in index Neighbour and return neighbour must be same
     */
    @Test
    public void getNeighboursWithSuccessSameNeighbour() {
        List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();
        Neighbour expectedNeighbour = service.getNeighbours().get(10);
        assertEquals(service.getNeighbours().get(10), expectedNeighbour);
    }

    /**
     *DELETENEIGHBOURS TESTS
     *
     * after delete list is -1 (11)
     */
    @Test
    public void deleteNeighbourWithSuccess() {
        List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertTrue(service.getNeighbours().size() == 11);
    }

    /**
     * once deleted, neighbour in index x musnt be same as the one (the deleted) coming from this index
     */
    @Test
    public void deletedNeighbourIsNeighbourToDelete() {
        List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertNotEquals(neighbourToDelete, service.getNeighbours().get(0));
    }

    /**
     * once deleted neighbour musnt be in list neighbours
     */
    @Test
    public void deleteNeighbourWithSuccessFromList() {
        List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        neighbourToDelete.setFavorite(true);
        service.deleteNeighbour(neighbourToDelete);
        assertTrue(!service.getNeighbours().contains(neighbourToDelete));
    }

    /**
     * once deleted favoriteNeighbour musnt be in list neighbours
     */
    @Test
    public void deleteFavoriteNeighbourWithSuccessFromList() {
        List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        neighbourToDelete.setFavorite(true);
        service.deleteNeighbour(neighbourToDelete);
        assertTrue(!service.getNeighbourIsFavorite().contains(neighbourToDelete));
    }

    /**
     * once deleted favoriteNeighbour musnt be in list favoriteNeighbours
     */
    @Test
    public void deleteFavoriteNeighbourWithSuccessFromFavoriteList() {
        List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        neighbourToDelete.setFavorite(true);
        service.deleteNeighbour(neighbourToDelete);
        assertTrue(!service.getNeighbourIsFavorite().contains(neighbourToDelete));
    }



    /**
     * CREATENEIGHBOUR TESTS
     *
     *  add a new test neighbour search if is present in list
     */
    @Test
    public void createNeighbourWithSuccess() {
        Neighbour neighbourToCreate = new Neighbour(25, "Test", "Test", "Test", "Test","Test",false);
        service.createNeighbour(neighbourToCreate);
        assertTrue(service.getNeighbours().contains(neighbourToCreate));
    }

    /**
     * base list size is 12 may find 13
     */
    @Test
    public void createNeighbourWithSuccessMakeListUp() {
        Neighbour neighbourToCreate = new Neighbour(25, "Test", "Test", "Test", "Test","Test",false);
        service.createNeighbour(neighbourToCreate);
        assertTrue(service.getNeighbours().size() == 13);
    }

    /**
     *  base list size 12(index 11 max), add a new test neighbour in size 13 (index 12 max) control if created neighbour in 12 is added neighbour
     */
    @Test
    public void createNeighbourWithSuccessIsNeighbourAddToList() {
        Neighbour neighbourToCreate = new Neighbour(25, "Test", "Test", "Test", "Test","Test",false);
        service.createNeighbour(neighbourToCreate);
        assertEquals( service.getNeighbours().get(12), neighbourToCreate);
    }


    /**
     * GETNEIGHBOURISFAVORITE TESTS
     *
     * base neighbour list hasn't favorite. expected null
     */
    @Test
    public void getNeighbourIsFavoriteWithSucces() {
        List<Neighbour> neighbours = service.getNeighbours();
        List<Neighbour> expectedNeighbours = service.getNeighbourIsFavorite();
        assertArrayEquals((boolean[]) null, null);
    }

    /**
     * add a favoriteNeighbour to Neighbours list and check it is get from favoriteList
     */
    @Test
    public void getNeighbourIsFavoriteWithSuccessNotNull() {
        List<Neighbour> neighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        Neighbour expectedNeighbour = service.getNeighbours().get(0);
        expectedNeighbour.setFavorite(true);
        expectedNeighbour = service.getNeighbourIsFavorite().get(0);
        assertNotNull(expectedNeighbour);
    }

    /**
     * REPLACENEIGHBOURBYTHISNEIGHBOUR TEST
     *
     * modify and replace a neighbour in the same index with new param
     */
    @Test
    public void replaceNeighbourByThisNeighbourWithSucces() {
        List<Neighbour> neighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        Neighbour neighbour = service.getNeighbours().get(5);
        neighbour.setFavorite(true);
        neighbour.setName("Test");
        service.replaceNeighbourByThisNeighbour(neighbour);
        Neighbour neighbourExpected = service.getNeighbours().get(5);
        assertEquals(neighbourExpected, neighbour);
    }
}

