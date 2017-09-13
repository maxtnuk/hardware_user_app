package android.maxtnt.hardware_game.fragment

import android.maxtnt.hardware_game.Base_Activity
import android.maxtnt.hardware_game.R
import android.maxtnt.hardware_game.constant.hanlder_message
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView

class game_frag : base_frag() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        layout_id= R.layout.game_tab;
        layout_setting={
            game_start()
            //set_gamew_view()
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }
    fun game_start(){
        (cur_view?.findViewById(R.id.gmae_start_btn) as TextView).setOnClickListener{
            view: View? ->
            if(activity is Base_Activity){
                (activity as Base_Activity).apply {
                    handler.sendEmptyMessage(hanlder_message.BLUE_FINISHED)
                    /*
                     blue_service.cnthread?.let{
                        if(it.socket.isConnected) {
                            it.send_user()
                        }
                    } ?: kotlin.run{
                        blue_service.check_device()
                    }
                     */
                    blue_service.btspp?.let{

                    } ?: kotlin.run{
                        //blue_service.check_device()
                    }
                }
            }
        }
        (cur_view?.findViewById(R.id.search_btn) as TextView).setOnClickListener {
            view: View? ->
            if(activity is Base_Activity){
                (activity as Base_Activity).apply {
                    handler.sendEmptyMessage(hanlder_message.BLUE_FINISHED)
                    blue_service.check_descovery()
                }
            }
        }
    }
}