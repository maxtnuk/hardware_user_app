package android.maxtnt.hardware_game.fragment

import android.maxtnt.hardware_game.R
import android.maxtnt.hardware_game.adaptor.shop_view_adp
import android.maxtnt.hardware_game.constant.shop
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.ceryle.fitgridview.FitGridView

/**
 * Created by maxtnt on 2017-08-17.
 */
class store_frag : base_frag() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        layout_id= R.layout.store_tab;
        layout_setting={
            store()
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }
    fun store(){
        val shoper=cur_view?.findViewById(R.id.shoper) as FitGridView
        shop_view_adp(activity,R.layout.cell_item,user).let {
            shoper.setFitGridAdapter(it)
        }
        shoper.update()
    }
}