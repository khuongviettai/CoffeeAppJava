package com.khuongviettai.coffee.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.khuongviettai.coffee.fragment.CartFragment;
import com.khuongviettai.coffee.fragment.HistoryFragment;
import com.khuongviettai.coffee.fragment.HomeFragment;
import com.khuongviettai.coffee.fragment.ProfileFragment;
import com.khuongviettai.coffee.fragment.ShopFragment;

public class MainAdapter extends FragmentStatePagerAdapter {
    public MainAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new ShopFragment();
            case 2:
                return new CartFragment();
            case 3:
                return new HistoryFragment();
            case 4:
                return new ProfileFragment();

            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}
