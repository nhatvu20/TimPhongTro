package com.example.timphongtro.SearchPage;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.timphongtro.ChungCuActivity.ChungCuFragment;
import com.example.timphongtro.SearchPage.TroFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new TroFragment();
            case 1:
                return new ChungCuFragment();
            default:
                return new TroFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
