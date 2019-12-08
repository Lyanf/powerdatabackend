package dclab.powerdatabackend.mapper

import dclab.powerdatabackend.domain.DataInfo
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select
import org.springframework.stereotype.Repository

@Mapper
@Repository
interface OldDataMapper {
    @Select("select * from data where " +
            "measurePoint like #{measurePoint}  and " +
            "locations like #{location}  and " +
            "#{startTime} <= dates and " +
            "#{endTime} >= dates")
    fun getSearchArray(measurePoint: String,
                       location: String,
                       startTime: String,
                       endTime: String): Array<DataInfo>

}