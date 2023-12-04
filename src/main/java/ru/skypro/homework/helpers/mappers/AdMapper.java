package ru.skypro.homework.helpers.mappers;

import ru.skypro.homework.models.domain.AdDomain;
import ru.skypro.homework.models.dto.Ad;
import ru.skypro.homework.models.dto.CreateOrUpdateAd;
import ru.skypro.homework.models.dto.ExtendedAd;

public class AdMapper {
    public static AdDomain createOrUpdateAdToAdDomain(CreateOrUpdateAd dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Tried to map null to AdDomain");
        }
        return new AdDomain()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .price(dto.getPrice());
    }

    public static Ad adDomainToAd(AdDomain model) {
        if (model == null) {
            throw new IllegalArgumentException("Tried to map null to Ad");
        }
        return new Ad()
                .author(model.getUser().getId())
                .image(model.getImageUrl())
                .pk(model.getId())
                .price(model.getPrice())
                .title(model.getTitle());
    }

    public static ExtendedAd adDomainToExtendedAd(AdDomain model) {
        if (model == null) {
            throw new IllegalArgumentException("Tried to map null to ExtendedAd");
        }
        return new ExtendedAd()
                .pk(model.getId())
                .authorFirstName(model.getUser().getFirstName())
                .authorLastName(model.getUser().getFirstName())
                .description(model.getDescription())
                .email(model.getUser().getEmail())
                .image(model.getImageUrl())
                .phone(model.getUser().getPhone())
                .price(model.getPrice())
                .title(model.getTitle());
    }
}
