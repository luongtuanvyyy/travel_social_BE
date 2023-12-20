package com.app.modal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class vnpayModal {

    String orderInfo;
    String paymentTime;
    String transactionId;
    String totalPrice;

}
