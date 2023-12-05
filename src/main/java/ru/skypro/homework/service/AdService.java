package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.models.domain.AdDomain;
import ru.skypro.homework.models.dto.Ad;
import ru.skypro.homework.models.dto.Ads;
import ru.skypro.homework.models.dto.CreateOrUpdateAd;
import ru.skypro.homework.models.dto.ExtendedAd;

public interface AdService {
    Ads getAllAds();
    Ads getMyAds(Integer userId);
    ExtendedAd getAd(Integer id);

    AdDomain getAdDomain(Integer id);

    void deleteAd(Integer id);
    Ad createOrUpdateAd(CreateOrUpdateAd ad, Integer id);
    byte[] updateImage(MultipartFile file);
}