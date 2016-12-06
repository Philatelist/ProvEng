package com.provectus.proveng.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.provectus.proveng.domain.Material;
import com.provectus.proveng.exception.ErrorResponseException;
import com.provectus.proveng.service.MaterialService;
import com.provectus.proveng.service.UserService;
import com.provectus.proveng.utils.ResponseUtils;
import com.provectus.proveng.utils.view.MaterialView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class MaterialController {

    private static Logger logger = LogManager.getLogger(MaterialController.class);

    @Autowired
    MaterialService materialService;
    @Autowired
    UserService userService;

    /******
     * Create material
     ******/
    @RequestMapping(value = "/rest/v1/material", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(MaterialView.AllInfoLevel.class)
    ResponseEntity<Map<String, Object>> createMaterial(@RequestHeader("token") String token,
                                                       @RequestBody Material material) {
        logger.debug(">>> createMaterial");

        userService.getUserIdByToken(token);

        String message = null;
        if (material.getId() != 0)
            message = "material's id can't be not '0'";
        if (material.getName() == null)
            message = "material's name is missing";
        if (material.getMinLevel() == 0)
            message = "material's level is missing";
        if (material.getDescription() == null)
            message = "material's description is missing";
        if (material.getLink() == null)
            message = "material's link is missing";
        if (material.getType() == null)
            message = "material's type is missing";

        if (message != null)
            throw new ErrorResponseException("createMaterialError", message,
                    HttpStatus.BAD_REQUEST);

        material = materialService.create(material);

        logger.debug("<<< createMaterial");
        return ResponseUtils.buildSuccessfulResponse(material);

    }

    /*****
     * update material
     *****/
    @RequestMapping(value = "/rest/v1/material", method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(MaterialView.AllInfoLevel.class)
    ResponseEntity<Map<String, Object>> updateMaterial(@RequestHeader("token") String token,
                                                       @RequestBody Material material) {
        logger.debug(">>> updateMaterial()");

        userService.getUserIdByToken(token);

        String message = null;

        if (material.getId() == 0)
            message = "material's id can't be '0'";
        if (material.getName() == null)
            message = "material's name is missing";
        if (material.getMinLevel() == 0)
            message = "material's level is missing";
        if (material.getDescription() == null)
            message = "material's description is missing";
        if (material.getLink() == null)
            message = "material's link is missing";
        if (material.getType() == null)
            message = "material's type is missing";

        if (message != null)
            throw new ErrorResponseException("updateMaterialError", message,
                    HttpStatus.BAD_REQUEST);

        Material materialFromDb = materialService.getActiveById(material.getId());
        if (materialFromDb == null)
            throw new ErrorResponseException("updateMaterialError",
                    "material with this id doesn't exist", HttpStatus.BAD_REQUEST);

        material = materialService.update(material);

        logger.debug("<<< updateMaterial()");
        return ResponseUtils.buildSuccessfulResponse(material);

    }

    @RequestMapping(value = "/rest/v1/materials", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(MaterialView.ShortInfoLevel.class)
    ResponseEntity<Map<String, Object>> getMaterials(@RequestHeader("token") String token) {

        Long userId = userService.getUserIdByToken(token);
        List<Material> materials = materialService.getMaterialsForUser(userId);
        return ResponseUtils.buildSuccessfulResponse(materials);
    }

    @RequestMapping(value = "/rest/v1/material", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @JsonView(MaterialView.AllInfoLevel.class)
    ResponseEntity<Map<String, Object>> getMaterial(@RequestHeader("token") String token,
                                                    @RequestParam(value = "id") Long id) {
        logger.debug(">>> getMaterial");

        userService.getUserIdByToken(token);

        Material material = null;

        try {

            material = materialService.getActiveById(id);

        } catch (ErrorResponseException e) {

            throw new ErrorResponseException("getMaterialError", e.getErrorMessage(),
                    e.getHttpStatus());
        }

        logger.debug("<<< getMaterial");
        return ResponseUtils.buildSuccessfulResponse(material);

    }
}
