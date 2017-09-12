package android.maxtnt.hardware_game.user

import android.content.Context
import android.content.SharedPreferences
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.maxtnt.hardware_game.constant.init_data
import android.os.Bundle
import android.os.Parcelable
import android.util.Base64
import android.util.Log
import org.json.JSONArray
import org.json.JSONObject
import java.io.Serializable


class User_con(context: Context) :Serializable {
    val pref: SharedPreferences=context.getSharedPreferences("user_info",MODE_PRIVATE)
    get
    val stack_obj: ArrayList<Pair<String, Double>> = ArrayList<Pair<String, Double>>()
    get
    var usr_obj:  ArrayList<Pair<String, String>> = ArrayList<Pair<String, String>>()
    get
    public val shop_info = HashMap<String,Int>()
    get
    init {
        pref.apply {
            if(getBoolean("first",true)){
                setUser("")
                setStack("")
                edit().putBoolean("first",false).apply()
            }
        }
    }
    fun setUser(str: String){
        pref.let{
            var output = ""
            if(str.length==0){
                output+=init_data.user_info.toString()
            }else{
                output+=str
            }
            it.edit().putString("user_info",output).apply()
        }
    }
    fun setStack(str: String){
        pref.let{
            var output = ""
            if(str.length==0){
                output+=init_data.stack_info.toString()
            }else{
                output+=str
            }
            it.edit().putString("stack_info",output).apply()
        }
    }
    fun read_user(){
        pref.getString(User_Const.send_list[1]+"_info",null).let{
            JSONObject(it).getJSONArray(User_Const.send_list[1])?.let{
                var count=0
                while (!it.isNull(count)){
                    val data=it.getJSONObject(count)
                    usr_obj.add(Pair(data.getString("name"),data.getString("content")))
                    count+=1
                }
            }
        }
    }fun read_stack(){
        pref.getString(User_Const.send_list[0]+"_info",null).let{
            JSONObject(it).getJSONArray(User_Const.send_list[0])?.let{
                var count=0
                while (!it.isNull(count)){
                    val data=it.getJSONObject(count)
                    stack_obj.add(Pair(data.getString("name"),data.getDouble("num")))
                    count+=1
                }
            }
        }
    }
    fun get_image(): Bitmap?{
        return pref.getString(User_Const.send_list[1]+"_info",null).let{
            var bitmap: Bitmap?=null
            if(!JSONObject(it).isNull(User_Const.send_list[2])){
                JSONObject(it).getString(User_Const.send_list[2])?.let{
                    Base64.decode(it,Base64.DEFAULT).let{
                        bitmap=BitmapFactory.decodeByteArray(it,0,it.size)
                    }
                }
            }
            bitmap
        }
    }
}