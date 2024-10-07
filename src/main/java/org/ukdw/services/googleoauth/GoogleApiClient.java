package org.ukdw.services.googleoauth;

//import feign.FeignException;
//import org.springframework.cloud.openfeign.FeignClient;

/**
 * Project: SRM-BE
 * Package: com.srmbe.service.googleoauth
 * <p>
 * User: dendy
 * Date: 29/08/2020
 * Time: 7:53
 * <p>
 * Description : GoogleApiClient using feign
 */
//@FeignClient(name = "googleapi", url = "https://www.googleapis.com")
public interface GoogleApiClient {

    // verify access token to google apis at https://www.googleapis.com/oauth2/v1/tokeninfo/?access_token={token}
//    @GetMapping(value = "/oauth2/v1/tokeninfo", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    AccessTokenResponse verifyAccessToken(@RequestParam("access_token") String accessToken) throws FeignException;
}
