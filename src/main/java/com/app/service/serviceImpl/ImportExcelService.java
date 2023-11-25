package com.app.service.serviceImpl;

import com.app.payload.response.APIResponse;
import com.app.payload.response.FailureAPIResponse;
import com.app.payload.response.SuccessAPIResponse;
import com.app.utils.ExcelToJavaMapper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImportExcelService {
    public <T> APIResponse uploadExcel(MultipartFile excel, Class<T> clazz, JpaRepository<T, Integer> repository) {
        try (XSSFWorkbook workbook = new XSSFWorkbook(excel.getInputStream())) {
            XSSFSheet sheet = workbook.getSheetAt(0);

            int successCount = 0;
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = sheet.getRow(i);
                T object;
                try {
                    object = ExcelToJavaMapper.mapExcelRowToObject(row, clazz);
                } catch (Exception e) {
                    return new FailureAPIResponse("Fail to import Excel: " + e.getMessage());
                }

                if (object != null) {
                    repository.save(object);
                    successCount++;
                }
            }

            return new SuccessAPIResponse("Upload Excel success. Imported records: " + successCount);
        } catch (IOException e) {
            e.printStackTrace();
            return new FailureAPIResponse("Fail to import Excel: " + e.getMessage());
        }
    }
}

