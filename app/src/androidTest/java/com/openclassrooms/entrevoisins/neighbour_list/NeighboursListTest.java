
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
        //on recupere la vue id list qui check with le nombre d'elements de la liste (neighbour.size())
        //on creer une var recuperant la valeur de neighbour.size apres une suppression (-1)
        int neighbourSizeToFind = neighbours.size()-1;
        onView(withId(R.id.list_neighbours)).check(withItemCount(neighbours.size()));
        //on recupere la vue id list puis on click sur le bouton delete
        onView(withId(R.id.list_neighbours)).perform(actionOnItemAtPosition(1, new DeleteViewAction()));
        //on recupere la vue id list with nombre d'element -1
        onView(withId(R.id.list_neighbours)).check(withItemCount(neighbourSizeToFind));}

    /**
     * on vérifie qu'en appuyant sur la liste des favoris, le layout principal du detail s'affiche
     */
    @Test
    public void detailNeighbourActivityIsLaunch() {
        //sur la vue qui affiche la liste des voisins, on effectue une action: en position 1 on click
        onView(withId(R.id.list_neighbours)).perform(actionOnItemAtPosition(1, click()));
        //a lire a l'envers: on verifie que ce qui est affiche correspond a la la vue de l'image de l'avatar
        // il est preferable de passer par le layout de l'activité
        onView(withId(R.id.detail_main_constraint_layout)).check(matches(isDisplayed()));
        //code original: onView(ViewMatchers.withId(R.id.detail_main_constraint_layout)).check(matches(isDisplayed()));
        //merci a charlotte pour sa remarque
        //si c'est bon, c'est que le detailActivity est lancé, forcement...je pense
    }

    /**
     *  on verifie que la vue favoris ne contient que des favoris
     */
    @Test
    public void favoriteNeighbourListContainsOnlyFavoriteNeighbour() {
        //on passe un random ir neighbour (1-12) en favorite true
        Random r = new Random();
        int ir = r.nextInt(11)+1;
        for (int i=0; i < ir; i++){
            neighbours.get(i).setFavorite(true);
        }
        /**
         * need launch an activity (detail) then to kill activity for reload neighbourList with favorite param true
         * i think because at start, neighbour list is initiate with no favorite and in tests add favorite action doesnt initiate favorite param
         */
        //a partir du recyclerView neighbour list on lance detail
        onView(ViewMatchers.withId(R.id.list_neighbours)).perform(actionOnItemAtPosition(0, click()));
        //on tue l'activité et retourne à main: reload de neighbour list avec les param favorite
        onView(ViewMatchers.withId(R.id.back_button)).perform(click());

        //on swipe l'activité a gauche (on va a droite) pour afficher favoris
        onView(ViewMatchers.withId(R.id.main_content)).perform(swipeLeft());

        //on verifie que le nombre d'item affichés correspond à ir favorite neighbours
        onView(ViewMatchers.withId(R.id.list_favorite_neighbours)).check(matches(hasChildCount(ir)));
        }



    /**
     * on vérifie qu'en appuyant sur un voisin dans le recyclerView c'est bien son nom qui s'affiche dans les details, donc qu'il existe
     */
    @Test
    public void detailNeighbourFirstnameExist() {
        //on recupere le voisin en position 2
        Neighbour neighbour = mApiService.getNeighbours().get(1);
        //sur la vue qui affiche la liste des voisins, on effectue une action: on click sur le 2nd (index 1)
        onView(withId(R.id.list_neighbours)).perform(actionOnItemAtPosition(1, click()));
        //a lire a l'enver: on verifie que le texte du nom correspond au texte de l'id du firstname du voisin selectionné
        onView(withId(R.id.detail_firstname)).check(matches(withText(neighbour.getName())));
    }

}//fin tests
