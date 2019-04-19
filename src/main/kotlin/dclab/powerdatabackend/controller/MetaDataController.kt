//package dclab.powerdatabackend.controller
//
//import com.alibaba.fastjson.JSON
//import com.alibaba.fastjson.JSONObject
//import dclab.powerdatabackend.domain.ConceptData
//import dclab.powerdatabackend.domain.LogicData
//import dclab.powerdatabackend.domain.PhysicsData
//import dclab.powerdatabackend.mapper.OldDataMapper
//import dclab.powerdatabackend.mapper.MataDataMapper
//import org.springframework.beans.factory.annotation.Autowired
//import org.apache.kafka.clients.consumer.KafkaConsumer
//import org.apache.tomcat.util.json.JSONParserConstants
//import org.springframework.web.bind.annotation.*
//import java.util.*
//
//@CrossOrigin
//@RestController
//@RequestMapping("/api")
//class MetaDataController {
//    @Autowired
//    lateinit var metaDataMapper: MataDataMapper
//
//    @Autowired
//    lateinit var oldDataMapper: OldDataMapper
////概念层
//    @RequestMapping("/getConcept")
//    fun getConcept(): Array<ConceptData> {
//        return metaDataMapper.getConcept()
//    }
//
//    @PostMapping("/addConcept")
//    fun addConcept(@RequestBody data:String){
//        print(data)
//        val conceptName = JSON.parseObject(data).get("conceptName").toString()
//        metaDataMapper.insertConcept(conceptName)
//
//    }
//
//    @PostMapping("/changeConcept")
//    fun changeConcept(@RequestBody data:String){
//        val originName = JSON.parseObject(data).get("originName").toString()
//        val changeName = JSON.parseObject(data).get("changeName").toString()
//
//        metaDataMapper.updateConcept(originName,changeName)
//    }
//
//    @RequestMapping("/deleteConcept")
//    fun deleteConcept(@RequestBody data:String){
//        val originName = JSON.parseObject(data).get("originName").toString()
//        print(originName)
//        return metaDataMapper.deleteConcept(originName)
//    }
////逻辑层
//    @RequestMapping("/getLogic")
//    fun getLogic(): Array<LogicData> {
//        return metaDataMapper.getLogic()
//    }
//    @PostMapping("/addLogic")
//    fun addLogic(@RequestBody data:String){
//        print(data)
//        val conceptName = JSON.parseObject(data).get("conceptName").toString()
//        val logicName = JSON.parseObject(data).get("logicName").toString()
//        metaDataMapper.insertLogic(logicName,conceptName)
//
//    }
//
//    @PostMapping("/changeLogic")
//    fun changeLogic(@RequestBody data:String){
//        val originName = JSON.parseObject(data).get("originName").toString()
//        val changeName = JSON.parseObject(data).get("changeName").toString()
//        val changeConcept:String = JSON.parseObject(data).get("conceptName").toString()
//
//        metaDataMapper.updateLogic(originName,changeName,changeConcept)
//    }
//
//    @RequestMapping("/deleteLogic")
//    fun deleteLogic(@RequestBody data:String){
//        val originName = JSON.parseObject(data).get("originName").toString()
//        print(originName)
//        return metaDataMapper.deleteLogic(originName)
//    }
////物理层
//    @RequestMapping(path=["/getMetaData","/getPhysics"])
//    fun getPhysics(): Array<PhysicsData> {
//        print(123)
//        return metaDataMapper.getPhysics()
//    }
//
//    @PostMapping("/addPhysics")
//    fun addPhysics(@RequestBody data:String){
//        print(data)
//        val conceptName = JSON.parseObject(data).get("conceptName").toString()
//        val logictName = JSON.parseObject(data).get("logicName").toString()
//        val physicsName = JSON.parseObject(data).get("physicsName").toString()
//        metaDataMapper.insertPhysics(logictName,conceptName,physicsName)
//
//    }
//
//    @PostMapping("/changePhysics")
//    fun changePhysics(@RequestBody data:String){
//        val originName = JSON.parseObject(data).get("originName").toString()
//        val changeName = JSON.parseObject(data).get("changeName").toString()
//        val changeLogic:String = JSON.parseObject(data).get("logicName").toString()
//        val changeConcept:String = JSON.parseObject(data).get("conceptName").toString()
//
//        metaDataMapper.updatePhysics(originName,changeName,changeLogic,changeConcept)
//    }
//
//    @RequestMapping("/deletePhysics")
//    fun deletePhysics(@RequestBody data:String){
//        val originName = JSON.parseObject(data).get("originName").toString()
//        print(originName)
//        return metaDataMapper.deletePhysics(originName)
//    }
//
//
//
//    @RequestMapping("/kafka")
//    fun startKafka():String
//    {
//        val properties = Properties()
//        properties.put("bootstrap.servers", "192.168.1.194:9092")
//        properties.put("group.id", "group-1")
//        properties.put("enable.auto.commit", "true")
//        properties.put("auto.commit.interval.ms", "1000")
//        properties.put("auto.offset.reset", "earliest")
//        properties.put("session.timeout.ms", "30000")
//        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
//        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
//
//        val kafkaConsumer = KafkaConsumer<Int,String>(properties)
//        kafkaConsumer.subscribe(Arrays.asList("my-test"))
//        while (true) {
//            val records = kafkaConsumer.poll(1)
//            for (record in records) {
//                val jsonObject = JSONObject.parseObject(record.value())
//                val cedian:String = jsonObject.getString("cedian")
//                val value:Double = jsonObject.getDouble("value")
//                val date:String = jsonObject.getString("date")
//                val location:String = jsonObject.getString("location")
//                println(cedian)
//                println(value)
//                println(date)
//                println(location)
//            }
//        }
//
//    }
//
//}