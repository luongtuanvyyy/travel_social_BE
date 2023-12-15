package com.app.service.serviceImpl;

import com.app.entity.BlogNotification;
import com.app.entity.Place;
import com.app.entity.Voucher;
import com.app.payload.request.BlogNotificationQueryParam;
import com.app.payload.response.APIResponse;
import com.app.payload.response.FailureAPIResponse;
import com.app.payload.response.SuccessAPIResponse;
import com.app.repository.BlogNotificationRepository;
import com.app.service.BlogNotificationServices;
import com.app.speficication.BlogNotificationSpecification;
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
public class BlogNotificationServicesImpl implements BlogNotificationServices {
    @Autowired
    BlogNotificationRepository blogNotificationRepository;
    @Autowired
    BlogNotificationSpecification blogNotificationSpecification;
    @Autowired
    RequestParamsUtils requestParamsUtils;
    @Autowired
    CloudinaryService cloudinaryService;
    @Autowired
    ImportExcelService importExcelService;

    @Override
    public APIResponse filterBlogNotification(BlogNotificationQueryParam blogNotificationQueryParam) {
        try {
        Specification<BlogNotification> spec = blogNotificationSpecification.getAccountSpecification(blogNotificationQueryParam);
        Pageable pageable = requestParamsUtils.getPageable(blogNotificationQueryParam);
        Page<BlogNotification> response = blogNotificationRepository.findAll(spec, pageable);
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
    public APIResponse create(BlogNotification blogNotification) {
        try {
            blogNotification = blogNotificationRepository.save(blogNotification);
            return new SuccessAPIResponse(blogNotification);
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse update(BlogNotification blogNotification) {
        try {
            if (blogNotification == null) {
                return new FailureAPIResponse("Blog Notification id is required!");
            }
            BlogNotification exists = blogNotificationRepository.findById(blogNotification.getId()).orElse(null);
            if (exists == null) {
                return new FailureAPIResponse("Cannot find blog notification with id: " + blogNotification.getId());
            }

            blogNotification = blogNotificationRepository.save(blogNotification);
            return new SuccessAPIResponse(blogNotification);
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse delete(Integer id) {
        try {
            blogNotificationRepository.deleteById(id);
            return new SuccessAPIResponse("Delete successfully!");
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse uploadExcel(MultipartFile excel) {
        return importExcelService.uploadExcel(excel, BlogNotification.class, blogNotificationRepository);
    }

    @Override
    public APIResponse createBatch(List<BlogNotification> blogNotifications) {
        List<BlogNotification> createdBlogNotifications = new ArrayList<>();
        for (BlogNotification blogNotification : blogNotifications) {
            BlogNotification createBlogNotification = blogNotificationRepository.save(blogNotification);
            createdBlogNotifications.add(createBlogNotification);
        }
        return new SuccessAPIResponse(createdBlogNotifications);
    }

    @Override
    public APIResponse getBlogNotificationByEmail(String email, BlogNotificationQueryParam blogNotificationQueryParam) {
        try {
            Specification<BlogNotification> spec = blogNotificationSpecification.getAccountSpecification(blogNotificationQueryParam);
            Pageable pageable = requestParamsUtils.getPageable(blogNotificationQueryParam);
            Page<BlogNotification> blogNotifications = blogNotificationRepository.findAllByEmail(email, pageable);
            return new SuccessAPIResponse(blogNotifications);
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

}
