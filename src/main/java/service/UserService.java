package service;

import org.springframework.stereotype.Service;

import bean.User;

@Service
public class UserService {
    
    public User getUser(int id) {
        User user = new User();
        user.setId(id);
        
        return user;
    }
}
