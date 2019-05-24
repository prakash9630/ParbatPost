package automask.nuza.prakash.parbatpost.Adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import automask.nuza.prakash.parbatpost.Fragment.Feature_news;
import automask.nuza.prakash.parbatpost.Fragment.ParvatCerofero;


/**
 * Created by prakash on 9/21/2016.
 */
public class Fragment_adapter extends FragmentStatePagerAdapter {
    public Fragment_adapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                Feature_news fn=new Feature_news();
                return fn;
            case 1:
                ParvatCerofero pc = new ParvatCerofero();
                return pc;
            case 2:
                ParvatCerofero pcc = new ParvatCerofero();
                return pcc;

        }


        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}

