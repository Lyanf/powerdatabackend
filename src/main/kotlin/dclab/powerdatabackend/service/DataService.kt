package dclab.powerdatabackend.service

import dclab.powerdatabackend.domain.DataInfo
import dclab.powerdatabackend.mapper.DataMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service

@Service
class DataService{
    @Autowired
    val dataMapper:DataMapper? = null

    fun search(measurePoint:String,location:String,startTime:String?,endTime:String?):Array<DataInfo>{
        return dataMapper!!.getSearchArray("%$measurePoint%","%$location%",if(startTime==null)"0" else startTime.replace("-","")
                ,if(endTime==null)"999999" else endTime.replace("-",""))
    }
}