package com.klezovich.camelcoaching.route.component.activemq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserBeanProcessor {

    public void process(UserBean userBean) throws Exception {
        log.info("User is " + userBean.getName());
        var id = userBean.getId();
        id++;

        userBean.setId(id);
    }
}
