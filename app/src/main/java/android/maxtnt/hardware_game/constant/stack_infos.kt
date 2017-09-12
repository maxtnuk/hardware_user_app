package android.maxtnt.hardware_game.constant

import android.maxtnt.hardware_game.R
import android.maxtnt.hardware_game.fragment.basic_frag
import android.maxtnt.hardware_game.fragment.game_frag
import android.maxtnt.hardware_game.fragment.store_frag
import android.maxtnt.hardware_game.user.User_con
import android.support.v4.app.Fragment

class stack_infos {
    companion object {
        val max_num = 100
        val stack_bg_color =R.color.progress
        val stack_color = listOf<Int>(R.color.health,R.color.attack,R.color.defense,R.color.speed);
    }
}