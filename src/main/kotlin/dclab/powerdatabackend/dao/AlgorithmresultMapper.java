package dclab.powerdatabackend.dao;

import dclab.powerdatabackend.model.Algorithmresult;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;
@Mapper
@Repository
public interface AlgorithmresultMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table algorithmresult
     *
     * @mbg.generated
     */
    @Insert({
        "insert into algorithmresult (hash, result)",
        "values (#{hash,jdbcType=VARCHAR}, #{result,jdbcType=VARCHAR})"
    })
    int insert(Algorithmresult record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table algorithmresult
     *
     * @mbg.generated
     */
    @Select({
        "select",
        "hash, result",
        "from algorithmresult"
    })
    @Results({
        @Result(column="hash", property="hash", jdbcType=JdbcType.VARCHAR),
        @Result(column="result", property="result", jdbcType=JdbcType.VARCHAR)
    })
    List<Algorithmresult> selectAll();
}