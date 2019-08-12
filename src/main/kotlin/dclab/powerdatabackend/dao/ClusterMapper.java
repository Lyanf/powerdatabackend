package dclab.powerdatabackend.dao;

import dclab.powerdatabackend.model.Cluster;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ClusterMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cluster
     *
     * @mbg.generated
     */
    @Insert({
        "insert into cluster (hash, json)",
        "values (#{hash,jdbcType=VARCHAR}, #{json,jdbcType=LONGVARCHAR})"
    })
    int insert(Cluster record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table cluster
     *
     * @mbg.generated
     */
    @Select({
        "select",
        "hash, json",
        "from cluster"
    })
    @Results({
        @Result(column="hash", property="hash", jdbcType=JdbcType.VARCHAR),
        @Result(column="json", property="json", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<Cluster> selectAll();
}