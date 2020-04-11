package com.openclassrooms.entrevoisins.ui.neighbour_list;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.Arrays;
import java.util.List;


public class ListNeighbourPagerAdapter extends FragmentPagerAdapter {

//    public ListNeighbourPagerAdapter(FragmentManager fm) {
//        super(fm);
//    }
    //creation des instances de classes
//    private NeighbourFragment neighbourFragment =  NeighbourFragment.newInstance();
//    private FavoriteNeighbourFragment favoriteNeighbourFragment = FavoriteNeighbourFragment.newInstance();
//
//    /**
//     * getItem is called to instantiate the fragment for the given page.
//     * @param position
//     * @return
//     */
    //utilisable avec des instances de classe: une instance est crée a la place d'une instance cree a chaque appel
//    @Override
//    public Fragment getItem(int position) {
//        switch (position) {
//            case 0:
//                return neighbourFragment;
//            case 1:
//                return favoriteNeighbourFragment;
//            default:
//                return null;
//        }
//    }

    //utilisable avec une instance pour chaque fragment: mauvais car demande beaucoup de ressources
 //   @Override
//    public Fragment getItem(int position) {
//        switch (position) {
//            case 0:
//                return NeighbourFragment.newInstance();
//            case 1:
//                return FavoriteNeighbourFragment.newInstance();
//            default:
//                return null;
//        }
//    }

    //utilisable avec un nombre d'instances determiné voir plus haut
//    /**
//     * get the number of pages
//     * @return
//     */
//    @Override
//    public int getCount() {
//        return 2;
//    }


    private List<Fragment> fragments = Arrays.asList(
            NeighbourFragment.newInstance(),
            FavoriteNeighbourFragment.newInstance()
    );

    public ListNeighbourPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * getItem is called to instantiate the fragment for the given page. return fragment is this at position in list
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {//
        return fragments.get(position);
    }

    /**
     * get the number of pages return list size
     * @return
     */
    @Override
    public int getCount() {
        return fragments.size();
    }
}