package dclab.powerdatabackend.controller

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import dclab.powerdatabackend.dao.*
import dclab.powerdatabackend.model.*
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*
fun getNowStr():String {
    val now = LocalDateTime.now()
    val timeStr = ("${now.year}/${now.monthValue}/${now.dayOfMonth} ${now.hour}:${now.minute}")
    return timeStr

}

@CrossOrigin
@RestController
@RequestMapping("/api")
class NewMetaDataController {
    @Autowired
    lateinit var conceptMapper: ConceptMapper

    @Autowired
    lateinit var logicMapper: LogicMapper

    @Autowired
    lateinit var physicsMapper: PhysicsMapper

    @Autowired
    lateinit var concepttypeMapper: ConcepttypeMapper

    @Autowired
    lateinit var logictypeMapper: LogictypeMapper

    @Autowired
    lateinit var physicstypeMapper: PhysicstypeMapper

    @Autowired
    lateinit var dataMapper: DataMapper

    //概念层
    @RequestMapping("/getConcept")
    fun getConcept(): List<Concept> {
        return conceptMapper.selectAll()
    }

    @PostMapping("/addConcept")
    fun addConcept(@RequestBody data: String) {
        print(data)
        val concept = JSON.parseObject(data, Concept::class.javaObjectType)
        concept.createtime = getNowStr()
        conceptMapper.insert(concept)
    }

    @PostMapping("/changeConcept")
    fun changeConcept(@RequestBody data: String) {
        val concept = JSON.parseObject(data, Concept::class.javaObjectType)
        concept.createtime = getNowStr()
        conceptMapper.updateByPrimaryKey(concept)
    }

    @RequestMapping("/deleteConcept")
    fun deleteConcept(@RequestBody data: String) {
        val concept = JSON.parseObject(data, Concept::class.javaObjectType)
        conceptMapper.deleteByPrimaryKey(concept.id)
    }

    @RequestMapping("/getConceptType")
    fun getConcepttype(): List<Concepttype> {
        return concepttypeMapper.selectAll()
    }

    //逻辑层
    @RequestMapping("/getLogic")
    fun getLogic(): List<Logic> {
        return logicMapper.selectAll()
    }

    @RequestMapping("/getLogicType")
    fun getLogicType(): List<Logictype> {
        return logictypeMapper.selectAll()
    }

    //
    @PostMapping("/addLogic")
    fun addLogic(@RequestBody data: String) {
        val logicObj: Logic = JSON.parseObject(data, Logic::class.javaObjectType)
        logicObj.createtime = getNowStr()
        logicMapper.insert(logicObj)

    }

    @PostMapping("/changeLogic")
    fun changeLogic(@RequestBody data: String) {
        val logicObj: Logic = JSON.parseObject(data, Logic::class.javaObjectType)
        logicObj.createtime = getNowStr()
        logicMapper.updateByPrimaryKey(logicObj)
    }

    @RequestMapping("/deleteLogic")
    fun deleteLogic(@RequestBody data: String) {
        val logicObj = JSON.parseObject(data, Logic::class.javaObjectType)
        logicMapper.deleteByPrimaryKey(logicObj.id)
    }

    //物理层
    @RequestMapping(path = ["/getMetaData", "/getPhysics"])
    fun getPhysics(): List<Physics> {
        return physicsMapper.selectAll()
    }

    @RequestMapping("/getPhysicsType")
    fun getPhysicsType(): List<Physicstype> {
        return physicstypeMapper.selectAll()
    }

    //
    @PostMapping("/addPhysics")
    fun addPhysics(@RequestBody data: String) {
        val physics: Physics = JSON.parseObject(data, Physics::class.javaObjectType)
        physics.createtime = getNowStr()
        physicsMapper.insert(physics)

    }

    @PostMapping("/changePhysics")
    fun changePhysics(@RequestBody data: String) {
        val physicsObj: Physics = JSON.parseObject(data, Physics::class.javaObjectType)
        physicsObj.createtime = getNowStr()
        physicsMapper.updateByPrimaryKey(physicsObj)
    }

    @RequestMapping("/deletePhysics")
    fun deletePhysics(@RequestBody data: String) {
        val physicsObj = JSON.parseObject(data, Physics::class.javaObjectType)
        physicsMapper.deleteByPrimaryKey(physicsObj.id)
    }

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
    @RequestMapping("/kafka")
    fun startKafka():String
    {
        val properties = Properties()
        properties.put("bootstrap.servers", "192.168.1.194:9092")
        properties.put("group.id", "group-1")
        properties.put("enable.auto.commit", "true")
        properties.put("auto.commit.interval.ms", "1000")
        properties.put("auto.offset.reset", "earliest")
        properties.put("session.timeout.ms", "30000")
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")

        val kafkaConsumer = KafkaConsumer<Int,String>(properties)
        kafkaConsumer.subscribe(Arrays.asList("my-test"))
        while (true) {
            val records = kafkaConsumer.poll(1)
            for (record in records) {
                val jsonObject = JSONObject.parseObject(record.value())
                val cedian:String = jsonObject.getString("cedian")
                val value:Double = jsonObject.getDouble("value")
                val date:String = jsonObject.getString("date")
                val location:String = jsonObject.getString("location")
                val tempData = Data()
                tempData.date = date
                tempData.location = location
                tempData.value = value.toFloat()
                tempData.measurepoint = cedian
                print(tempData)
                dataMapper.insert(tempData)
            }
        }

    }

}