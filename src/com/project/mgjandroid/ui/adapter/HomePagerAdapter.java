package com.project.mgjandroid.ui.adapter;

import java.util.ArrayList;

import com.project.mgjandroid.ui.fragment.BaseFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

/**
 * 主页ViewPager适配器
 * @author jian
 *
 */
public class HomePagerAdapter extends FragmentPagerAdapter{
	private FragmentManager fm;
	private ArrayList<BaseFragment> fragments;
	public HomePagerAdapter(FragmentManager fm) {
		super(fm);
		this.fm = fm;
		fragments = new ArrayList<BaseFragment>();
	}

	@Override
	public Fragment getItem(int arg0) {
		return fragments.get(arg0);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}
	
	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		Object obj = super.instantiateItem(container, position);
		return obj;
	}
	
	public ArrayList<BaseFragment> getFragments() {
		return fragments;
	}
	
	public void notify(ArrayList<BaseFragment> fragments) {
		if (this.fragments != null) {
			FragmentTransaction ft = fm.beginTransaction();
			for (Fragment f : this.fragments) {
				ft.remove(f);
			}
			ft.commitAllowingStateLoss();
			ft = null;
			fm.executePendingTransactions();
		}
		this.fragments = fragments;
		notifyDataSetChanged();
	}

}
