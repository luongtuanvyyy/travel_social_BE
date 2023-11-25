package com.app.service.serviceImpl;
import com.app.entity.Vehicle;
import com.app.entity.Voucher;
import com.app.payload.request.VoucherQueryParam;
import com.app.payload.response.APIResponse;
import com.app.payload.response.FailureAPIResponse;
import com.app.payload.response.SuccessAPIResponse;
import com.app.repository.VoucherRepository;
import com.app.service.VoucherServices;
import com.app.speficication.VoucherSpecification;
import com.app.utils.PageUtils;
import com.app.utils.RequestParamsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class VoucherServicesImpl implements VoucherServices {
    @Autowired
    VoucherRepository voucherRepository;
    @Autowired
    VoucherSpecification voucherSpecification;

    @Autowired
    RequestParamsUtils requestParamsUtils;

    @Autowired
    ImportExcelService importExcelService;
    @Override
    public APIResponse filterVoucher(VoucherQueryParam voucherQueryParam) {
        Specification<Voucher> spec = voucherSpecification.getVoucherSpecification(voucherQueryParam);
        Pageable pageable = requestParamsUtils.getPageable(voucherQueryParam);
        Page<Voucher> response = voucherRepository.findAll(spec, pageable);
        return new APIResponse(PageUtils.toPageResponse(response));
    }

    @Override
    public APIResponse create(Voucher voucher) {
        voucher = voucherRepository.save(voucher);
        return new SuccessAPIResponse(voucher);
    }

    @Override
    public APIResponse update(Voucher voucher) {
        if (voucher == null) {
            return new FailureAPIResponse("Voucher id is required!");
        }
        Voucher exists = voucherRepository.findById(voucher.getId()).orElse(null);
        if (exists == null) {
            return new FailureAPIResponse("Cannot find voucher with id: " + voucher.getId());
        }

        voucher = voucherRepository.save(voucher);
        return new SuccessAPIResponse(voucher);
    }

    @Override
    public APIResponse delete(Integer id) {
        try {
            voucherRepository.deleteById(id);
            return new SuccessAPIResponse("Delete successfully!");
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse uploadExcel(MultipartFile excel) {
        return importExcelService.uploadExcel(excel, Voucher.class, voucherRepository);
    }

    @Override
    public APIResponse createBatch(List<Voucher> vouchers) {
        List<Voucher> createdVouchers = new ArrayList<>();
        for (Voucher voucher : vouchers) {
            Voucher createdVoucher = voucherRepository.save(voucher);
            createdVouchers.add(createdVoucher);
        }
        return new SuccessAPIResponse(createdVouchers);
    }
}
