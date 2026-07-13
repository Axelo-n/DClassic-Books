package com.example.dclassicbooks.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.dclassicbooks.fragment.FictionFragment;
import com.example.dclassicbooks.fragment.NonFictionFragment;

public class BooksPagerAdapter extends FragmentStateAdapter {

    public BooksPagerAdapter(@NonNull FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return position == 0 ? new NonFictionFragment() : new FictionFragment();
    }

    @Override
    public int getItemCount() { return 2; }
}
