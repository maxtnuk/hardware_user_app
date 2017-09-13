package android.maxtnt.hardware_game.adaptor

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.maxtnt.hardware_game.Base_Activity
import android.maxtnt.hardware_game.R
import android.maxtnt.hardware_game.constant.shop
import android.maxtnt.hardware_game.user.User_con
import android.view.View
import android.widget.TextView
import android.widget.Toast
import co.ceryle.fitgridview.FitGridAdapter
import java.util.*

class shop_view_adp(context: Context?, itemId: Int, user: User_con) : FitGridAdapter(context, itemId)
{
    val context=context
    val user_data=user
    override fun onBindView(position: Int, view: View?) {
        view?.let{
            if(position< shop.hp_list.size){
                shop.hp_list[position].let { item_num ->
                    val hp_str="hp_"+item_num;
                    (view.findViewById(R.id.item_name) as TextView).setText(hp_str)
                    val item_view=view.findViewById(R.id.item_bk);
                    item_view.setOnClickListener {
                        view: View? ->
                        (context as Base_Activity).apply{
                            user.shop_info.put("hp", item_num)
                            /*
                            blue_service.cnthread?.let{
                                if(it.socket.isConnected){
                                    it.send_shop()
                                }
                            } ?: kotlin.run{
                                Toast.makeText(this,"블루투스에 연결해야 합니다",Toast.LENGTH_SHORT).show()
                            }
                             */
                            blue_service.btspp?.let{
                                blue_service.send_shop()
                            } ?: kotlin.run{
                                Toast.makeText(context,"블루투스가 연결이 원활하지 않습니다 ",Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }
    }
}