package android.maxtnt.hardware_game.fragment

import android.graphics.Bitmap
import android.maxtnt.hardware_game.R
import android.maxtnt.hardware_game.user.User_con
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

open class base_frag : Fragment() {
    var layout_id : Int? =null
    var layout_setting: (()->Unit)? = null
    lateinit var user: User_con
    var user_image: Bitmap?=null
    var cur_view: View? = null
    set(value) {
        if(value!=null){
            field=value as View
        }
    }
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //user=arguments.getSerializable("user") as User_con
        user=User_con(activity).apply {
            read_user()
            read_stack()
            if(user_image==null){
                user_image=get_image()
            }
        }
        if(cur_view==null){
            cur_view=layout_id?.let { inflater?.inflate(it,container,false) }
            return cur_view.apply {
                layout_setting?.invoke()
            }
        }else{
            return cur_view
        }
    }
}