package com.app.service.serviceImpl;

import com.app.entity.Blog;
import com.app.entity.BlogNotification;
import com.app.entity.Favorite;
import com.app.payload.request.FavoriteQueryParam;
import com.app.payload.response.APIResponse;
import com.app.payload.response.FailureAPIResponse;
import com.app.payload.response.SuccessAPIResponse;
import com.app.repository.FavoriteRepository;
import com.app.service.FavoriteServices;
import com.app.utils.PageUtils;
import com.app.utils.RequestParamsUtils;
import com.app.speficication.FavoriteSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavoriteServicesImpl implements FavoriteServices {
    @Autowired
    FavoriteRepository favoriteRepository;
    @Autowired
    FavoriteSpecification favoriteSpecification;
    @Autowired
    RequestParamsUtils requestParamsUtils;
    @Autowired
    CloudinaryService cloudinaryService;
    @Autowired
    ImportExcelService importExcelService;

    @Override
    public APIResponse filterFavorite(FavoriteQueryParam favoriteQueryParam) {
        Specification<Favorite> spec = favoriteSpecification.getFavoriteSpecitification(favoriteQueryParam);
        Pageable pageable = requestParamsUtils.getPageable(favoriteQueryParam);
        Page<Favorite> response = favoriteRepository.findAll(spec, pageable);
        return new APIResponse(PageUtils.toPageResponse(response));
    }

    @Override
    public APIResponse create(Favorite favorite) {
        favorite = favoriteRepository.save(favorite);
        return new SuccessAPIResponse(favorite);
    }

    @Override
    public APIResponse update(Favorite favorite) {
        if (favorite == null) {
            return new FailureAPIResponse("Favorite id is required!");
        }
        Favorite exists = favoriteRepository.findById(favorite.getId()).orElse(null);
        if (exists == null) {
            return new FailureAPIResponse("Cannot find favorite with id: " + favorite.getId());
        }

        favorite = favoriteRepository.save(favorite);
        return new SuccessAPIResponse(favorite);
    }

    @Override
    public APIResponse delete(Integer id) {
        try {
            favoriteRepository.deleteById(id);
            return new SuccessAPIResponse("Delete successfully!");
        } catch (Exception ex) {
            return new FailureAPIResponse(ex.getMessage());
        }
    }

    @Override
    public APIResponse uploadExcel(MultipartFile excel) {
        return importExcelService.uploadExcel(excel, Favorite.class, favoriteRepository);
    }

    @Override
    public APIResponse createBatch(List<Favorite> favorites) {
        List<Favorite> createdFavorites = new ArrayList<>();
        for (Favorite favorite : favorites) {
            Favorite createdBlog = favoriteRepository.save(favorite);
            createdFavorites.add(createdBlog);
        }
        return new SuccessAPIResponse(createdFavorites);
    }
}
