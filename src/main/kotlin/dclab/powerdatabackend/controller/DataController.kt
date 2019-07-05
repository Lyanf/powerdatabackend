package dclab.powerdatabackend.controller

import com.alibaba.fastjson.JSON
import dclab.powerdatabackend.service.OldDataService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@CrossOrigin
@RestController
@RequestMapping("/api")
class DataController{
    @Autowired
    private val dataService:OldDataService? = null

    @RequestMapping("/searchData",method = arrayOf(RequestMethod.POST))
    fun searchData(@RequestBody data:String):Any?{
        data class Data(var ilocation:String,var imeasurePoint:String,var idate:Array<String>?)
        val dataObj = JSON.parseObject(data,Data::class.javaObjectType)
        return dataService!!.search(dataObj.imeasurePoint, dataObj.ilocation,dataObj.idate?.get(0),dataObj.idate?.get(1))
    }
}
