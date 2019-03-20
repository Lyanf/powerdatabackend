package dclab.powerdatabackend.controller

import dclab.powerdatabackend.domain.User
import dclab.powerdatabackend.service.LoginService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class LoginController {
    @Autowired
    private var loginService: LoginService? = null

    fun setLoginService(loginService: LoginService) {
        this.loginService = loginService
    }

    //    @RequestMapping(value="/login",method=RequestMethod.POST)
    //    public Object login(@RequestParam(value="username")String username,@RequestParam(value="password")String password){
    //        return loginService.login(username, password);
    //    }
    @RequestMapping(value = ["/login"], method = [RequestMethod.POST])
    fun login(@RequestBody user: User): Any {
        return loginService!!.login(user.username,user.password)
    }

    @RequestMapping(value = ["/register"], method = [RequestMethod.POST])
    fun register(user: User): Any? {
        //System.out.println("phone"+user.getPhone());
        return loginService!!.register(user)
    }
}