package com.provectus.proveng.controller;


import com.provectus.proveng.utils.FalseResult;
import com.provectus.proveng.utils.RestException;
import com.provectus.proveng.utils.SuccessfulResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ValidateController {

    public static final String GTOKEN_INFO_URL_GOOGLE = "https://www.googleapis.com/oauth2/v3/tokeninfo?access_token=";
    private static final Logger log = LogManager.getLogger(ValidateController.class);

    static {
        log.info("Started ValidateController");
    }


    @Autowired
    SuccessfulResult successfulResult;

    /**
     * rest call for user info
     *
     * @param token String (token of our application)
     * @return entity in Json with next params:
     * { "aud":
     * "azp":
     * "sub":
     * "access_type":
     * "email_verified":
     * "scope":
     * "exp":
     * "expires_in":
     * "email":}
     */

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/rest/v1/validate_token",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public
    @ResponseBody
    ResponseEntity validateGtoken(@RequestHeader("access_token") String token) throws RestException {
        try {
            log.debug("INJECTED 'validateGtoken' access_token: " + token);
            String url = GTOKEN_INFO_URL_GOOGLE + token;

            RestTemplate restTemplate = new RestTemplate();

            Map resultMap = restTemplate.getForObject(url, HashMap.class);

            successfulResult = new SuccessfulResult(resultMap);

            log.debug(">>> RESULT 'validateGtoken' returns: " + successfulResult.toString());

            return successfulResult.getResult();

        } catch (Exception e) {
            return getErrorMapResponseEntity(e);
        }
    }

    public boolean isValidToken(String token) throws RestException {

        ResponseEntity entity = validateGtoken(token);

        return entity.getBody() == null;
    }

    private ResponseEntity getErrorMapResponseEntity(Exception e) {
        log.error(">>> MESSAGE ERROR - " + String.valueOf(e));

        String description = String.valueOf(e);

        FalseResult falseResult = new FalseResult(e.getCause().toString(), description, HttpStatus.BAD_REQUEST);
        log.debug(">>> ERROR returns: " + e.toString());
        return falseResult.getResult();
    }
}