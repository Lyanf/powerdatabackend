package dclab.powerdatabackend.controller

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import com.alibaba.fastjson.*
import dclab.powerdatabackend.dao.AlgorithmresultMapper
import dclab.powerdatabackend.model.Algorithmresult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import java.io.File
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.MediaType
import org.springframework.http.client.MultipartBodyBuilder
import java.security.MessageDigest
import javax.xml.bind.DatatypeConverter

@CrossOrigin
@RestController
@RequestMapping("/api")
class AlgorhtimController{
    val formType = MediaType.parse("multipart/form-data")
    val jsonType = MediaType.parse("application/json; charset=utf-8")
    @Autowired
    lateinit var algorithmResult:AlgorithmresultMapper
    @RequestMapping("/predict")
    fun predict(@RequestBody data:Map<String,String>):String{
        val factory = data["factory"]
        val line = data["line"]
        val device = data["device"]
        val allString = factory+line+device
        val md = MessageDigest.getInstance("MD5")
        md.update(allString.toByteArray())
        val stringHash =(DatatypeConverter.printHexBinary(md.digest())).toLowerCase()
        val allList = algorithmResult.selectAll()
        var result:Algorithmresult? = null
        for( i in allList){
            if(i.hash==stringHash){
                result=i
                break
            }
        }
        println("\n-----------预测模块接收参数------------")
        println("/${factory}/${line}/${device}")
        println("-----------预测模块接收参数------------")
        if(result==null){
//            发出http请求
            val client = OkHttpClient()
            val jsonObject = JSONObject()
            jsonObject.put("factory",factory)
            jsonObject.put("line",line)
            jsonObject.put("device",device)
            val requestBody = com.squareup.okhttp.RequestBody.create(jsonType,jsonObject.toJSONString())
            val algorithmURL = System.getenv("ALGORITHM_URL")
            val apiURL = "${algorithmURL}/algorithm/predict"
            val request = Request.Builder().url(apiURL).addHeader("Content-Type","application/json;charset=utf-8").post(requestBody).build()
            client.newCall(request).execute()
            return ""
        }
        else{
            val sharedRoot = System.getenv("SHARED_ROOT")
            val realFileName = "${result.result}.json"
            val file = File(sharedRoot,realFileName)
            val contents = file.readText()
            println(contents)
            return contents
        }

    }
}
