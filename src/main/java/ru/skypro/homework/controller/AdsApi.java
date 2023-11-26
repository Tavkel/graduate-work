package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;

import jakarta.validation.Valid;

import java.util.List;

@Validated
public interface AdsApi {

    @Operation(summary = "Добавление объявления", description = "", tags = {"Объявления"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = Ad.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized")})
    ResponseEntity<Ad> addAd(@Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema())
                             @RequestParam(value = "properties", required = false) CreateOrUpdateAd properties,
                             @Parameter(description = "file detail")
                             @Valid
                             @RequestPart("file") MultipartFile image);


    @Operation(summary = "Добавление комментария к объявлению", description = "", tags = {"Комментарии"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(
                    mediaType = "application/json", schema = @Schema(implementation = Comment.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Not found")})
    ResponseEntity<Comment> addComment(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema())
                                       @PathVariable("id") Integer id,
                                       @Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema())
                                       @Valid
                                       @RequestBody CreateOrUpdateComment body);


    @Operation(summary = "Удаление комментария", description = "", tags = {"Комментарии"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")})
    ResponseEntity<Void> deleteComment(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema())
                                       @PathVariable("adId") Integer adId,
                                       @Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema())
                                       @PathVariable("commentId") Integer commentId);


    @Operation(summary = "Получение информации об объявлении", description = "", tags = {"Объявления"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "OK",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExtendedAd.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Not found")})
    ResponseEntity<ExtendedAd> getAds(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema())
                                      @PathVariable("id") Integer id);


    @Operation(summary = "Получение объявлений авторизованного пользователя", description = "", tags = {"Объявления"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Ads.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized")})
    ResponseEntity<Ads> getAdsMe();


    @Operation(summary = "Получение всех объявлений", description = "", tags = {"Объявления"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Ads.class)))})
    ResponseEntity<Ads> getAllAds();


    @Operation(summary = "Получение комментариев объявления", description = "", tags = {"Комментарии"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Comments.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Not found")})
    ResponseEntity<Comments> getComments(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema())
                                         @PathVariable("id") Integer id);


    @Operation(summary = "Удаление объявления", description = "", tags = {"Объявления"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")})
    ResponseEntity<Void> removeAd(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema())
                                  @PathVariable("id") Integer id);


    @Operation(summary = "Обновление информации об объявлении", description = "", tags = {"Объявления"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Ad.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")})
    ResponseEntity<Ad> updateAds(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema())
                                 @PathVariable("id") Integer id,
                                 @Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema())
                                 @Valid
                                 @RequestBody CreateOrUpdateAd body);


    @Operation(summary = "Обновление комментария", description = "", tags = {"Комментарии"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Comment.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")})
    ResponseEntity<Comment> updateComment(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema())
                                          @PathVariable("adId") Integer adId,
                                          @Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema())
                                          @PathVariable("commentId") Integer commentId,
                                          @Parameter(in = ParameterIn.DEFAULT, description = "", schema = @Schema())
                                          @Valid
                                          @RequestBody CreateOrUpdateComment body);


    @Operation(summary = "Обновление картинки объявления", description = "", tags = {"Объявления"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/octet-stream",
                    array = @ArraySchema(schema = @Schema(implementation = byte[].class)))),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "Not found")})
    ResponseEntity<List<byte[]>> updateImage(@Parameter(in = ParameterIn.PATH, description = "", required = true, schema = @Schema())
                                             @PathVariable("id") Integer id,
                                             @Parameter(description = "file detail")
                                             @Valid
                                             @RequestPart("file") MultipartFile image);
}

