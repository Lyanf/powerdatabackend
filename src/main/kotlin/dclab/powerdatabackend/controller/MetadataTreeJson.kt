package dclab.powerdatabackend.controller

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.alibaba.fastjson.JSONObject
import org.springframework.web.bind.annotation.RequestMethod
import java.io.File

@CrossOrigin
@RestController
@RequestMapping("/api")
class MetadataTreeJson {
        @RequestMapping("/metadataTreeJson",method = arrayOf(RequestMethod.POST))
        fun metadataTreeJson():String{
            val jsonFile = File("C:\\Users\\lyf\\Desktop\\南方电网\\powerdatabackend\\src\\main\\resources\\fix.json")
            val t = jsonFile.readText()
            val obj = JSONObject.parse(t)
            println(obj)
            return t
        }

}