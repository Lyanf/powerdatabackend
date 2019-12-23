package dclab.powerdatabackend.controller

import com.alibaba.fastjson.JSONObject
import com.squareup.okhttp.MediaType
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import dclab.powerdatabackend.dao.AlgorithmresultMapper
import dclab.powerdatabackend.dao.ProfilefeatureMapper
import dclab.powerdatabackend.model.Algorithmresult
import dclab.powerdatabackend.model.Profilefeature
import dclab.powerdatabackend.util.Constants
import dclab.powerdatabackend.util.ExcelOp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.File
import java.security.MessageDigest
import javax.xml.bind.DatatypeConverter

@CrossOrigin
@RestController
@RequestMapping("/api")
class ProfileFeature {
    val formType = MediaType.parse("multipart/form-data")
    val jsonType = MediaType.parse("application/json; charset=utf-8")
    @Autowired
    lateinit var algorithmResult:ProfilefeatureMapper
    @RequestMapping("/profileFeature")
    fun predict(@RequestBody data: Map<String, String>): String {
        val factory = data["factory"]
        val line = data["line"]
        val device = data["device"]
        var measurePoint = data["measurePoint"]
//        measurePoint = ExcelOp.getMeasurePointEnglishName(measurePoint)
        val allString = factory + line + device + measurePoint
        val md = MessageDigest.getInstance("MD5")
        md.update(allString.toByteArray())
        val stringHash = (DatatypeConverter.printHexBinary(md.digest())).toLowerCase()
        val allList = algorithmResult.selectAll()
        var result: Profilefeature? = null
        for (i in allList) {
            if (i.hash == stringHash) {
                result = i
                break
            }
        }
        println("\n-----------画像模块接收参数------------")
        println("/${factory}/${line}/${device}")
        println("-----------画像模块接收参数------------")
        if (result == null) {
//            发出http请求
            val client = OkHttpClient()
            val jsonObject = JSONObject()
            jsonObject.put("factory", factory)
            jsonObject.put("line", line)
            jsonObject.put("device", device)
            jsonObject.put("measurePoint", measurePoint)
            val requestBody = com.squareup.okhttp.RequestBody.create(jsonType, jsonObject.toJSONString())
            val apiURL = "${Constants.ALGORITHM_URL}/algorithm/profilefeature"
            val request = Request.Builder().url(apiURL).addHeader("Content-Type", "application/json;charset=utf-8").post(requestBody).build()
            client.newCall(request).execute()
            return ""
        } else {
//            val sharedRoot = System.getenv("SHARED_ROOT") + File.separator + "predict"
//            val realFileName = "${result.result}.json"
//            val file = File(sharedRoot, realFileName)
//            val contents = file.readText()
//            println(contents)
            return result.json
        }

    }
}
