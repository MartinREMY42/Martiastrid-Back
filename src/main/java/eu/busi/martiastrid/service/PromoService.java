package eu.busi.martiastrid.service;

import eu.busi.martiastrid.dataAccess.dao.UserDao;
import eu.busi.martiastrid.exception.PizzaException;
import eu.busi.martiastrid.model.Pizza;
import eu.busi.martiastrid.model.PromoInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class PromoService {

    @Autowired
    CartService cartService;

    @Autowired
    UserDao userDao;

    public PromoInfo getPromoResult(int currentSum, String userName) throws PizzaException {
        if (isDecember()){
            // En décembre, tout le monde paye 12% en moins !
            int reduction = (int) (currentSum*0.12);
            return new PromoInfo("decembersPromo", reduction);
        }
        if (isUserBirthday(userName)) {
            // 5% remise à ton annif cousin
            int reduction = (int) (currentSum*0.05);
            return new PromoInfo("birthdayDudePromo", reduction);
        }
        if (veganReward()){
            // 15% de remises sur les commandes 100% véganes
            int reduction = (int) (currentSum*0.15);
            return new PromoInfo("veganReward", reduction);
        }
        return new PromoInfo(null, 0);
    }

    private boolean isDecember(){
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int month = calendar.get(Calendar.MONTH) + 1;
        return (month==12);
    }

    private boolean isUserBirthday(String userName) {
        Date birthday = userDao.getByUsername(userName).getBirthDate();
        if (Objects.isNull(birthday)) {
            return false;
        }
        int month = birthday.getMonth() + 1;
        int day = birthday.getDate();

        LocalDate today = LocalDate.now();
        int todayDay = today.getDayOfMonth();
        int todayMonth = today.getMonthValue();
        return (todayDay == day) && (todayMonth == month);
    }

    private boolean veganReward() throws PizzaException {
        Set<Pizza> orderedPizzas = cartService.loadCartFromDatabase().keySet();
        return orderedPizzas
                .stream()
                .allMatch(pizza ->
                        pizza.getCategory().contains("vegan"));
    }

}
