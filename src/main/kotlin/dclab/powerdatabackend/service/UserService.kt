package dclab.powerdatabackend.service

import dclab.powerdatabackend.mapper.OldUserMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {
    @Autowired
    private val oldUserMapper: OldUserMapper? = null
}
