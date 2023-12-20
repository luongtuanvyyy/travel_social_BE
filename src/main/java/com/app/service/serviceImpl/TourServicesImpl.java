package com.app.service.serviceImpl;


import com.app.dto.AccountData;
import com.app.dto.TourDto;
import com.app.entity.Tour;
import com.app.entity.TourDetail;
import com.app.payload.request.TourQueryParam;
import com.app.payload.response.APIResponse;
import com.app.payload.response.FailureAPIResponse;
import com.app.payload.response.SuccessAPIResponse;
import com.app.repository.TourRepository;
import com.app.service.TourServices;
import com.app.speficication.TourSpecification;
import com.app.utils.PageUtils;
import com.app.utils.RequestParamsUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TourServicesImpl implements TourServices {
    @Autowired
    TourRepository tourRepository;

    @Autowired
    RequestParamsUtils requestParamsUtils;

    @Autowired
    TourSpecification tourSpecification;

    @Autowired
    CloudinaryService cloudinaryService;

    @Autowired
    ImportExcelService importExcelService;

    @Autowired
    ModelMapper modelMapper;


//    @Override
//    public List<Users> findAll() {
//        return userRepository.findAll();
//    }

    //    @Override
//    public List<Users> searchUsersByFullName(String searchKeyword) {
//        // Xử lý trước từ khóa tìm kiếm để loại bỏ dấu và chuyển thành chữ thường
//        String processedKeyword = userRepository.removeDiacritics(searchKeyword.toLowerCase());
//        return userRepository.findByFullNameIgnoreCaseContaining(processedKeyword);
//    }
    @Override
    public APIResponse filterTour(TourQueryParam tourQueryParam) {
        try {
            System.out.println("===getIsActivated: " + tourQueryParam.getIsActivated());
            Specification<Tour> spec = tourSpecification.getTourSpecification(tourQueryParam);
            Pageable pageable = requestParamsUtils.getPageable(tourQueryParam);
            Page<Tour> response = tourRepository.findAll(spec, pageable);
            if (response.isEmpty()) {
                return new APIResponse(false, "No data found");
            } else {
                return new APIResponse(PageUtils.toPageResponse(response));
            }
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }


    @Override
    public APIResponse filterTourDiscount(TourQueryParam tourQueryParam) {
        try {
            Specification<Tour> spec = tourSpecification.getTourSpecification(tourQueryParam);
            Pageable pageable = requestParamsUtils.getPageable(tourQueryParam);
            Page<Tour> response = tourRepository.DiscountIsNotNull(spec, pageable);
            if (response.isEmpty()) {
                return new APIResponse(false, "No data found");
            } else {
                return new APIResponse(PageUtils.toPageResponse(response));
            }
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse filterNewlyPosted(TourQueryParam tourQueryParam) {
        try {

            Specification<Tour> spec = tourSpecification.isNewlyPosted();
            Pageable pageable = requestParamsUtils.getPageable(tourQueryParam);
            Page<Tour> response = tourRepository.findAll(spec, pageable);
            if (response.isEmpty()) {
                return new APIResponse(false, "No data found");
            } else {
                return new APIResponse(PageUtils.toPageResponse(response));
            }
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse getAccountByTourId(Integer id, TourQueryParam tourQueryParam) {
        try {

            Pageable pageable = requestParamsUtils.getPageable(tourQueryParam);
            Page<AccountData> response = tourRepository.getCompanyCreatedBY(id, pageable);
            if (response.isEmpty()) {
                return new APIResponse(false, "No data found");
            } else {
                return new APIResponse(PageUtils.toPageResponse(response));
            }
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse findTourDetailById(Integer id, TourQueryParam tourQueryParam) {
        try {

            Pageable pageable = requestParamsUtils.getPageable(tourQueryParam);
            Page<TourDto> response = tourRepository.findTourDetailById(id, pageable);
            System.out.println(response);
            if (response.isEmpty()) {
                return new APIResponse(false, "No data found");
            } else {
                return new APIResponse(PageUtils.toPageResponse(response));
            }
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse findbyid(Integer id) {
        try {
            Optional<Tour> response = tourRepository.findTourById(id);
            if (response.isEmpty()) {
                return new APIResponse(false, "No data found");
            } else {
                return new APIResponse(response);
            }
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse create(Tour tour) {
        try {
            if (tour.getName() == null || tour.getName().trim().isEmpty()) {
                throw new IllegalArgumentException("Tour name cannot be empty");
            }

            tour = tourRepository.save(tour);
            return new SuccessAPIResponse("Tạo mới Tour thành công ");
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse update(Tour tour) {
        try {
            if (tour.getId() == null) {
                return new FailureAPIResponse("Tour id is required!");
            }
            if (tour.getName() == null || tour.getName().trim().isEmpty()) {
                throw new IllegalArgumentException("Tour name cannot be empty");
            }
            Tour existingTour = tourRepository.findById(tour.getId()).orElse(null);
            if (existingTour == null) {
                return new FailureAPIResponse("Cannot find tour with id: " + tour.getId());
            }

            // Map chi tiết tour
            List<Integer> errorIds = new ArrayList<>();
            List<TourDetail> updatedTourDetails = new ArrayList<>();

            Boolean pass;
            for (TourDetail updatedDetail : tour.getTourDetailList() ) {
                for (TourDetail tourDetail : existingTour.getTourDetailList()) {
                    pass = false;
                    if(tourDetail.getId() == updatedDetail.getId()) {
                        modelMapper.map(updatedDetail, tourDetail);
                        updatedTourDetails.add(tourDetail);
                        pass = true;
                    }
                    if(!pass) {
                        errorIds.add(updatedDetail.getId());
                    }
                }
            }

            if (!errorIds.isEmpty()) {
                // Nếu có ID lỗi, trả về APIResponse thất bại với danh sách ID lỗi
                String errorMessage = "Invalid TourDetail IDs: " + errorIds.toString();
                return new FailureAPIResponse(errorMessage);
            }

            // Cập nhật danh sách chi tiết tour của existingTour với danh sách đã cập nhật
            existingTour.setTourDetailList(updatedTourDetails);

            modelMapper.map(tour, existingTour);

            // Lưu lại existingTour
            tourRepository.save(existingTour);

            // Trả về SuccessAPIResponse hoặc thông báo khác tùy thuộc vào kết quả
            return new SuccessAPIResponse("Tour details updated successfully");
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse delete(Integer id) {
        try {
            tourRepository.deleteById(id);
            return new SuccessAPIResponse("Delete successfully!");
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse uploadExcel(MultipartFile excel) {
        return importExcelService.uploadExcel(excel, Tour.class, tourRepository);
    }

    @Override
    public APIResponse createBatch(List<Tour> tours) {
        List<Tour> createdTours = new ArrayList<>();
        for (Tour tour : tours) {
            Tour createdTour = tourRepository.save(tour);
            createdTours.add(createdTour);
        }
        return new SuccessAPIResponse(createdTours);
    }


}
