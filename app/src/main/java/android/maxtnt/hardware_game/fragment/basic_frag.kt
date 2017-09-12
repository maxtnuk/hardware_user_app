package android.maxtnt.hardware_game.fragment

import android.app.Activity
import android.app.ActivityManager
import android.maxtnt.hardware_game.R
import android.maxtnt.hardware_game.adaptor.detail_view_adp
import android.maxtnt.hardware_game.adaptor.stack_view_adp
import android.maxtnt.hardware_game.constant.init_data
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import co.ceryle.fitgridview.FitGridView
import org.json.JSONArray
import org.json.JSONObject
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.maxtnt.hardware_game.addition_request.Request_cons
import android.maxtnt.hardware_game.user.User_Const
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import java.io.ByteArrayOutputStream
import android.media.ExifInterface
import android.net.Uri
import java.net.URI


class basic_frag: base_frag() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        layout_id= R.layout.main_tab;
        layout_setting={
            set_image()
            set_stack_info()
            set_detail_info()
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }
    lateinit var detail_adp: detail_view_adp
    get
    lateinit var image: ImageView
    fun set_image(){
        image= cur_view?.findViewById(R.id.avata_photo) as ImageView
        user_image?.let{
            image.setImageBitmap(it)
        } ?: image.setImageResource(R.drawable.user_icon)
        image.setOnClickListener { view: View? ->
            val intent = Intent(Intent.ACTION_PICK)
            intent.data = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            intent.type = "image/*"
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), Request_cons.PICTURE)
        }
    }
    fun set_stack_info(){
        val stack_grid= cur_view?.findViewById(R.id.stack_info) as FitGridView
        stack_grid.let {
            it.numColumns=1
            it.numRows=user.stack_obj.size
            it.update()
            it.setFitGridAdapter(stack_view_adp(activity,R.layout.stack_item,user.stack_obj))
            it.update()
        }
    }
    fun set_detail_info(){
        val detail_info=cur_view?.findViewById(R.id.detail_info) as FitGridView
        detail_info.let {
            it.numColumns=1
            it.numRows = user.usr_obj.size
            it.update()
            detail_adp=detail_view_adp(activity,R.layout.message_box,user.usr_obj)
            it.setFitGridAdapter(detail_adp)
            it.update()
        }
    }
    fun save_user(){
        JSONObject().let {
            val stacks= JSONArray().let {
                jsonarray ->
                for (item in detail_adp.detail_array){
                    //Log.v("json_test",item.value.first.text.toString())
                    JSONObject().let {
                        it.put("name",item.value.first.text)
                        it.put("content",item.value.second.text)
                        jsonarray.put(it)
                    }
                }
                jsonarray
            }
            it.put("user",stacks)
            val base_str:String=ByteArrayOutputStream().let{
                (image.drawable as BitmapDrawable).bitmap.compress(Bitmap.CompressFormat.PNG,100,it)
                val return_str=it.toByteArray().let{
                    Base64.encodeToString(it,Base64.DEFAULT)
                }
                return_str
            }
            it.put("image",base_str)
            user.setUser(it.toString())
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode){
            Request_cons.PICTURE ->{
                when(resultCode){
                    Activity.RESULT_OK ->{
                        /*

                         */
                        MediaStore.Images.Media.getBitmap(activity.contentResolver, data?.getData())?.let{
                            image.setImageBitmap(it)
                        }
                        /*
                        (data?.data as Uri).let{
                            val path: String = activity.getContentResolver()
                                    .query(it, null, null, null, null)?.let{
                                cursor ->
                                cursor.moveToFirst()
                                cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA).let{
                                    cursor.getString(it)
                                }
                            } ?: kotlin.run{
                                it.path
                            }
                            path.let {
                                (ExifInterface(it).getAttribute(ExifInterface.TAG_ORIENTATION)?.let {
                                    Integer.parseInt(it)
                                } ?: kotlin.run{
                                    ExifInterface.ORIENTATION_NORMAL
                                }).let{
                                    when(it){
                                        ExifInterface.ORIENTATION_ROTATE_90 -> 90
                                        ExifInterface.ORIENTATION_ROTATE_180 -> 180
                                        ExifInterface.ORIENTATION_ROTATE_270 -> 270
                                        else -> 0
                                    }.let {
                                        angle->
                                        Log.v("pciture","here")
                                        MediaStore.Images.Media.getBitmap(activity.contentResolver, data.getData())?.let{
                                            bm_image->
                                            Matrix().let{
                                                it.setRotate(angle.toFloat(),bm_image.width.toFloat()/2,bm_image.height.toFloat()/2)
                                                Bitmap.createBitmap(bm_image, 0, 0, bm_image.width, bm_image.height,it, true).let{
                                                    image.setImageBitmap(it)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                         */
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
}