package com.dclassics.books.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.dclassics.books.fragment.FictionFragment;
import com.dclassics.books.fragment.NonFictionFragment;

public class BooksPagerAdapter extends FragmentStateAdapter {

    public BooksPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) return new NonFictionFragment();
        return new FictionFragment();
    }

    @Override
    public int getItemCount() { return 2; }
}
