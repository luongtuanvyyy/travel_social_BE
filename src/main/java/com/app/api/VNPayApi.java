package com.app.api;

import com.app.modal.vnpayModal;
import com.app.payload.request.AccountQueryParam;
import com.app.payload.response.APIResponse;
import com.app.security.CurrentUser;
import com.app.security.UserPrincipal;
import com.app.service.AccountService;
import com.app.service.VNPayService;
import io.swagger.models.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class VNPayApi {
    @Autowired
    AccountService accountService;

    @Autowired
    VNPayService vnPayService;

    @PostMapping("/user/submitOrder")
    public ResponseEntity<?> submidOrder(@RequestParam("amount") int orderTotal,
                              @RequestParam("orderInfo") String orderInfo,
                              HttpServletRequest request){
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        APIResponse response  = vnPayService.createOder(orderTotal, orderInfo, baseUrl);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/vnpay-payment")
    public ResponseEntity<?>  GetMapping(HttpServletRequest request, vnpayModal vnpayModal){

        int paymentStatus =vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        vnpayModal.setPaymentTime(paymentTime);
        vnpayModal.setOrderInfo(orderInfo);
        vnpayModal.setTransactionId(transactionId);
        vnpayModal.setTotalPrice(totalPrice);
        return ResponseEntity.ok(vnpayModal);
    }

}
