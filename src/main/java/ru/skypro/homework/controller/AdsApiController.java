package ru.skypro.homework.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.models.dto.*;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.CommentService;


import java.util.Arrays;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@Slf4j
public class AdsApiController implements AdsApi {
    private final AdService adService;
    private final CommentService commentService;

    public AdsApiController(AdService adService, CommentService commentService) {
        this.adService = adService;
        this.commentService = commentService;
    }

    @RequestMapping(value = "/ads",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            method = RequestMethod.POST)
    public ResponseEntity<Ad> addAd(CreateOrUpdateAd properties,
                                    MultipartFile image) {
        var result = adService.createAd(properties, image);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/ads/{id}/comments",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    public ResponseEntity<Comment> addComment(@PathVariable("id") Integer adId,
                                              CreateOrUpdateComment comment) {
        return new ResponseEntity<>(commentService.createOrUpdateComment(comment, 0, adId), HttpStatus.OK);
    }

    @RequestMapping(value = "/ads/{adId}/comments/{commentId}",
            method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteComment(@PathVariable Integer adId,
                                              @PathVariable Integer commentId) {
        commentService.deleteComment(commentId, adId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/ads/{id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<ExtendedAd> getExtendedAd(@PathVariable Integer id) {
        return new ResponseEntity<>(adService.getAd(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/ads/me",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<Ads> getAdsMe() {
        return new ResponseEntity<>(adService.getMyAds(), HttpStatus.OK);
    }

    @RequestMapping(value = "/ads",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<Ads> getAllAds() {
        return new ResponseEntity<>(adService.getAllAds(), HttpStatus.OK);
    }

    @RequestMapping(value = "/ads/{id}/comments",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<Comments> getComments(@PathVariable Integer id) {
        return new ResponseEntity<>(commentService.getCommentsForAd(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/ads/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteAd(@PathVariable Integer id) {
        adService.deleteAd(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/ads/{id}",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PATCH)
    public ResponseEntity<Ad> updateAds(@PathVariable Integer id,
                                        @RequestBody CreateOrUpdateAd payload) {
        return new ResponseEntity<>(adService.updateAd(payload, id), HttpStatus.OK);
    }

    @RequestMapping(value = "/ads/{adId}/comments/{commentId}",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PATCH)
    public ResponseEntity<Comment> updateComment(@PathVariable Integer adId,
                                                 @PathVariable Integer commentId,
                                                 @RequestBody CreateOrUpdateComment payload) {
        return new ResponseEntity<>(commentService.createOrUpdateComment(payload, commentId, adId), HttpStatus.OK);
    }

    @RequestMapping(value = "/ads/{id}/image",
            produces = {"application/octet-stream"},
            consumes = {"multipart/form-data"},
            method = RequestMethod.PATCH)
    public ResponseEntity<String> updateImage(@PathVariable Integer id,
                                              @Valid
                                              MultipartFile image) {
        return new ResponseEntity<>(Arrays.toString(adService.updateImage(id, image)), HttpStatus.OK);
    }
}
