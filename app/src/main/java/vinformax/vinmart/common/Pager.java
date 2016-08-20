package vinformax.vinmart.common;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import vinformax.vinmart.fragment.DiscountTab;
import vinformax.vinmart.fragment.HotDealsTab;
import vinformax.vinmart.fragment.CategoriesTab;

/**
 * Created by Rachan on 6/27/2016.
 */
public class Pager extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public Pager(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 1:
                DiscountTab tab1 = new DiscountTab();
                return tab1;
            case 0:
                HotDealsTab tab2 = new HotDealsTab();
                return tab2;
            case 2:
                CategoriesTab tab3 = new CategoriesTab();
                return tab3;
            default:
                CategoriesTab default1 = new CategoriesTab();
                return default1;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}