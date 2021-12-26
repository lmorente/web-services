package es.andaluces.ourCode.service;

import es.andaluces.ourCode.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private User user;

    public void setInfo(String info){
        this.user.setInfo(info);
    }

    public String getInfo(){
        return this.user.getInfo();
    }
}
