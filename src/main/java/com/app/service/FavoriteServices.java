package com.app.service;

import com.app.entity.Favorite;
import com.app.entity.Voucher;
import com.app.payload.request.FavoriteQueryParam;
import com.app.payload.response.APIResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FavoriteServices {
    APIResponse filterFavorite(FavoriteQueryParam favoriteQueryParam);
    APIResponse create(Favorite favorite);
    APIResponse update(Favorite favorite);
    APIResponse delete(Integer id);
    APIResponse uploadExcel(MultipartFile excel);
    APIResponse createBatch(List<Favorite> favorites);
}
