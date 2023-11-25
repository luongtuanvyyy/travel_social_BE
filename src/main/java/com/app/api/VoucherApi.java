package com.app.api;

import com.app.entity.Voucher;
import com.app.payload.request.VoucherQueryParam;
import com.app.payload.response.APIResponse;
import com.app.service.VoucherServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api")
public class VoucherApi {
    @Autowired
    VoucherServices vourcherServices;

    @GetMapping("/public/vouchers")
    public ResponseEntity<?> getAllVoucher(VoucherQueryParam voucherQueryParam) {
        return ResponseEntity.ok(vourcherServices.filterVoucher(voucherQueryParam));
    }

    @PostMapping("/company/vouchers")
    public ResponseEntity<?> createVoucher(@RequestBody Voucher voucher) {
        APIResponse response = vourcherServices.create(voucher);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/company/vouchers")
    public ResponseEntity<?> updateVoucher(@RequestBody Voucher voucher) {
        APIResponse response = vourcherServices.update(voucher);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/company/vouchers")
    public ResponseEntity<?> deleteVoucher(@RequestParam("id") Integer id) {
        APIResponse response = vourcherServices.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/company/vouchers/upload-excel")
    public ResponseEntity<?> uploadExcel(@RequestPart(name = "excel") MultipartFile excel) {
        APIResponse response = vourcherServices.uploadExcel(excel);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/company/vouchers/batch")
    public ResponseEntity<?> createViewsBatch(@RequestBody List<Voucher> vouchers) {
        APIResponse response = vourcherServices.createBatch(vouchers);
        return ResponseEntity.ok().body(response);
    }
}
