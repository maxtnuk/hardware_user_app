package android.maxtnt.hardware_game.adaptor

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.maxtnt.hardware_game.R
import android.maxtnt.hardware_game.constant.games
import android.maxtnt.hardware_game.constant.stack_infos
import android.view.View
import android.widget.TextView
import co.ceryle.fitgridview.FitGridAdapter
import com.akexorcist.roundcornerprogressbar.IconRoundCornerProgressBar

/**
 * Created by maxtnt on 17. 8. 23.
 */
class stack_view_adp(context: Context?, itemId: Int,array: ArrayList<Pair<String, Double>>) : FitGridAdapter(context, itemId)
{
    var item_array: ArrayList<Pair<String, Double>> = array
    override fun onBindView(position: Int, view: View?) {
        view?.let{
            if(position< item_array.size){
                val stack_title  = view.findViewById(R.id.stack_title) as TextView
                val stack_bar = view.findViewById(R.id.stack_bar) as IconRoundCornerProgressBar
                val items=item_array[position]

                stack_title.setText(items.first)
                stack_bar.max=stack_infos.max_num.toFloat()
                stack_bar.progress = items.second.toFloat()
                stack_bar.secondaryProgress=items.second.toFloat()
                stack_bar.progressColor=stack_infos.stack_color[position]
                stack_bar.progressBackgroundColor=stack_infos.stack_bg_color
            }
        }
    }
}