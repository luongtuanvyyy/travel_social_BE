package com.app.service.serviceImpl;

import com.app.entity.Payment;
import com.app.entity.Place;
import com.app.payload.request.PaymentQueryParam;
import com.app.payload.response.APIResponse;
import com.app.payload.response.FailureAPIResponse;
import com.app.payload.response.SuccessAPIResponse;
import com.app.repository.PaymentRepository;
import com.app.service.PaymentServices;
import com.app.speficication.PaymentSpecification;
import com.app.utils.PageUtils;
import com.app.utils.RequestParamsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PaymentServiceimpl implements PaymentServices {
    @Autowired
    PaymentSpecification paymentSpecification;
    @Autowired
    RequestParamsUtils requestParamsUtils;
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    CloudinaryService cloudinaryService;

    @Autowired
    ImportExcelService importExcelService;
    @Override
    public APIResponse filterPayment(PaymentQueryParam paymentQueryParam) {
        Specification<Payment> spec = paymentSpecification.getPaymentSpecitification(paymentQueryParam);
        Pageable pageable = requestParamsUtils.getPageable(paymentQueryParam);
        Page<Payment> response = paymentRepository.findAll(spec, pageable);
        return new APIResponse(PageUtils.toPageResponse(response));
    }
    @Override
    public APIResponse create(Payment payment) {
        payment = paymentRepository.save(payment);
        return new SuccessAPIResponse(payment);
    }

    @Override
    public APIResponse update(Payment payment) {
        if(payment == null){
            return  new FailureAPIResponse("Payment id is required!");
        }
        Payment exists = paymentRepository.findById(payment.getId()).orElse(null);
        if(exists == null){
            return  new FailureAPIResponse("Cannot find payment with id: "+payment.getId());
        }
        payment = paymentRepository.save(payment);
        return new SuccessAPIResponse(payment);
    }

    @Override
    public APIResponse delete(Integer id) {
        try {
            paymentRepository.deleteById(id);
            return new SuccessAPIResponse("Delete successfully!");
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse uploadExcel(MultipartFile excel) {
        return importExcelService.uploadExcel(excel, Payment.class, paymentRepository);
    }
}
