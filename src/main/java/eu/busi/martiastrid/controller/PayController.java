package eu.busi.martiastrid.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import eu.busi.martiastrid.controller.util.URLUtils;
import eu.busi.martiastrid.dataAccess.dao.OrderDao;
import eu.busi.martiastrid.exception.PizzaException;
import eu.busi.martiastrid.model.Order;
import eu.busi.martiastrid.model.PromoInfo;
import eu.busi.martiastrid.service.OrderService;
import eu.busi.martiastrid.service.PaypalService;
import eu.busi.martiastrid.service.PromoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Objects;

import static eu.busi.martiastrid.constants.Constants.*;

@Controller
@RequestMapping("/checkout")
@SessionAttributes({SUM})
public class PayController {

    @Autowired
    OrderDao orderDao;

    @Autowired
    OrderService orderService;

    @Autowired
    PromoService promoService;

    @Autowired
    private PaypalService paypalService;

    public static final String PAYPAL_SUCCESS_URL = "checkout/pay/success";
    public static final String PAYPAL_CANCEL_URL = "checkout/pay/cancel";

    @GetMapping
    public String showPayPage(Model model,
                              Principal principal,
                              HttpServletRequest httpServletRequest){
        Order order = orderService.getOrderForConnectedUserOrCreateIfNonExistent();
        Integer sum = orderService.calculateTotalAmount(order);
        model.addAttribute(SUM, sum);
        int reduction = 0;
        String promoName = null;
        try {
            PromoInfo promoInfo = promoService.getPromoResult(sum, principal.getName());
            promoName = promoInfo.name;
            reduction = promoInfo.reduction;
        } catch (PizzaException e) {
            System.out.println("promo exception");
        }

        if (!Objects.isNull(promoName)) {
            model.addAttribute(OLD_SUM, sum);
            model.addAttribute(PROMO_NAME, promoName);

            order.setReduction(reduction);
            order.setPromoName(promoName);
            orderDao.save(order);
        }

        model.addAttribute(SUM, sum - reduction);


        return "loginTemplate:pay";
    }

    @PostMapping("/pay")
    public String pay(HttpServletRequest request,
                      @SessionAttribute(SUM) Integer sumInteger){

        Double sum = Double.valueOf(sumInteger);

        String cancelUrl = URLUtils.getBaseURl(request) + "/" + PAYPAL_CANCEL_URL;
        String successUrl = URLUtils.getBaseURl(request) + "/" + PAYPAL_SUCCESS_URL;
        try {
            Payment payment = paypalService.createPayment(
                    sum,
                    "EUR",
                    "payment description",
                    cancelUrl,
                    successUrl);

            for(Links links : payment.getLinks()){
                if(links.getRel().equals("approval_url")){
                    return "redirect:" + links.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            return "redirect:/checkout/pay/cancel";
        }
        return "redirect:/";
    }

    @GetMapping("/pay/cancel")
    public String cancelPay(){
        return "loginTemplate:cancelPayment";
    }

    @GetMapping("/pay/success")
    public String successPay(@RequestParam("paymentId") String paymentId,
                             @RequestParam("PayerID") String payerId,
                             @SessionAttribute(SUM) Integer sumInteger){
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if(payment.getState().equals("approved")){
                orderService.closePayedOrder(paymentId, sumInteger);
                return "loginTemplate:successPayment";
            }
        } catch (PayPalRESTException e) {
            return "redirect:/checkout/pay/cancel";
        } catch (PizzaException e) {
            //todo add message that payment ok but error saving order
            return "loginTemplate:successPayment";
        }
        return "redirect:/";
    }
}
