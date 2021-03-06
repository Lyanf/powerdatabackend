package dclab.powerdatabackend.dao;

import dclab.powerdatabackend.model.Datas;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface DatasMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sjtudb.datas
     *
     * @mbg.generated
     */
    @Insert({
        "insert into sjtudb.datas (factory, line, ",
        "device, timestamps, ",
        "aphaseelectrictension, bphaseelectrictension, ",
        "cphaseelectrictension, ablineelectrictension, ",
        "bclineelectrictension, calineelectrictension, ",
        "aphaseelectriccurrent, bphaseelectriccurrent, ",
        "cphaseelectriccurrent, zerosequencecurrent, ",
        "aphaseactivepower, bphaseactivepower, ",
        "cphaseactivepower, threephasetotalactivepower, ",
        "aphasereactivepower, bphasereactivepower, ",
        "cphasereactivepower, threephasetotalreactivepower, ",
        "aphaseatpower, bphaseatpower, ",
        "cphaseatpower, threephasetotalatpower, ",
        "aphasepowerfactor, bphasepowerfactor, ",
        "cphasepowerfactor, averagepowerfactor, ",
        "frequency, forwardactive, ",
        "reverseactive, forwardreactivewattage, ",
        "reversereactivewattage, voltageunbalance, ",
        "electriccurrentunbalance, aphasevoltageharmonictotaldistortion, ",
        "bphasevoltageharmonictotaldistortion, ",
        "cphasevoltageharmonictotaldistortion, ",
        "totalharmonicdistortionofaphasecurrent, ",
        "totalharmonicdistortionofbphasecurrent, ",
        "totalharmonicdistortionofcphasecurrent, ",
        "maximumpositiveactivedemand, maximumreverseactivedemand, ",
        "maximumforwardreactivepowerdemand, maximumreversereactivepowerdemand)",
        "values (#{factory,jdbcType=VARCHAR}, #{line,jdbcType=VARCHAR}, ",
        "#{device,jdbcType=VARCHAR}, #{timestamps,jdbcType=VARCHAR}, ",
        "#{aphaseelectrictension,jdbcType=DOUBLE}, #{bphaseelectrictension,jdbcType=DOUBLE}, ",
        "#{cphaseelectrictension,jdbcType=DOUBLE}, #{ablineelectrictension,jdbcType=DOUBLE}, ",
        "#{bclineelectrictension,jdbcType=DOUBLE}, #{calineelectrictension,jdbcType=DOUBLE}, ",
        "#{aphaseelectriccurrent,jdbcType=DOUBLE}, #{bphaseelectriccurrent,jdbcType=DOUBLE}, ",
        "#{cphaseelectriccurrent,jdbcType=DOUBLE}, #{zerosequencecurrent,jdbcType=DOUBLE}, ",
        "#{aphaseactivepower,jdbcType=DOUBLE}, #{bphaseactivepower,jdbcType=DOUBLE}, ",
        "#{cphaseactivepower,jdbcType=DOUBLE}, #{threephasetotalactivepower,jdbcType=DOUBLE}, ",
        "#{aphasereactivepower,jdbcType=DOUBLE}, #{bphasereactivepower,jdbcType=DOUBLE}, ",
        "#{cphasereactivepower,jdbcType=DOUBLE}, #{threephasetotalreactivepower,jdbcType=DOUBLE}, ",
        "#{aphaseatpower,jdbcType=DOUBLE}, #{bphaseatpower,jdbcType=DOUBLE}, ",
        "#{cphaseatpower,jdbcType=DOUBLE}, #{threephasetotalatpower,jdbcType=DOUBLE}, ",
        "#{aphasepowerfactor,jdbcType=DOUBLE}, #{bphasepowerfactor,jdbcType=DOUBLE}, ",
        "#{cphasepowerfactor,jdbcType=DOUBLE}, #{averagepowerfactor,jdbcType=DOUBLE}, ",
        "#{frequency,jdbcType=DOUBLE}, #{forwardactive,jdbcType=DOUBLE}, ",
        "#{reverseactive,jdbcType=DOUBLE}, #{forwardreactivewattage,jdbcType=DOUBLE}, ",
        "#{reversereactivewattage,jdbcType=DOUBLE}, #{voltageunbalance,jdbcType=DOUBLE}, ",
        "#{electriccurrentunbalance,jdbcType=DOUBLE}, #{aphasevoltageharmonictotaldistortion,jdbcType=DOUBLE}, ",
        "#{bphasevoltageharmonictotaldistortion,jdbcType=DOUBLE}, ",
        "#{cphasevoltageharmonictotaldistortion,jdbcType=DOUBLE}, ",
        "#{totalharmonicdistortionofaphasecurrent,jdbcType=DOUBLE}, ",
        "#{totalharmonicdistortionofbphasecurrent,jdbcType=DOUBLE}, ",
        "#{totalharmonicdistortionofcphasecurrent,jdbcType=DOUBLE}, ",
        "#{maximumpositiveactivedemand,jdbcType=DOUBLE}, #{maximumreverseactivedemand,jdbcType=DOUBLE}, ",
        "#{maximumforwardreactivepowerdemand,jdbcType=DOUBLE}, #{maximumreversereactivepowerdemand,jdbcType=DOUBLE})"
    })
    int insert(Datas record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sjtudb.datas
     *
     * @mbg.generated
     */
    @Select({
        "select",
        "factory, line, device, timestamps, aphaseelectrictension, bphaseelectrictension, ",
        "cphaseelectrictension, ablineelectrictension, bclineelectrictension, calineelectrictension, ",
        "aphaseelectriccurrent, bphaseelectriccurrent, cphaseelectriccurrent, zerosequencecurrent, ",
        "aphaseactivepower, bphaseactivepower, cphaseactivepower, threephasetotalactivepower, ",
        "aphasereactivepower, bphasereactivepower, cphasereactivepower, threephasetotalreactivepower, ",
        "aphaseatpower, bphaseatpower, cphaseatpower, threephasetotalatpower, aphasepowerfactor, ",
        "bphasepowerfactor, cphasepowerfactor, averagepowerfactor, frequency, forwardactive, ",
        "reverseactive, forwardreactivewattage, reversereactivewattage, voltageunbalance, ",
        "electriccurrentunbalance, aphasevoltageharmonictotaldistortion, bphasevoltageharmonictotaldistortion, ",
        "cphasevoltageharmonictotaldistortion, totalharmonicdistortionofaphasecurrent, ",
        "totalharmonicdistortionofbphasecurrent, totalharmonicdistortionofcphasecurrent, ",
        "maximumpositiveactivedemand, maximumreverseactivedemand, maximumforwardreactivepowerdemand, ",
        "maximumreversereactivepowerdemand",
        "from sjtudb.datas"
    })
    @Results({
        @Result(column="factory", property="factory", jdbcType=JdbcType.VARCHAR),
        @Result(column="line", property="line", jdbcType=JdbcType.VARCHAR),
        @Result(column="device", property="device", jdbcType=JdbcType.VARCHAR),
        @Result(column="timestamps", property="timestamps", jdbcType=JdbcType.VARCHAR),
        @Result(column="aphaseelectrictension", property="aphaseelectrictension", jdbcType=JdbcType.DOUBLE),
        @Result(column="bphaseelectrictension", property="bphaseelectrictension", jdbcType=JdbcType.DOUBLE),
        @Result(column="cphaseelectrictension", property="cphaseelectrictension", jdbcType=JdbcType.DOUBLE),
        @Result(column="ablineelectrictension", property="ablineelectrictension", jdbcType=JdbcType.DOUBLE),
        @Result(column="bclineelectrictension", property="bclineelectrictension", jdbcType=JdbcType.DOUBLE),
        @Result(column="calineelectrictension", property="calineelectrictension", jdbcType=JdbcType.DOUBLE),
        @Result(column="aphaseelectriccurrent", property="aphaseelectriccurrent", jdbcType=JdbcType.DOUBLE),
        @Result(column="bphaseelectriccurrent", property="bphaseelectriccurrent", jdbcType=JdbcType.DOUBLE),
        @Result(column="cphaseelectriccurrent", property="cphaseelectriccurrent", jdbcType=JdbcType.DOUBLE),
        @Result(column="zerosequencecurrent", property="zerosequencecurrent", jdbcType=JdbcType.DOUBLE),
        @Result(column="aphaseactivepower", property="aphaseactivepower", jdbcType=JdbcType.DOUBLE),
        @Result(column="bphaseactivepower", property="bphaseactivepower", jdbcType=JdbcType.DOUBLE),
        @Result(column="cphaseactivepower", property="cphaseactivepower", jdbcType=JdbcType.DOUBLE),
        @Result(column="threephasetotalactivepower", property="threephasetotalactivepower", jdbcType=JdbcType.DOUBLE),
        @Result(column="aphasereactivepower", property="aphasereactivepower", jdbcType=JdbcType.DOUBLE),
        @Result(column="bphasereactivepower", property="bphasereactivepower", jdbcType=JdbcType.DOUBLE),
        @Result(column="cphasereactivepower", property="cphasereactivepower", jdbcType=JdbcType.DOUBLE),
        @Result(column="threephasetotalreactivepower", property="threephasetotalreactivepower", jdbcType=JdbcType.DOUBLE),
        @Result(column="aphaseatpower", property="aphaseatpower", jdbcType=JdbcType.DOUBLE),
        @Result(column="bphaseatpower", property="bphaseatpower", jdbcType=JdbcType.DOUBLE),
        @Result(column="cphaseatpower", property="cphaseatpower", jdbcType=JdbcType.DOUBLE),
        @Result(column="threephasetotalatpower", property="threephasetotalatpower", jdbcType=JdbcType.DOUBLE),
        @Result(column="aphasepowerfactor", property="aphasepowerfactor", jdbcType=JdbcType.DOUBLE),
        @Result(column="bphasepowerfactor", property="bphasepowerfactor", jdbcType=JdbcType.DOUBLE),
        @Result(column="cphasepowerfactor", property="cphasepowerfactor", jdbcType=JdbcType.DOUBLE),
        @Result(column="averagepowerfactor", property="averagepowerfactor", jdbcType=JdbcType.DOUBLE),
        @Result(column="frequency", property="frequency", jdbcType=JdbcType.DOUBLE),
        @Result(column="forwardactive", property="forwardactive", jdbcType=JdbcType.DOUBLE),
        @Result(column="reverseactive", property="reverseactive", jdbcType=JdbcType.DOUBLE),
        @Result(column="forwardreactivewattage", property="forwardreactivewattage", jdbcType=JdbcType.DOUBLE),
        @Result(column="reversereactivewattage", property="reversereactivewattage", jdbcType=JdbcType.DOUBLE),
        @Result(column="voltageunbalance", property="voltageunbalance", jdbcType=JdbcType.DOUBLE),
        @Result(column="electriccurrentunbalance", property="electriccurrentunbalance", jdbcType=JdbcType.DOUBLE),
        @Result(column="aphasevoltageharmonictotaldistortion", property="aphasevoltageharmonictotaldistortion", jdbcType=JdbcType.DOUBLE),
        @Result(column="bphasevoltageharmonictotaldistortion", property="bphasevoltageharmonictotaldistortion", jdbcType=JdbcType.DOUBLE),
        @Result(column="cphasevoltageharmonictotaldistortion", property="cphasevoltageharmonictotaldistortion", jdbcType=JdbcType.DOUBLE),
        @Result(column="totalharmonicdistortionofaphasecurrent", property="totalharmonicdistortionofaphasecurrent", jdbcType=JdbcType.DOUBLE),
        @Result(column="totalharmonicdistortionofbphasecurrent", property="totalharmonicdistortionofbphasecurrent", jdbcType=JdbcType.DOUBLE),
        @Result(column="totalharmonicdistortionofcphasecurrent", property="totalharmonicdistortionofcphasecurrent", jdbcType=JdbcType.DOUBLE),
        @Result(column="maximumpositiveactivedemand", property="maximumpositiveactivedemand", jdbcType=JdbcType.DOUBLE),
        @Result(column="maximumreverseactivedemand", property="maximumreverseactivedemand", jdbcType=JdbcType.DOUBLE),
        @Result(column="maximumforwardreactivepowerdemand", property="maximumforwardreactivepowerdemand", jdbcType=JdbcType.DOUBLE),
        @Result(column="maximumreversereactivepowerdemand", property="maximumreversereactivepowerdemand", jdbcType=JdbcType.DOUBLE)
    })
    List<Datas> selectAll();
}