package ru.skypro.homework.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.models.dto.*;


import java.util.List;

@RestController
@CrossOrigin(value = "http://localhost:3000")
public class AdsApiController implements AdsApi {
    private static final Logger logger = LoggerFactory.getLogger(AdsApiController.class);
    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    @org.springframework.beans.factory.annotation.Autowired
    public AdsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @RequestMapping(value = "/ads",
            produces = {"application/json"},
            consumes = {"multipart/form-data"},
            method = RequestMethod.POST)
    public ResponseEntity<Ad> addAd(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema())
                                    @RequestParam(value = "properties", required = false) CreateOrUpdateAd properties,
                                    @Parameter(description = "file detail")
                                    @Valid
                                    @RequestPart("file") MultipartFile image) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/ads/{id}/comments",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    public ResponseEntity<Comment> addComment(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema())
                                              @PathVariable("id") Integer id,
                                              @Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema())
                                              @Valid
                                              @RequestBody CreateOrUpdateComment body) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/ads/{adId}/comments/{commentId}",
            method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteComment(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema())
                                              @PathVariable("adId") Integer adId,
                                              @Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema())
                                              @PathVariable("commentId") Integer commentId) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/ads/{id}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<ExtendedAd> getAds(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema())
                                             @PathVariable("id") Integer id) {
        var result = new ExtendedAd();
        result.setPk(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/ads/me",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<Ads> getAdsMe() {
        var result = new Ads();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/ads",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<Ads> getAllAds() {
        var result = new Ads();
        result.setCount(0);
        result.setResults(List.of());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/ads/{id}/comments",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<Comments> getComments(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema())
                                                @PathVariable("id") Integer id) {
        var result = new Comments();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/ads/{id}",
            method = RequestMethod.DELETE)
    public ResponseEntity<Void> removeAd(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema())
                                         @PathVariable("id") Integer id) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/ads/{id}",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PATCH)
    public ResponseEntity<Ad> updateAds(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema())
                                        @PathVariable("id") Integer id,
                                        @Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema())
                                        @Valid
                                        @RequestBody CreateOrUpdateAd body) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/ads/{adId}/comments/{commentId}",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.PATCH)
    public ResponseEntity<Comment> updateComment(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema())
                                                 @PathVariable("adId") Integer adId,
                                                 @Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema())
                                                 @PathVariable("commentId") Integer commentId,
                                                 @Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema())
                                                 @Valid
                                                 @RequestBody CreateOrUpdateComment body) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @RequestMapping(value = "/ads/{id}/image",
            produces = {"application/octet-stream"},
            consumes = {"multipart/form-data"},
            method = RequestMethod.PATCH)
    public ResponseEntity<List<byte[]>> updateImage(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema())
                                                    @PathVariable("id") Integer id,
                                                    @Parameter(description = "file detail")
                                                    @Valid
                                                    @RequestPart("file") MultipartFile image) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
