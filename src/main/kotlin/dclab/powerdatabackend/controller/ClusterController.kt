package dclab.powerdatabackend.controller

import com.alibaba.fastjson.JSONObject
import com.squareup.okhttp.MediaType
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import dclab.powerdatabackend.dao.ClusterMapper
import dclab.powerdatabackend.model.Cluster
import dclab.powerdatabackend.util.Constants
import dclab.powerdatabackend.util.ExcelOp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.MessageDigest
import javax.xml.bind.DatatypeConverter

@CrossOrigin
@RestController
@RequestMapping("/api")
class ClusterController {
    val jsonType = MediaType.parse("application/json; charset=utf-8")
    @Autowired
    lateinit var clusterMapper: ClusterMapper

    @RequestMapping("/cluster")
    fun predict(@RequestBody data: Map<String, String>): String {
        val factory = data["factory"]
        val line = data["line"]
        val device = data["device"]
//        平常都用英文名了
        var measurePoint = data["measurePoint"]
        measurePoint = ExcelOp.getMeasurePointEnglishName(measurePoint)
        val allString = factory + line + device + measurePoint
        val md = MessageDigest.getInstance("MD5")
        md.update(allString.toByteArray())
        val stringHash = (DatatypeConverter.printHexBinary(md.digest())).toLowerCase()
        val allList = clusterMapper.selectAll()
        var result: Cluster? = null
        for (i in allList) {
            if (i.hash == stringHash) {
                result = i
                break
            }
        }
        println("\n-----------cluster接收参数------------")
        println("/${factory}/${line}/${device}/${measurePoint}")
        println("-----------cluster接收参数------------")
        if (result == null) {
//            发出http请求
            val client = OkHttpClient()
            val jsonObject = JSONObject()
            jsonObject.put("factory", factory)
            jsonObject.put("line", line)
            jsonObject.put("device", device)
            jsonObject.put("measurePoint", measurePoint)
            val requestBody = com.squareup.okhttp.RequestBody.create(jsonType, jsonObject.toJSONString())
            val apiURL = "${Constants.ALGORITHM_URL}/algorithm/cluster"
            val request = Request.Builder().url(apiURL).addHeader("Content-Type", "application/json;charset=utf-8").post(requestBody).build()
            client.newCall(request).execute()
            return ""
        } else {
            return result.json
        }
    }


}