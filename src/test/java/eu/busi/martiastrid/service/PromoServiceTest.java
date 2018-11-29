package eu.busi.martiastrid.service;

import eu.busi.martiastrid.dataAccess.dao.UserDao;
import eu.busi.martiastrid.exception.PizzaException;
import eu.busi.martiastrid.model.PromoInfo;
import eu.busi.martiastrid.model.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PromoServiceTest {
    @Mock
    CartService cartService;

    @Mock
    UserDao userDao;

    @InjectMocks
    PromoService promoService;

    @Test
    public void getPromoResult_whenBirthday() throws PizzaException {
        LocalDate today = LocalDate.now();
        int year = 2015;
        int month = today.getMonthValue() - 1;
        int date = today.getDayOfMonth();
        User roger = new User();
        roger.setBirthDate(new Date(year, month, date));
        when(userDao.getByUsername("roger")).thenReturn(roger);
        PromoInfo expected = new PromoInfo("birthdayDudePromo", 5);
        PromoInfo actual = promoService.getPromoResult(100, "roger");
        assertEquals(expected, actual);
    }

    @Test
    public void getPromoResult_whenVegan() throws PizzaException {
        User roger = new User();
        when(userDao.getByUsername("roger")).thenReturn(roger);
        // warning, si on lance ce test en décembre, on aura la december promo à la place
        PromoInfo expected = new PromoInfo("veganReward", 15);
        PromoInfo actual = promoService.getPromoResult(100, "roger");
        assertEquals(expected, actual);
    }
}