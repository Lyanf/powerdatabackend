package dclab.powerdatabackend.controller

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import dclab.powerdatabackend.service.DataService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.collections.ArrayList

@CrossOrigin
@RestController
@RequestMapping("/api")
class DataController{
    @Autowired
    private val dataService:DataService? = null

    @RequestMapping("/searchData",method = arrayOf(RequestMethod.POST))
    fun searchData(@RequestBody data:String):Any?{
        data class Data(var ilocation:String,var imeasurePoint:String,var idate:Array<String>?)
        val dataObj = JSON.parseObject(data,Data::class.javaObjectType)
        return dataService!!.search(dataObj.imeasurePoint, dataObj.ilocation,dataObj.idate?.get(0),dataObj.idate?.get(1))
    }
}
