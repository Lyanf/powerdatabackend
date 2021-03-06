package dclab.powerdatabackend.dao;

import dclab.powerdatabackend.model.File;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FileMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sjtudb.file
     *
     * @mbg.generated
     */
    @Insert({
        "insert into sjtudb.file (filename, locations, ",
        "types, creator, ",
        "createtime, size)",
        "values (#{filename,jdbcType=VARCHAR}, #{locations,jdbcType=VARCHAR}, ",
        "#{types,jdbcType=VARCHAR}, #{creator,jdbcType=VARCHAR}, ",
        "#{createtime,jdbcType=VARCHAR}, #{size,jdbcType=VARCHAR})"
    })
    int insert(File record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sjtudb.file
     *
     * @mbg.generated
     */
    @Select({
        "select",
        "filename, locations, types, creator, createtime, size",
        "from sjtudb.file"
    })
    @Results({
        @Result(column="filename", property="filename", jdbcType=JdbcType.VARCHAR),
        @Result(column="locations", property="locations", jdbcType=JdbcType.VARCHAR),
        @Result(column="types", property="types", jdbcType=JdbcType.VARCHAR),
        @Result(column="creator", property="creator", jdbcType=JdbcType.VARCHAR),
        @Result(column="createtime", property="createtime", jdbcType=JdbcType.VARCHAR),
        @Result(column="size", property="size", jdbcType=JdbcType.VARCHAR)
    })
    List<File> selectAll();
}