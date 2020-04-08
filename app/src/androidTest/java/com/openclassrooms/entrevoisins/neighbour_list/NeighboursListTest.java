
package com.openclassrooms.entrevoisins.neighbour_list;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Random;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.core.IsNull.notNullValue;



/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    private NeighbourApiService mApiService = DI.getNeighbourApiService();
    private List<Neighbour> neighbours = mApiService.getNeighbours();


    @Rule
    public ActivityTestRule mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        ListNeighbourActivity mActivity = (ListNeighbourActivity) mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(withId(R.id.list_neighbours))
                .check(matches(hasMinimumChildCount(1)));
    }


     /**
     * aprés click suppression on verifie que le nombre d'item de la vue voisin supprimée ne correspond pas a celui de la vue voisin
     */
    @Test
    public void useDeleteNeighbourButtonDisplayNeighbourListMinusOne() {
        int neighbourSizeToFind = neighbours.size()-1;
        onView(withId(R.id.list_neighbours)).check(withItemCount(neighbours.size()));
        onView(withId(R.id.list_neighbours)).perform(actionOnItemAtPosition(1, new DeleteViewAction()));
        onView(withId(R.id.list_neighbours)).check(withItemCount(neighbourSizeToFind));}

    /**
     * on vérifie qu'en appuyant sur la liste des favoris, le layout principal du detail s'affiche
     */
    @Test
    public void detailNeighbourActivityIsLaunch() {
        onView(withId(R.id.list_neighbours)).perform(actionOnItemAtPosition(1, click()));
        onView(withId(R.id.detail_main_constraint_layout)).check(matches(isDisplayed()));
    }

    /**
     *  on verifie que la vue favoris ne contient que des favoris
     */
    @Test
    public void favoriteNeighbourListContainsOnlyFavoriteNeighbour() {
        Random r = new Random();
        int ir = r.nextInt(11)+1;
        for (int i=0; i < ir; i++){
            neighbours.get(i).setFavorite(true);
        }

        /**
         * need launch an activity (detail) then to kill activity for reload neighbourList with favorite param true
         * i think because at start, neighbour list is initiate with no favorite and in tests add favorite action doesnt initiate favorite param
         */
        onView(ViewMatchers.withId(R.id.list_neighbours)).perform(actionOnItemAtPosition(0, click()));
        onView(ViewMatchers.withId(R.id.back_button)).perform(click());
        onView(ViewMatchers.withId(R.id.main_content)).perform(swipeLeft());
        onView(ViewMatchers.withId(R.id.list_favorite_neighbours)).check(matches(hasChildCount(ir)));
        }

    /**
     * on vérifie qu'en appuyant sur un voisin dans le recyclerView c'est bien son nom qui s'affiche dans les details, donc qu'il existe
     */
    @Test
    public void detailNeighbourFirstnameExist() {
        Neighbour neighbour = mApiService.getNeighbours().get(1);
        onView(withId(R.id.list_neighbours)).perform(actionOnItemAtPosition(1, click()));
        onView(withId(R.id.detail_firstname)).check(matches(withText(neighbour.getName())));
    }

}//fin tests
