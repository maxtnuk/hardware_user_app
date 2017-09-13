package android.maxtnt.hardware_game.adaptor

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.maxtnt.hardware_game.R
import android.maxtnt.hardware_game.Base_Activity
import android.maxtnt.hardware_game.constant.games
import android.maxtnt.hardware_game.user.User_con
import android.view.View
import android.widget.TextView
import co.ceryle.fitgridview.FitGridAdapter
import java.util.*


class game_view_adp(context: Context?, itemId: Int,user: User_con) : FitGridAdapter(context, itemId)
{
    val context=context
    val user_data=user
    override fun onBindView(position: Int, view: View?) {
        val rnd = Random()
        view?.let{
            if(position<games.game_list.size){
                games.game_list[position].let { game_name ->

                    (view.findViewById(R.id.item_name) as TextView).setText(game_name)
                    val item_view=view.findViewById(R.id.item_bk);
                    item_view.setOnClickListener {
                        view: View? ->
                        //(context as Base_Activity).blue_service.check_device()
                    }
                    val shape: GradientDrawable =item_view.background as GradientDrawable
                    val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
                    shape.setColor(color)
                    item_view.background=shape
                }
            }else{
                it.visibility-View.INVISIBLE
            }
        }
    }
}