package android.maxtnt.hardware_game.adaptor

import android.bluetooth.BluetoothDevice
import android.maxtnt.hardware_game.R
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class recycle_blue(device_set: LinkedHashSet<BluetoothDevice>): BaseAdapter() {
    val items=device_set
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        //Log.v("bluetooth_get","postion: "+p0)
        return (p1 ?: kotlin.run{
            LayoutInflater.from(p2?.context).inflate(R.layout.blueitem, p2, false)
        }).let{
            it.tag=ItemViewHolder(it).let {
                holder->
                items.elementAt(p0).name?.let{
                    holder.device_name.setText(it)
                } ?: kotlin.run{
                    holder.device_name.setText("없음")
                }
                holder.self.setOnClickListener {
                    cbitem.ClickName(holder,p0)
                }
            }
            it
        }
    }

    override fun getItem(p0: Int): Any {
        return items.elementAt(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
       return items.size
    }

    lateinit var cbitem:Item
    set
    open interface Item{
        fun ClickName(holder: ItemViewHolder,Position: Int)
    }
    class ItemViewHolder(view: View){
        val self=view
        val device_name : TextView = view.findViewById(R.id.item_title) as TextView
        get
    }
}