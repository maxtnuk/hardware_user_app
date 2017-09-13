package android.maxtnt.hardware_game.bluetooth

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.app.Activity
import android.app.AlertDialog
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.bluetooth.le.*
import android.content.BroadcastReceiver
import android.content.Intent
import android.maxtnt.hardware_game.Base_Activity
import android.maxtnt.hardware_game.addition_request.Request_cons
import android.maxtnt.hardware_game.user.User_Const
import android.maxtnt.hardware_game.user.User_con
import android.os.Handler
import android.util.Log
import android.widget.Toast
import java.io.InputStream
import java.io.OutputStream
import java.nio.charset.Charset
import java.util.*
import kotlin.collections.ArrayList
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.content.ContentValues.TAG
import android.content.Context
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.maxtnt.hardware_game.R
import android.maxtnt.hardware_game.adaptor.recycle_blue
import android.maxtnt.hardware_game.constant.hanlder_message
import android.maxtnt.hardware_game.diff.blueDiff
import android.os.Bundle
import android.os.Message
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import app.akexorcist.bluetotohspp.library.BluetoothSPP
import app.akexorcist.bluetotohspp.library.BluetoothState
import org.json.JSONObject
import java.io.IOException
import kotlin.collections.HashSet
import kotlin.collections.LinkedHashSet


class BluetoothService(activity: Activity,handler: Handler,user: User_con) {
    // Debugging
    private val TAG = "BluetoothService"
    private val bt = BluetoothSPP(activity);
    var device_count=0
    var registered=true;
    var cancle_check=false
    get
    companion object {
        private val myuuid= UUID.fromString("ec79da00-853f-11e4-b4a9-0800200c9a66")
    }
    private val bluetooth_adp=BluetoothAdapter.getDefaultAdapter()
    //private val btdevice_list: BluetoothLeScanner =btAdapter.bluetoothLeScanner
    private val blue_devices: LinkedHashSet<BluetoothDevice> = LinkedHashSet<BluetoothDevice>()
    val blue_adp: recycle_blue= recycle_blue(blue_devices).apply {
        cbitem=object: recycle_blue.Item{
            override fun ClickName(holder: recycle_blue.ItemViewHolder,Postion: Int){
                bluetooth_adp.isDiscovering.let {
                    if(it){
                        bluetooth_adp?.cancelDiscovery()
                    }
                }
                holder.apply{
                    self.setEnabled(false)
                    device_name.setTextColor(R.color.whitegray)
                }
                //before_connect(blue_devices.elementAt(Postion))
                before_connect(blue_devices.elementAt(Postion))
            }
        }
    }

    private val mActivity: Activity=activity
    private val mHandler: Handler = handler
    private val user: User_con=user
    public var btspp: BluetoothSPP? = null
    get


    val mReceiver = object: BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) {
            p1?.getAction()?.let{
                when(it){
                    BluetoothAdapter.ACTION_DISCOVERY_STARTED ->{
                        device_count=0
                        Toast.makeText(mActivity,"searching...",Toast.LENGTH_SHORT).show()
                        blue_devices.clear()
                        blue_adp.notifyDataSetChanged()
                    }
                    BluetoothAdapter.ACTION_DISCOVERY_FINISHED->{
                        Toast.makeText(mActivity,"search finish",Toast.LENGTH_SHORT).show()
                    }
                    BluetoothDevice.ACTION_FOUND->{
                        Log.v("bluetooth_get","find it")
                        p1.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)?.let {
                            device->
                            blue_devices.add(device)
                            blue_adp.notifyDataSetChanged()
                        }
                    }
                    else -> {}
                }
            }
        }
    }
    init {
        IntentFilter().let {
            it.addAction(BluetoothDevice.ACTION_FOUND)
            it.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED)
            it.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)

            mActivity.registerReceiver(mReceiver,it)
        }
    }
    fun check_descovery(){
        if(btspp==null){
            initialize_blue()
        }
        btspp?.isBluetoothEnabled?.let{
            if(it){
                mActivity.startActivityForResult(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE),Request_cons.REQUEST_ENABLE_BT_DISCOVERY)
            }else{
                start_discovery()
            }
        }
    }
    fun start_discovery(){
        bluetooth_adp.startDiscovery()
    }
    fun initialize_blue(){
        btspp= BluetoothSPP(mActivity).apply {
            setBluetoothConnectionListener(object :BluetoothSPP.BluetoothConnectionListener{
                override fun onDeviceDisconnected() {

                }

                override fun onDeviceConnected(name: String?, address: String?) {
                    //Toast.makeText(mActivity,address,Toast.LENGTH_SHORT).show()
                }

                override fun onDeviceConnectionFailed() {

                }

            })
            setOnDataReceivedListener { data, message ->

            }
            setAutoConnectionListener(object: BluetoothSPP.AutoConnectionListener{
                override fun onAutoConnectionStarted() {

                }

                override fun onNewConnection(name: String?, address: String?) {
                    Toast.makeText(mActivity,address,Toast.LENGTH_SHORT).show()
                }

            })

        }
    }
    fun connect_rasp(){
        if(bluetooth_adp.isDiscovering){
            bluetooth_adp.cancelDiscovery()
        }
        initialize_blue()
        btspp?.connect(Bluetooth_cons.rasp_device)
        cancle_check=false
        //before_connect(btAdapter.getRemoteDevice(Bluetooth_cons.rasp_device))
    }

    fun check_device(){
        btspp?.isBluetoothEnabled?.let{
            if(it){
                mActivity.startActivityForResult(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE),Request_cons.REQUEST_ENABLE_BT)
            }else{
                //connect_rasp()
            }
        }
    }
    fun before_connect(remote_device: BluetoothDevice) {
      remote_device?.let{
          device->
          if(btspp==null){
              initialize_blue()
          }
          btspp?.let{
              it.setupService()
              it.startService(BluetoothState.DEVICE_ANDROID);
              bluetooth_adp.isDiscovering.let {
                  if(it){
                      bluetooth_adp?.cancelDiscovery()
                  }
              }
              Log.v("bluetooth_get",remote_device.address)
              it.connect(remote_device.address)
          }
          cancle_check=false
        }
    }
    fun close(){
        Log.v("bluetooth_get","get")
        bluetooth_adp.isDiscovering.let {
            if(it){
                   bluetooth_adp?.cancelDiscovery()
            }
        }
        //cnthread?.close()
        if(!cancle_check){
            btspp?.disconnect()
            btspp?.stopService()
            cancle_check=true;
        }
        //btdevice_list.stopScan(bt_cb)
        /*

         */
        if(registered){
            mActivity.unregisterReceiver(mReceiver)
            registered=false
        }
        //Toast.makeText(mActivity,"unregist complete",Toast.LENGTH_SHORT).show()
    }
    fun send_shop(){
        JSONObject().let{
            content->
            for (stacks in user.shop_info){
                JSONObject().let{
                    it.put(stacks.key,stacks.value)
                    content.put("shop",it)
                }
            }
            //Toast.makeText(mActivity,"sended",Toast.LENGTH_SHORT).show()
            (content.toString()+Bluetooth_cons.delimitor.toString()).let{
                btspp?.send(it,true)
            }
        }
    }
}