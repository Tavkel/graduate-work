package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.exceptions.ActionForbiddenException;
import ru.skypro.homework.helpers.mappers.AdMapper;
import ru.skypro.homework.models.domain.AdDomain;
import ru.skypro.homework.models.dto.Ad;
import ru.skypro.homework.models.dto.Ads;
import ru.skypro.homework.models.dto.CreateOrUpdateAd;
import ru.skypro.homework.models.dto.ExtendedAd;
import ru.skypro.homework.repositories.AdRepository;
import ru.skypro.homework.security.utility.PermissionChecker;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

import java.io.IOException;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class AdServiceImpl implements AdService {
    private final AdRepository repository;
    private final UserService userService;
    private final ImageService imageService;

    public AdServiceImpl(AdRepository repository, UserService userService, ImageService imageService) {
        this.repository = repository;
        this.userService = userService;
        this.imageService = imageService;
    }

    @Override
    public Ads getAllAds() {
        var ads = repository.findAll().stream().map(AdMapper::adDomainToAd).toList();
        return new Ads(ads.size(), ads);
    }

    @Override
    public Ads getMyAds() {
        var ads = repository.findByUserId(userService.getUserId()).stream().map(AdMapper::adDomainToAd).toList();
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

        var user = userService.getUserDomain();
        if (!PermissionChecker.isActionAllowed(user, ad)) throw new ActionForbiddenException();

        repository.delete(ad);

        var filename = ad.getImageUrl().substring(ad.getImageUrl().lastIndexOf("/") + 1);
        try {
            imageService.deleteImage(filename, AdDomain.class.getSimpleName());
        } catch (IOException e) {
            log.error("failed to delete file {}", filename);
        }

    }

    @Override
    public Ad createAd(CreateOrUpdateAd ad, MultipartFile image) {
        var model = AdMapper.createOrUpdateAdToAdDomain(ad);
        try {
            var url = "/content/" + imageService.saveImage(image, model);
            model.setUser(userService.getUserDomain());
            model.setImageUrl(url);
        } catch (IOException e) {
            throw new RuntimeException(e); //todo specify exception
        }

        return AdMapper.adDomainToAd(repository.save(model));
    }

    @Override
    public Ad updateAd(CreateOrUpdateAd ad, Integer id) {
        var model = repository.findById(id).orElseThrow(() -> {
            log.info("Ad with id {} was not found", id);
            return new NoSuchElementException("Ad not found");
        });

        var user = userService.getUserDomain();
        if (!PermissionChecker.isActionAllowed(user, model)) throw new ActionForbiddenException();

        model.title(ad.getTitle() != null? ad.getTitle() : model.getTitle())
                .description(ad.getDescription() != null? ad.getDescription() : model.getDescription())
                .price(ad.getPrice() != null? ad.getPrice() : model.getPrice());
        return AdMapper.adDomainToAd(repository.save(model));
    }

    @Override
    public byte[] updateImage(Integer id, MultipartFile file) {
        var model = getAdDomain(id);
        try {
            model.setImageUrl( "/content/" + imageService.saveImage(file, model));
        } catch (IOException e) {
            throw new RuntimeException(e); //todo specify exception
        }
        repository.save(model);
        return new byte[0];
    }
}
