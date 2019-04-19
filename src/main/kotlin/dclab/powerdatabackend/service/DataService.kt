package dclab.powerdatabackend.service

import dclab.powerdatabackend.domain.DataInfo
import dclab.powerdatabackend.mapper.OldDataMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DataService{
    @Autowired
    val oldDataMapper:OldDataMapper? = null

    fun search(measurePoint:String,location:String,startTime:String?,endTime:String?):Array<DataInfo>{
        return oldDataMapper!!.getSearchArray("%$measurePoint%","%$location%",if(startTime==null)"0" else startTime.replace("-","")
                ,if(endTime==null)"999999" else endTime.replace("-",""))
    }
}