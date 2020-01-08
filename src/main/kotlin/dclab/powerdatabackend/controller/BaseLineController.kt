package dclab.powerdatabackend.controller

import com.alibaba.fastjson.JSONObject
import com.squareup.okhttp.MediaType
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import dclab.powerdatabackend.dao.BaselineMapper
import dclab.powerdatabackend.model.Baseline
import dclab.powerdatabackend.util.Constants
import dclab.powerdatabackend.util.ExcelOp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.MessageDigest
import java.util.concurrent.TimeUnit
import javax.xml.bind.DatatypeConverter

@CrossOrigin
@RestController
@RequestMapping("/api")
class BaseLineController {
    val jsonType = MediaType.parse("application/json; charset=utf-8")
    @Autowired
    lateinit var baseLine: BaselineMapper

    @RequestMapping("/baseline")
    fun baseLine(@RequestBody data: Map<String, String>): String {
        val factory = data["factory"]
        val line = data["line"]
        val device = data["device"]
        val year = data["year"]
        val month = data["month"]!!.toInt()+1
        val day = data["day"]
//        平常都用英文名了
        var measurePoint = data["measurePoint"]
//        measurePoint = ExcelOp.getMeasurePointEnglishName(measurePoint)
        val allString = factory + line + device + measurePoint + year + month + day
        val md = MessageDigest.getInstance("MD5")
        md.update(allString.toByteArray())
        val stringHash = (DatatypeConverter.printHexBinary(md.digest())).toLowerCase()
        val allList = baseLine.selectAll()
        var result: Baseline? = null
        for (i in allList) {
            if (i.hash == stringHash) {
                result = i
                break

            }
        }
        println("\n-----------baseLine接收参数------------")
        println("/${factory}/${line}/${device}/${measurePoint}/${year}/${month}/${day}")
        println("-----------baseLine接收参数------------")
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
            jsonObject.put("year", year)
            jsonObject.put("month", month)
            jsonObject.put("day", day)
            jsonObject.put("hashname", stringHash)
            val requestBody = com.squareup.okhttp.RequestBody.create(jsonType, jsonObject.toJSONString())
            val apiURL = "${Constants.ALGORITHM_URL}/algorithm/baseline"
            val request = Request.Builder().url(apiURL).addHeader("Content-Type", "application/json;charset=utf-8").post(requestBody).build()
            val re = client.newCall(request).execute()
            val data = JSONObject.parseObject(re.body().string())
            val allList = baseLine.selectAll()
            for (i in allList) {
                if (i.hash == data["msg"]) {
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