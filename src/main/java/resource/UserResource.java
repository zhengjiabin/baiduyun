package resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import service.UserService;
import bean.User;

import com.sun.jersey.spi.resource.Singleton;

@Controller
@Singleton
//Spring会针对每一个request请求都生成新的Jersey服务类实例，此方法不需要配置Spring RequsetContextListener
@Scope("prototype")
@Path(value = "/user")
public class UserResource {
    @Autowired
    private UserService userService;
    
    @GET
    @Path(value = "/getUser")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public User getUser(@QueryParam(value = "id")
    int id) {
        User user = userService.getUser(id);
        return user;
    }
}
