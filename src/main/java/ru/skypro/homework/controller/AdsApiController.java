package ru.skypro.homework.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.models.dto.*;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.CommentService;


import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@Slf4j
public class AdsApiController implements AdsApi {
    private final HttpServletRequest request;
    private final AdService adService;
    private final CommentService commentService;
    @Autowired
    public AdsApiController(HttpServletRequest request, AdService adService, CommentService commentService) {
        this.request = request;
        this.adService = adService;
        this.commentService = commentService;
    }

    @RequestMapping(value = "/ads",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            method = RequestMethod.POST)
    public ResponseEntity<Ad> addAd(CreateOrUpdateAd properties, MultipartFile image) {
        //todo implement image handling
        var result = adService.createOrUpdateAd(properties, 0);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/ads/{id}/comments",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    public ResponseEntity<Comment> addComment(@PathVariable("id") Integer adId, CreateOrUpdateComment comment) {
        return new ResponseEntity<>(commentService.createOrUpdateComment(comment,0, adId), HttpStatus.OK);
    }

    @RequestMapping(value = "/ads/{adId}/comments/{commentId}",
            method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteComment(@PathVariable("adId") Integer adId, @PathVariable("commentId") Integer commentId) {
        commentService.deleteComment(commentId, adId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/ads/{id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<ExtendedAd> getAds(@PathVariable("id") Integer id) {

        return new ResponseEntity<>(adService.getAd(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/ads/me",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<Ads> getAdsMe() {
        //todo remove stub
        return new ResponseEntity<>(adService.getMyAds(1), HttpStatus.OK);
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
    public ResponseEntity<Comments> getComments(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(commentService.getCommentsForAd(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/ads/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<Void> removeAd(@PathVariable("id") Integer id) {
        adService.deleteAd(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/ads/{id}",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PATCH)
    public ResponseEntity<Ad> updateAds(@PathVariable("id") Integer id, @RequestBody CreateOrUpdateAd payload) {
        return new ResponseEntity<>(adService.createOrUpdateAd(payload, id), HttpStatus.OK);
    }

    @RequestMapping(value = "/ads/{adId}/comments/{commentId}",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PATCH)
    public ResponseEntity<Comment> updateComment(@PathVariable("adId") Integer adId,
                                                 @PathVariable("commentId") Integer commentId,
                                                 @RequestBody CreateOrUpdateComment payload) {
        return new ResponseEntity<>(commentService.createOrUpdateComment(payload, commentId, adId), HttpStatus.OK);
    }

    //todo задать вопрос про возвращаемый тип. ВТФ?! Откуда в спецификации взялась коллекция массивов?!
    @RequestMapping(value = "/ads/{id}/image",
            produces = {"application/octet-stream"},
            consumes = {"multipart/form-data"},
            method = RequestMethod.PATCH)
    public ResponseEntity<String> updateImage(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema())
                                                    @PathVariable("id") Integer id,
                                                    @Parameter(description = "file detail")
                                                    @Valid
                                                    @RequestPart("file") MultipartFile image) {
        return new ResponseEntity<>(Arrays.toString(adService.updateImage(image)), HttpStatus.OK);
    }
}
