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
//@FeignClient(name = "oauth2googleapi", url = "https://accounts.google.com/o/oauth2")
public interface GoogleAccountApiClient {

    // revoke google user using valid access token or refresh token
    // get https://accounts.google.com/o/oauth2/revoke?token={token}
    //@Headers("Content-type: application/x-www-form-urlencoded")
//    @GetMapping(value = "/revoke")
//    AccessTokenResponse revokeToken(@RequestHeader Map<String, String> headerMap,
//                                    @RequestParam("token") String token) throws FeignException;
}