package android.maxtnt.hardware_game.diff

import android.bluetooth.BluetoothDevice
import android.support.v7.util.DiffUtil

/**
 * Created by maxtnt on 17. 9. 2.
 */
class blueDiff(before: Set<BluetoothDevice>,after: Set<BluetoothDevice>): DiffUtil.Callback() {
    val before: ArrayList<BluetoothDevice> = ArrayList<BluetoothDevice>().apply{
        addAll(before)
    }
    val after: ArrayList<BluetoothDevice> = ArrayList<BluetoothDevice>().apply{
        addAll(after)
    }
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
       return before[oldItemPosition].name.equals(after[newItemPosition].name)
    }

    override fun getOldListSize(): Int {
       return before.size
    }

    override fun getNewListSize(): Int {
        return after.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return before[oldItemPosition].name.equals(after[newItemPosition].name)
    }
}