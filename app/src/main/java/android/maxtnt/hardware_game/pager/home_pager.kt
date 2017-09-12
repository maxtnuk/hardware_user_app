package android.maxtnt.hardware_game.pager

import android.maxtnt.hardware_game.constant.Tab_menus
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class home_pager(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {
    val bundle: Bundle = Bundle()
    get

    override fun getItem(position: Int): Fragment {
       return Tab_menus.tab_menus[position].second.let { content ->
           content.arguments = bundle;
           content
       }
    }

    override fun getCount(): Int {
        return Tab_menus.tab_menus.size
    }
}