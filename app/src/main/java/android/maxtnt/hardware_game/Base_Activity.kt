package android.maxtnt.hardware_game

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.maxtnt.hardware_game.addition_request.Request_cons
import android.maxtnt.hardware_game.bluetooth.BluetoothService
import android.maxtnt.hardware_game.user.User_con
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.maxtnt.hardware_game.constant.hanlder_message
import android.os.Message
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.ListView
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener


open class Base_Activity : AppCompatActivity() {
    lateinit var user: User_con
    val handler=object : Handler(){
        override fun dispatchMessage(msg: Message?) {
            when(msg?.what){
                hanlder_message.BLUE_FINISHED->{
                    (findViewById(R.id.blue_gridview) as ListView).let{
                        //Log.v("bluetooth_get","called")
                        it.adapter= blue_service.blue_adp
                    }
                }
                else ->{

                }
            }
        }
    }
    lateinit var blue_service: BluetoothService
    get
    companion object {
        lateinit var mcontext: Context
    }
    val permission_listener=object : PermissionListener{
        override fun onPermissionDenied(response: PermissionDeniedResponse?) {

        }

        override fun onPermissionGranted(response: PermissionGrantedResponse?) {

        }

        override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest?, token: PermissionToken?) {

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        mcontext =this
        user=User_con(this).let {
            it.read_stack()
            it.read_user()
            it
        }
        super.onCreate(savedInstanceState)
        /*
          Dexter.withActivity(this)
	    .withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(permission_listener).check()
         */
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_COARSE_LOCATION).withListener(permission_listener).check()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode){
                Request_cons.REQUEST_ENABLE_BT ->{
                when (resultCode){
                    Activity.RESULT_OK ->{
                        blue_service.connect_rasp()
                    }
                    Activity.RESULT_CANCELED ->{

                    }
                    else ->{

                    }
                }
            }
            Request_cons.REQUEST_ENABLE_BT_DISCOVERY->{
                when (resultCode){
                    Activity.RESULT_OK ->{
                        blue_service.start_discovery()
                    }
                    Activity.RESULT_CANCELED ->{

                    }
                    else ->{

                    }
                }
            }
            else ->{

            }
        }
    }

    override fun onStop() {
        blue_service.close()
        super.onStop()
    }

}