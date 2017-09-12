package android.maxtnt.hardware_game

import android.content.Intent
import android.maxtnt.hardware_game.constant.hanlder_message
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity

class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Intent(this,MainActivity::class.java).let{
            this.startActivity(it)
        }
        finish()
    }
}