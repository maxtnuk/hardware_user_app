package android.maxtnt.hardware_game.adaptor

import android.content.Context
import android.maxtnt.hardware_game.R
import android.view.View
import android.widget.EditText
import android.widget.TextView
import co.ceryle.fitgridview.FitGridAdapter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.LinkedHashMap

/**
 * Created by maxtnt on 17. 8. 23.
 */
class detail_view_adp(context: Context?, itemId: Int,data: ArrayList<Pair<String, String>>) : FitGridAdapter(context, itemId)
{
    val detail_array = HashMap<Int,Pair<TextView,EditText>>()
    val detail_data=data
    override fun onBindView(position: Int, view: View?) {
        if(position< detail_data.size){
            view?.let{
                val detail_text = it.findViewById(R.id.message_title) as TextView
                val detail_content = it.findViewById(R.id.message_content) as EditText

                detail_text.setText(detail_data[position].first)
                detail_content.setText(detail_data[position].second)
                detail_array.put(position, Pair(detail_text,detail_content))
            }
        }
    }
}