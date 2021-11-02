package com.barlipdev.dwyf.scheduler;

import com.barlipdev.dwyf.model.user.User;
import com.barlipdev.dwyf.model.product.UsefulnessState;
import com.barlipdev.dwyf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;


@Component
public class ScheduledTasks {

    @Autowired
    UserService userService;

    //to do checking user products state (its safe to eat)
    @Scheduled(fixedRate = 50000)
    public void checkProductUsefulness() {
        List<User> users = userService.getUsers();
        LocalDate today = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (users.size() > 0){
            users.forEach(user -> {
                if (user.getProductList().size() > 0){
                    user.getProductList().forEach(product -> {
                        int daysToExpired = Period.between(today,product.getExpirationDate()).getDays();
                        if ( daysToExpired > 5){
                            product.setUsefulnessState(UsefulnessState.GOOD);
                        }else if (daysToExpired > 0){
                            product.setUsefulnessState(UsefulnessState.CLOSEEXPIRYDATE);
                        }else {
                            product.setUsefulnessState(UsefulnessState.EXPIRED);
                        }
                    });
                }
                userService.update(user);
            });
        }


    }
}
