package dclab.powerdatabackend.service

import dclab.powerdatabackend.mapper.UserMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service

@Service
class UserService {
    @Autowired
    private val userMapper: UserMapper? = null
}
