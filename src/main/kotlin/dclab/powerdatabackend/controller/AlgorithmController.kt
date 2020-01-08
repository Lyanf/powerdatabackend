package dclab.powerdatabackend.controller

import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.squareup.okhttp.MediaType
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import dclab.powerdatabackend.dao.AlgorithmresultMapper
import dclab.powerdatabackend.model.Algorithmresult
import dclab.powerdatabackend.util.Constants
import dclab.powerdatabackend.util.ExcelOp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.File
import java.security.MessageDigest
import java.util.concurrent.TimeUnit
import javax.xml.bind.DatatypeConverter

@CrossOrigin
@RestController
@RequestMapping("/api")
class AlgorithmController {
    val formType = MediaType.parse("multipart/form-data")
    val jsonType = MediaType.parse("application/json; charset=utf-8")
    @Autowired
    lateinit var algorithmresult: AlgorithmresultMapper

    @RequestMapping("/predict")
    fun predict(@RequestBody data: String): String {
        val data:JSONObject = JSONObject.parseObject(data)
        val factory = data["factory"].toString()
        val line = data["line"].toString()
        val device = data["device"].toString()
        var measurePoint = data["measurePoint"].toString()
        var timeRange: JSONArray = (data["date"] as JSONArray?)!!
        val t1:String = (timeRange[0] as String).substring(range = IntRange(0,9))
        val t2:String = (timeRange[1] as String).substring(range = IntRange(0,9))
        val allString = factory + line + device + measurePoint + t1 + t2
        val md = MessageDigest.getInstance("MD5")
        md.update(allString.toByteArray())
        val stringHash = (DatatypeConverter.printHexBinary(md.digest())).toLowerCase()
        val allList = algorithmresult.selectAll()
        var result: Algorithmresult? = null
        for (i in allList) {
            if (i.hashstr == stringHash) {

                result = i
                break
            }
        }
        println("\n-----------预测模块接收参数------------")
        println("/${factory}/${line}/${device}")
        println("-----------预测模块接收参数------------")
        if (result == null) {

//            发出http请求
            val client = OkHttpClient()
            client.setConnectTimeout(0, TimeUnit.SECONDS); // connect timeout
            client.setReadTimeout(0, TimeUnit.SECONDS);    // socket timeout
            val jsonObject = JSONObject()
            jsonObject.put("factory", factory)
            jsonObject.put("line", line)
            jsonObject.put("device", device)
            jsonObject.put("measurePoint", measurePoint)
            jsonObject.put("start", t1)
            jsonObject.put("end", t2)
            jsonObject.put("hashname", stringHash)
            val requestBody = com.squareup.okhttp.RequestBody.create(jsonType, jsonObject.toJSONString())
            val apiURL = "${Constants.ALGORITHM_URL}/algorithm/predict"
            val request = Request.Builder().url(apiURL).addHeader("Content-Type", "application/json;charset=utf-8").post(requestBody).build()
            val re = client.newCall(request).execute()
            val data = JSONObject.parseObject(re.body().string())
            val allList = algorithmresult.selectAll()
            for (i in allList) {
                if (i.hashstr == data["msg"]) {
                    result = i
                    break
                }
            }
            if(data["status"] != "success"){
                return data.toJSONString()
            }
        }

        return result!!.json


    }
}
