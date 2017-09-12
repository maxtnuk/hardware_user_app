package android.maxtnt.hardware_game.constant

import android.support.v4.app.Fragment
import android.maxtnt.hardware_game.fragment.basic_frag
import android.maxtnt.hardware_game.fragment.game_frag
import android.maxtnt.hardware_game.fragment.store_frag
import kotlin.reflect.KClass

class Tab_menus {
    companion object {
        val tab_menus: ArrayList<Pair<String, Fragment>> = ArrayList<Pair<String, Fragment>>().let { content->
            content.add(Pair("기본 정보",basic_frag()));
            content.add(Pair("스토어",store_frag()))
            content.add(Pair("게임",game_frag()))
            content
        }
    }
}