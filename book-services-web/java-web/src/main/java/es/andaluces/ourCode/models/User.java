package es.andaluces.ourCode.models;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class User {

    private String info = "";

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {return info;}
}