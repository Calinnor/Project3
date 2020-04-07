
package com.openclassrooms.entrevoisins.neighbour_list;

import android.os.SystemClock;

import androidx.test.espresso.contrib.RecyclerViewActions;
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
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.contrib.ViewPagerActions.scrollRight;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
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

    // This is fixed
    private static int ITEMS_COUNT = 12;

    private ListNeighbourActivity mActivity;
    private NeighbourApiService mApiService = DI.getNeighbourApiService();
    private List<Neighbour> neighbours = mApiService.getNeighbours();
    private List<Neighbour> favoriteNeighbours = mApiService.getNeighbourIsFavorite();


    @Rule
    public ActivityTestRule mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);

    @Before
    public void setUp() {
        mActivity = (ListNeighbourActivity) mActivityRule.getActivity();
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

//original code below for delete action button
//    /**
//     * When we delete an item, the item is no more shown
//     */
//    @Test
//    public void myNeighboursList_deleteAction_shouldRemoveItem() {
//        // Given : We remove the element at position 2
//        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT));
//        // When perform a click on a delete icon
//        onView(ViewMatchers.withId(R.id.list_neighbours))
//                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
//        // Then : the number of element is 11
//        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT - 1));
//    }
    /**
     //     * aprés click suppression on verifie que la vue voisin supprimé ne correspond pas a la vue voisin au meme index apres suppression ?
     //     */
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
     * on vérifie qu'en appuyant sur la liste des favoris, un element du detail s'affiche
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
        //base favorite list is empty. add rondom favorite neighbour (1-12)
        Random r = new Random();
        int ri = r.nextInt(11)+1;
        for(int i = 0; i < ri; i++){
            mApiService.getNeighbours().get(i).setFavorite(true);
        }//ici ri voisins sont favoris dans la liste neighbours
        for(int i = 0; i < ri; i++){
            favoriteNeighbours = mApiService.getNeighbourIsFavorite();
        }//ici ri voisins sont dans la liste favoriteNeighbours qui sera instanciée

        //on click sur la vue "favorite" affichant la liste des favoris...
        // a mon avis c'est ce clik qui ne fonctionne pas puisque la list favorite contient bien ri favoris
        //malheureusement il y a 0...affichés donc on affiche quoi ???..pas la liste de favorite puisqu'elle n'est pas vide.
        onView(ViewMatchers.withText("Favorites")).perform(click());

        onView(/*withText("Favorites"))*/withId(R.id.list_favorite_neighbours)).check(withItemCount(ri));
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
