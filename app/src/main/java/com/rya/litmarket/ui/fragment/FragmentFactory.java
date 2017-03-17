package com.rya.litmarket.ui.fragment;

import java.util.HashMap;

/**
 * Created by ryanyans32 on 2017/3/11.
 * <p>
 * Reach me : http://ryanyans.github.io
 * Email : ryanyans32@gmail.com
 */
public class FragmentFactory {
    private static final int HOME_FRAGMENT = 0;
    private static final int APP_FRAGMENT = 1;
    private static final int GAME_FRAGMENT = 2;
    private static final int FOCUS_FRAGMENT = 3;
    private static final int RECOMM_FRAGMENT = 4;
    private static final int SORT_FRAGMENT = 5;
    private static final int RANKING_FRAGMENT = 6;

    private static HashMap<Integer, BaseFragment> mFragmentMap = new HashMap<>();

    public static BaseFragment creatFragment(int currentItem) {
        BaseFragment fragment = mFragmentMap.get(currentItem);
        if (fragment == null) {
            switch (currentItem) {
                case HOME_FRAGMENT:
                    fragment = new HomeFragment();
                    break;
                case APP_FRAGMENT:
                    fragment = new AppFragment();
                    break;
                case GAME_FRAGMENT:
                    fragment = new GameFragment();
                    break;
                case FOCUS_FRAGMENT:
                    fragment = new FocusFragment();
                    break;
                case RECOMM_FRAGMENT:
                    fragment = new RecommFragment();
                    break;
                case SORT_FRAGMENT:
                    fragment = new SortFragment();
                    break;
                case RANKING_FRAGMENT:
                    fragment = new RankingFragment();
                    break;
                default:
                    break;
            }
            mFragmentMap.put(currentItem, fragment);
        }
        return fragment;
    }
}
