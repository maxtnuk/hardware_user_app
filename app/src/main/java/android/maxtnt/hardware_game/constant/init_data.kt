package android.maxtnt.hardware_game.constant

import org.json.JSONArray
import org.json.JSONObject

/**
 * Created by maxtnt on 17. 8. 24.
 */
class init_data {
    companion object {
        val stack_base: ArrayList<Pair<String, Double>> = ArrayList<Pair<String, Double>>().let { content->
            content.add(Pair("체력", 50.0));
            content.add(Pair("공격력", 20.0))
            content.add(Pair("방어력", 30.0))
            content.add(Pair("이동속도",40.0))
            content
        }
        val user_base: ArrayList<Pair<String, String>> = ArrayList<Pair<String, String>>().let { content->
            content.add(Pair("인삿말", "안녕 여기 처음이지"));
            content.add(Pair("자기 소개", "나로 말할 것 같으면 인간이야"))
            content
        }
        val stack_info: JSONObject=JSONObject().let {
            var stacks=JSONArray().let {
                jsonarray ->
                for(item in stack_base){
                    JSONObject().let {
                        it.put("name",item.first)
                        it.put("num",item.second)
                        jsonarray.put(it)
                    }
                }
                jsonarray
            }
            it.put("stack",stacks)
        }
        val user_info: JSONObject=JSONObject().let {
            var stacks=JSONArray().let {
                jsonarray ->
                for(item in user_base){
                    JSONObject().let {
                        it.put("name",item.first)
                        it.put("content",item.second)
                        jsonarray.put(it)
                    }
                }
                jsonarray
            }
            it.put("user",stacks)
        }
    }
}