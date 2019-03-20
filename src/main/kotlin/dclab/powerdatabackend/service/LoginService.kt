package dclab.powerdatabackend.service

import dclab.powerdatabackend.domain.User
import dclab.powerdatabackend.mapper.UserMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.HashMap



@Service
class LoginService {
    @Autowired
    private var userMapper: UserMapper? = null

    fun setUserMapper(userMapper: UserMapper) {
        this.userMapper = userMapper
    }

    fun login(username: String?, password: String?): Any {
        val user = userMapper?.getUserByUsername(username!!)
        if (user != null) {
            if (user.password.equals(password)) {
                val result = HashMap<String, Any>()
                result["user"] = user
                result["status"] = "SUCCESS"
                return result
            } else {
                val result = HashMap<String, Any>()
                result["message"] = "密码或用户名错误"
                result["status"] = "FAILED"
                return result
            }

        } else {
            val result = HashMap<String, Any>()
            result["message"] = "密码或用户名错误"
            result["status"] = "FAILED"
            return result
        }
    }

    fun register(user: User): Any? {
        return if (userMapper!!.insertUser(user) == 1)
            user
        else
            null
    }
}