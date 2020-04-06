package com.openclassrooms.entrevoisins.service;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
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
     *
     * GETNEIGHBOURS TESTS
     *
     * getNeighbour musnt return null value for expectedNeighbour
     */
    @Test
    public void getNeighboursWithSuccessNotNull() {
        Neighbour expectedNeighbour = service.getNeighbours().get(0);
        assertNotNull(expectedNeighbour);
    }
    /**
     * try to get a neighbour out of list size
     */
    @Test (expected = IndexOutOfBoundsException.class )
    public void getNeighboursOutOfList() {
        Neighbour expectedNeighbour = service.getNeighbours().get(service.getNeighbours().size()+1);
        assertTrue(service.getNeighbours().contains(expectedNeighbour));
    }
    /**
     * get in index Neighbour and return neighbour must be same
     */
    @Test
    public void getNeighboursWithSuccessSameNeighbour() {
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
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertEquals(11, service.getNeighbours().size());
    }
    /**
     * once deleted, neighbour in index x musnt be same as the one (the deleted) coming from this index
     */
    @Test
    public void deletedNeighbourIsNeighbourToDelete() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertNotEquals(neighbourToDelete, service.getNeighbours().get(0));
    }
    /**
     * once deleted neighbour musnt be in list neighbours
     */
    @Test
    public void deleteNeighbourWithSuccessFromList() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        neighbourToDelete.setFavorite(true);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbours().contains(neighbourToDelete));
    }
    /**
     * once deleted favoriteNeighbour musnt be in list neighbours
     */
    @Test
    public void deleteFavoriteNeighbourWithSuccessFromList() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        neighbourToDelete.setFavorite(true);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbourIsFavorite().contains(neighbourToDelete));
    }
    /**
     * once deleted favoriteNeighbour musnt be in list favoriteNeighbours
     */
    @Test
    public void deleteFavoriteNeighbourWithSuccessFromFavoriteList() {
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        neighbourToDelete.setFavorite(true);
        service.deleteNeighbour(neighbourToDelete);
        assertFalse(service.getNeighbourIsFavorite().contains(neighbourToDelete));
    }
    /**
     * try to delete neighbour not in list size
     */
    @Test (expected = IndexOutOfBoundsException.class)
    public void deleteNeighbourOutOfListSize() {
        Neighbour neighbourToDelete = service.getNeighbours().get(service.getNeighbours().size()+1);
        service.deleteNeighbour(neighbourToDelete);
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
        assertEquals(13, service.getNeighbours().size());
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
     * base neighbour list hasn't favorite. expected IndexOutOfBoundsException
     */
    //TODO test ok when test alone but incorrect with global test
    @Test (expected = IndexOutOfBoundsException.class)
    public void getFavoriteNeighboursWithSuccessFromEmptyList() {
        Neighbour expectedNeighbours = service.getNeighbourIsFavorite().get(5);
        assertTrue(service.getNeighbourIsFavorite().contains(expectedNeighbours));
    }
    /**
     * add a favoriteNeighbour to Neighbours list and check it is get from favoriteList
     */
    @Test
    public void getNeighbourIsFavoriteWithSuccessNotNull() {
        Neighbour expectedNeighbour = service.getNeighbours().get(0);
        expectedNeighbour.setFavorite(true);
        expectedNeighbour = service.getNeighbourIsFavorite().get(0);
        assertNotNull(expectedNeighbour);
    }
    /**
     *  try to get favorite neighbour out of list size
     */
    @Test (expected = IndexOutOfBoundsException.class)
    public void getNeighbourIsFavoriteOutOfListSize() {
        Neighbour expectedNeighbour = service.getNeighbours().get(0);
        expectedNeighbour.setFavorite(true);
        expectedNeighbour = service.getNeighbourIsFavorite().get(service.getNeighbours().size()+1);
        assertNotNull(expectedNeighbour);
    }



    /**
     * REPLACENEIGHBOURBYTHISNEIGHBOUR TEST
     *
     * modify and replace a neighbour in the same index with new param
     */
    @Test
    public void replaceNeighbourByThisNeighbourWithSucces() {
        Neighbour neighbour = service.getNeighbours().get(5);
        neighbour.setFavorite(true);
        neighbour.setName("Test");
        service.replaceNeighbourByThisNeighbour(neighbour);
        Neighbour neighbourExpected = service.getNeighbours().get(5);
        assertEquals(neighbourExpected, neighbour);
    }
    /**
     * try to replace neighbour without index, expected ArrayIndexOutOfBoundsException
     */
    @Test (expected = ArrayIndexOutOfBoundsException.class)
    public void replaceNeighbourCantReplace() {
        Neighbour testNeighbour = new Neighbour(25, "Test", "Test", "Test", "Test","Test",false);
        service.replaceNeighbourByThisNeighbour(testNeighbour);
        assertTrue(service.getNeighbours().contains(testNeighbour));
    }


}

