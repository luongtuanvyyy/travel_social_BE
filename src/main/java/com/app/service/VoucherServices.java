package com.app.service;

import com.app.entity.Voucher;
import com.app.payload.request.VoucherQueryParam;
import com.app.payload.response.APIResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VoucherServices {
    APIResponse filterVoucher(VoucherQueryParam voucherQueryParam);
    APIResponse create(Voucher voucher);
    APIResponse update(Voucher voucher);
    APIResponse delete(Integer id);
    APIResponse uploadExcel(MultipartFile excel);
    APIResponse createBatch(List<Voucher> vouchers);
}
