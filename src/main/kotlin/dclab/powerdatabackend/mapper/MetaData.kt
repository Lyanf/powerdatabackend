package dclab.powerdatabackend.mapper

import dclab.powerdatabackend.domain.ConceptData
import dclab.powerdatabackend.domain.LogicData
import dclab.powerdatabackend.domain.PhysicsData
import org.apache.ibatis.annotations.*
import org.springframework.stereotype.Repository

@Mapper
@Repository
interface MataDataMapper {
    @Insert("insert into concept values (#{concept})")
    fun insertConcept(concept:String):Int

    @Delete("delete from concept where concept = #{originName}")
    fun deleteConcept(originName:String)


    @Select("select * from concept")
    fun getConcept():Array<ConceptData>

    @Update("update concept set concept = #{changeName} where concept = #{originName}")
    fun updateConcept(originName:String,changeName:String)

//


    @Insert("insert into logic values (#{logic},#{concept})")
    fun insertLogic(logic:String,concept:String):Int

    @Delete("delete from logic where logic = #{originName}")
    fun deleteLogic(originName:String)

    @Select("select * from logic")
    fun getLogic():Array<LogicData>

    @Update("update logic set logic = #{changeName},concept=#{concept} where logic = #{originName}")
    fun updateLogic(originName:String,changeName:String,concept: String)

//

    @Insert("insert into physics values (#{physics},#{logic},#{concept})")
    fun insertPhysics(concept:String,logic:String,physics:String):Int

    @Delete("delete from physics where physics = #{originName}")
    fun deletePhysics(originName:String)


    @Select("select * from physics")
    fun getPhysics():Array<PhysicsData>

    @Update("update physics set physics = #{changeName},logic=#{logic},concept=#{concept} where physics = #{originName}")
    fun updatePhysics(originName:String,changeName:String,logic: String,concept: String)





}