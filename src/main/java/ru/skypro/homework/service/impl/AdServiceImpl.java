package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.helpers.mappers.AdMapper;
import ru.skypro.homework.models.domain.AdDomain;
import ru.skypro.homework.models.dto.Ad;
import ru.skypro.homework.models.dto.Ads;
import ru.skypro.homework.models.dto.CreateOrUpdateAd;
import ru.skypro.homework.models.dto.ExtendedAd;
import ru.skypro.homework.repositories.AdRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.UserService;

import java.util.NoSuchElementException;

@Service
@Slf4j
public class AdServiceImpl implements AdService {
    private final AdRepository repository;
    private final UserService userService;

    public AdServiceImpl(AdRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    @Override
    public Ads getAllAds() {
        var ads = repository.findAll().stream().map(AdMapper::adDomainToAd).toList();
        return new Ads(ads.size(), ads);
    }

    @Override
    public Ads getMyAds(Integer userId) {
        //todo replace stub
        userId  = 1;

        var ads = repository.findByUserId(userId).stream().map(AdMapper::adDomainToAd).toList();
        return new Ads(ads.size(), ads);
    }

    @Override
    public ExtendedAd getAd(Integer id) {
        return AdMapper.adDomainToExtendedAd(getAdDomain(id));
    }

    @Override
    public AdDomain getAdDomain(Integer id) {
        return repository.findById(id).orElseThrow(() -> {
            log.info("Ad with id {} was not found", id);
            return new NoSuchElementException("Ad not found");
        });
    }

    @Override
    public void deleteAd(Integer id) {
        var ad = repository.findById(id).orElseThrow(() -> {
            log.info("Ad with id {} was not found", id);
            return new NoSuchElementException("Ad not found");
        });
        repository.delete(ad);
    }

    @Override
    public Ad createOrUpdateAd(CreateOrUpdateAd ad, Integer id) {
        AdDomain model = null;
        if (id != null && id != 0) {
            model = repository.findById(id).orElseThrow(() -> {
                log.info("Ad with id {} was not found", id);
                return new NoSuchElementException("Ad not found");
            });
        }
        model = AdMapper.createOrUpdateAdToAdDomain(ad, model);

        //todo replace stub
        var user = userService.getUserDomain(1);
        model.setUser(user);

        var result = repository.save(model);
        return AdMapper.adDomainToAd(result);
    }

    @Override
    public byte[] updateImage(MultipartFile file) {
        //todo implement
        return new byte[0];
    }
}
