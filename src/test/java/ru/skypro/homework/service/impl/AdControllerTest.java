package ru.skypro.homework.service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import ru.skypro.homework.models.dto.*;
import ru.skypro.homework.models.enums.Roles;
import ru.skypro.homework.repositories.AdRepository;
import ru.skypro.homework.repositories.CommentRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;

import javax.sql.DataSource;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AdControllerTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    AdService adService;

    @Autowired
    UserService userService;

    @Autowired
    ImageService imageService;

    @Autowired
    AdRepository adRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    DataSource dataSource;

    @Autowired
    AuthService authService;

    @LocalServerPort
    int port;

    private String url;

    private final String userName = "User";
    private final String adminName = "Admin";
    private final String password = "qwerty123";

    @BeforeAll
    void init() {
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        url = "http://localhost:" + port + "/ads";

        authService.register(new Register(adminName.toLowerCase() + "@mail.com", password, adminName, adminName, "+7 999 888 77 66", Roles.ADMIN));
        authService.register(new Register(userName.toLowerCase() + "@mail.com", password, userName, userName, "+7 111 222 33 44", Roles.USER));

        //Зачем sql? потому что могу и просто тестил варианты инициализации базы под тесты.
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(new ClassPathResource("seed.sql"));
        populator.execute(this.dataSource);
    }

    @Test
    void addAd_unauthorized_shouldReturnError_401() {
        CreateOrUpdateAd ad = new CreateOrUpdateAd("new ad title", 100, "new ad description");

        MultiValueMap body = new LinkedMultiValueMap<>();

        body.add("properties", ad);
        body.add("image", "01010101");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity;
        requestEntity = new HttpEntity<>(body, headers);

        var response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void addAd_authorized_shouldReturnCreatedAd_200() {
        CreateOrUpdateAd ad = new CreateOrUpdateAd("new ad title", 100, "new ad description");

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        body.add("properties", ad);
        body.add("image", new FileSystemResource(new File("src\\test\\resources\\test_image.jpg").getAbsolutePath()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> requestEntity;
        requestEntity = new HttpEntity<>(body, headers);

        var response = restTemplate
                .withBasicAuth(userName.toLowerCase() + "@mail.com", password)
                .exchange(
                        url,
                        HttpMethod.POST,
                        requestEntity,
                        Ad.class
                );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ad.getTitle(), response.getBody().getTitle());
        assertEquals(ad.getPrice(), response.getBody().getPrice());

        //Нужен ликбез по @transactional
        adRepository.deleteById(response.getBody().getPk());
    }

    @Test
    void addComment_unauthorized_shouldReturnError_401() {
        var response = restTemplate.exchange(
                url + "/100/comments",
                HttpMethod.POST,
                new HttpEntity<>(new CreateOrUpdateComment()),
                String.class
        );
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void addComment_authorized_shouldReturnError_404() {
        var response = restTemplate
                .withBasicAuth(userName.toLowerCase() + "@mail.com", password)
                .exchange(
                        url + "/100/comments",
                        HttpMethod.POST,
                        new HttpEntity<>(new CreateOrUpdateComment()),
                        String.class
                );
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void addComment_authorized_shouldReturnComment_200() {
        var text = "comment text";
        var response = restTemplate
                .withBasicAuth(userName.toLowerCase() + "@mail.com", password)
                .exchange(
                        url + "/1/comments",
                        HttpMethod.POST,
                        new HttpEntity<>(new CreateOrUpdateComment(text)),
                        Comment.class
                );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(text, response.getBody().getText());
        assertEquals(userName, response.getBody().getAuthorFirstName());

        commentRepository.deleteById(response.getBody().getPk());
    }

    @Test
    void deleteComment_unauthorized_shouldReturnError_401() {
        var response = restTemplate.exchange(
                url + "/1/comments/3",
                HttpMethod.DELETE,
                null,
                String.class
        );
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void deleteComment_authorized_shouldReturnError_404() {
        var response = restTemplate
                .withBasicAuth(userName.toLowerCase() + "@mail.com", password)
                .exchange(
                        url + "/1/comments/3",
                        HttpMethod.DELETE,
                        null,
                        String.class
                );
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteComment_forbidden_shouldReturnError_403() {
        var response = restTemplate
                .withBasicAuth(userName.toLowerCase() + "@mail.com", password)
                .exchange(
                        url + "/1/comments/1",
                        HttpMethod.DELETE,
                        null,
                        String.class
                );
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void deleteComment_authorized_shouldReturnOK_200() {
        var response = restTemplate
                .withBasicAuth(adminName.toLowerCase() + "@mail.com", password)
                .exchange(
                        url + "/1/comments/2",
                        HttpMethod.DELETE,
                        null,
                        Void.class
                );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(commentRepository.existsById(2));
    }

    @Test
    void getExtendedAd_unauthorized_shouldReturnError_401() {
        var response = restTemplate.exchange(
                url + "/100",
                HttpMethod.GET,
                null,
                String.class
        );
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void getExtendedAd_notFound_shouldReturnError_404() {
        var response = restTemplate
                .withBasicAuth(userName.toLowerCase() + "@mail.com", password)
                .exchange(
                        url + "/100",
                        HttpMethod.GET,
                        null,
                        String.class
                );
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getExtendedAd_shouldReturnExtendedAd_200() {
        var id = 3;
        var response = restTemplate
                .withBasicAuth(userName.toLowerCase() + "@mail.com", password)
                .exchange(
                        url + "/" + id,
                        HttpMethod.GET,
                        null,
                        ExtendedAd.class
                );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(id, response.getBody().getPk());
        assertEquals("/content/img" + id + ".jpg", response.getBody().getImage());
        assertEquals("description of ad #" + id, response.getBody().getDescription());
        assertEquals(userName.toLowerCase() + "@mail.com", response.getBody().getEmail());
        assertEquals(id * 100, response.getBody().getPrice());
    }

    @Test
    void getAdsMe_unauthorized_shouldReturnError_401() {
        var response = restTemplate.exchange(
                url + "/me",
                HttpMethod.GET,
                null,
                Ads.class
        );
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void getAdsMe_authorized_shouldReturnMyAds_200() {
        var response = restTemplate
                .withBasicAuth(userName.toLowerCase() + "@mail.com", password)
                .exchange(
                        url + "/me",
                        HttpMethod.GET,
                        null,
                        Ads.class
                );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        //see seed.sql for parameters
        assertEquals(2, response.getBody().getCount());
        for (var ad : response.getBody().getResults()) {
            assertEquals(2, ad.getAuthor());
        }
    }

    @Test
    void getAllAds_shouldReturnAllAds_200() {
        var response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                Ads.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().getCount() >= 4);
    }

    @Test
    void getComments_unauthorized_shouldReturnError_401() {
        var response = restTemplate.exchange(
                url + "/1/comments",
                HttpMethod.GET,
                null,
                String.class
        );
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void getComments_authorized_shouldReturnComments_200() {
        var response = restTemplate
                .withBasicAuth(userName.toLowerCase() + "@mail.com", password)
                .exchange(
                        url + "/1/comments",
                        HttpMethod.GET,
                        null,
                        Comments.class
                );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().getCount() > 0);
    }


    @Test
    void deleteAd_unauthorized_shouldReturnError_401() {
        var response = restTemplate.exchange(
                url + "/1",
                HttpMethod.DELETE,
                null,
                String.class
        );
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void deleteAd_authorized_shouldReturnError_404() {
        var response = restTemplate
                .withBasicAuth(userName.toLowerCase() + "@mail.com", password)
                .exchange(
                        url + "/100",
                        HttpMethod.DELETE,
                        null,
                        String.class
                );
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteAd_forbidden_shouldReturnError_403() {
        var response = restTemplate
                .withBasicAuth(userName.toLowerCase() + "@mail.com", password)
                .exchange(
                        url + "/1",
                        HttpMethod.DELETE,
                        null,
                        String.class
                );
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void deleteAd_authorized_shouldReturnOK_200() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("addAd.sql"));
        populator.execute(this.dataSource);
        var id = adRepository.findAll().stream().filter(a -> a.getPrice() == 999).findFirst().get().getId();
        var response = restTemplate
                .withBasicAuth(adminName.toLowerCase() + "@mail.com", password)
                .exchange(
                        url + "/" + id,
                        HttpMethod.DELETE,
                        null,
                        Void.class
                );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(adRepository.existsById(id));
    }

    @Test
    void updateAd_unauthorized_shouldReturnError_401() {
        var response = restTemplate.exchange(
                url + "/1",
                HttpMethod.PATCH,
                new HttpEntity<>(new CreateOrUpdateAd()),
                String.class
        );
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void updateAd_authorized_shouldReturnError_404() {
        var response = restTemplate
                .withBasicAuth(userName.toLowerCase() + "@mail.com", password)
                .exchange(
                        url + "/100",
                        HttpMethod.PATCH,
                        new HttpEntity<>(new CreateOrUpdateAd()),
                        String.class
                );
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void updateAd_forbidden_shouldReturnError_403() {
        var response = restTemplate
                .withBasicAuth(userName.toLowerCase() + "@mail.com", password)
                .exchange(
                        url + "/1",
                        HttpMethod.PATCH,
                        new HttpEntity<>(new CreateOrUpdateAd()),
                        String.class
                );
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void updateAd_authorized_shouldReturnOK_200() {
        var title = "new title for ad 1";
        var response = restTemplate
                .withBasicAuth(adminName.toLowerCase() + "@mail.com", password)
                .exchange(
                        url + "/1",
                        HttpMethod.PATCH,
                        new HttpEntity<>(new CreateOrUpdateAd(title, null, null)),
                        Ad.class
                );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(title, response.getBody().getTitle());
        assertEquals(100, response.getBody().getPrice());
    }

    @Test
    void updateComment_unauthorized_shouldReturnError_401() {
        var response = restTemplate.exchange(
                url + "/1/comments/1",
                HttpMethod.PATCH,
                new HttpEntity<>(new CreateOrUpdateComment()),
                String.class
        );
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void updateComment_authorized_shouldReturnError_404() {
        var response = restTemplate
                .withBasicAuth(userName.toLowerCase() + "@mail.com", password)
                .exchange(
                        url + "/1/comments/122",
                        HttpMethod.PATCH,
                        new HttpEntity<>(new CreateOrUpdateAd()),
                        String.class
                );
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void updateComment_forbidden_shouldReturnError_403() {
        var response = restTemplate
                .withBasicAuth(userName.toLowerCase() + "@mail.com", password)
                .exchange(
                        url + "/1/comments/1",
                        HttpMethod.PATCH,
                        new HttpEntity<>(new CreateOrUpdateAd()),
                        String.class
                );
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void updateComment_authorized_shouldReturnOK_200() {
        var text = "new text for comment 1";
        var response = restTemplate
                .withBasicAuth(adminName.toLowerCase() + "@mail.com", password)
                .exchange(
                        url + "/1/comments/1",
                        HttpMethod.PATCH,
                        new HttpEntity<>(new CreateOrUpdateComment(text)),
                        Comment.class
                );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(text, response.getBody().getText());
    }


}