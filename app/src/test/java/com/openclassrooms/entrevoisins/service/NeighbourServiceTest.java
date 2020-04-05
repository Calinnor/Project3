package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
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
     */


    /**
     * getNeighbour musnt return null value for expectedNeighbour
     */
    @Test
    public void getNeighboursWithSuccessNotNull() {
        List<Neighbour> neighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        Neighbour expectedNeighbour = service.getNeighbours().get(0);
        assertNotNull(expectedNeighbour);
    }


    /**
     * get Neighbour and return neighbour must be same
     */
    @Test
    public void getNeighboursWithSuccessSameNeighbour() {
        List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();
        Neighbour expectedNeighbour = service.getNeighbours().get(10);
        assertEquals(service.getNeighbours().get(10), expectedNeighbour);
    }

    /**
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
     * once deleted neighbour musnt be same as get(0) from it comes
     */
    @Test
    public void deletedNeighbourIsNeighbourToDelete() {
        List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertNotEquals(neighbourToDelete, service.getNeighbours().get(0));
    }

    /**
     * once deleted neighbour musnt be in list
     */
    @Test
    public void deleteNeighbourWithSuccessFromList() {
        List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();
        Neighbour neighbourToDelete = service.getNeighbours().get(0);
        service.deleteNeighbour(neighbourToDelete);
        assertTrue(!service.getNeighbours().contains(neighbourToDelete));
    }

    /**
     *  add a new test neighbour search if is present in list
     */
    @Test
    public void createNeighbourWithSuccess() {
        Neighbour neighbourToCreate = new Neighbour(25, "Test", "Test", "Test", "Test","Test",false);
        service.createNeighbour(neighbourToCreate);
        assertTrue(service.getNeighbours().contains(neighbourToCreate));
    }

    /**
     *  base list size 12(index 11 max), add a new test neighbour in size 13 (index 12 max) control if created neighbour is added neighbour
     */
    @Test
    public void createNeighbourWithSuccessIsNeighbourAddToList() {
        Neighbour neighbourToCreate = new Neighbour(25, "Test", "Test", "Test", "Test","Test",false);
        service.createNeighbour(neighbourToCreate);
        assertEquals( service.getNeighbours().get(12), neighbourToCreate);
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







}
