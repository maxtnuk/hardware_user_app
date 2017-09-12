package android.maxtnt.hardware_game

import android.maxtnt.hardware_game.bluetooth.BluetoothService
import android.maxtnt.hardware_game.constant.Tab_menus
import android.maxtnt.hardware_game.fragment.basic_frag
import android.maxtnt.hardware_game.pager.home_pager
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.widget.RelativeLayout

class MainActivity : Base_Activity() {
    /*
      val controlBar: RelativeLayout? by bindOptionalView(R.id.control_bar)
    val tabMenu: TabLayout? by bindOptionalView(R.id.tab_menu)
    val detailViewpage: ViewPager? by bindOptionalView(R.id.detail_viewpage)
     */
    private val controlBar: RelativeLayout? = null
    private var tabMenu: TabLayout? =null
    private var detailViewpage: ViewPager? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        blue_service = BluetoothService(this,handler,user)
        initview()
    }

    override fun onDestroy() {
        (Tab_menus.tab_menus[0].second as basic_frag).save_user()
        super.onDestroy()
    }
    fun initview(){
        tabMenu = findViewById(R.id.tab_menu) as TabLayout
        tabMenu?.let { tabMenu ->
            for (menu_title in Tab_menus.tab_menus){
                tabMenu.addTab(tabMenu.newTab().setText(menu_title.first))
            }
            tabMenu.tabGravity = TabLayout.GRAVITY_FILL
        }
        detailViewpage = findViewById(R.id.detail_viewpage) as ViewPager
        detailViewpage?.let { viewPager ->
            val home_adaptor=home_pager(supportFragmentManager).apply {
                //bundle.putSerializable("user",user)
            }
            viewPager.apply {
                adapter=home_adaptor
                addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabMenu))
            }
            tabMenu?.let {
                tabLayout: TabLayout ->
                tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
                    override fun onTabSelected(tab: TabLayout.Tab) {
                        viewPager.setCurrentItem(tab.position)
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab) {

                    }

                    override fun onTabReselected(tab: TabLayout.Tab) {

                    }
                })
            }
        }
    }
}
