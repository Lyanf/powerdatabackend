package dclab.powerdatabackend.mapper

import dclab.powerdatabackend.domain.User
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select
import org.springframework.stereotype.Repository

@Mapper
@Repository
interface OldUserMapper {
    @Select("SELECT * FROM `user` WHERE username=#{username}")
    fun getUserByUsername(username: String): User

    @Insert("INSERT INTO `user` VALUES (#{username},#{password})")
    fun insertUser(user: User): Int
}
