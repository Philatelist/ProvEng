package com.provectus.proveng.controller;

import com.provectus.proveng.domain.Role;
import com.provectus.proveng.service.RoleService;
import com.provectus.proveng.utils.FalseResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.PrintWriter;
import java.io.StringWriter;

@RestController
public class RoleController {

    private static final Logger log = LogManager.getLogger(UserDataController.class);

    static {
        log.info("Started UserDataController");
    }

    @Autowired
    private RoleService roleService;

    /**
     * Create a new role with an auto-generated id and name as passed
     * values.
     */
    @RequestMapping(value = "/rest/db/role",
            method = RequestMethod.POST/*,
            produces = MediaType.APPLICATION_JSON_VALUE*/)
    public
    @ResponseBody
    String createRoleInDB(String name) {
        try {
            log.debug(">>> createRoleInDB... " +
                    "\nWITH injected name: " + name);
            Role role = new Role(name);
            log.debug(">> \nCREATED 'createRoleInDB' role WITH: " + role.toString());
            roleService.create(role);
            log.debug(">> \nSEND 'createRoleInDB' to DAO role: " + role.toString());

        } catch (Exception e) {
            e.printStackTrace();

            log.debug("FULL ERROR 'createRoleInDB' getStacktrace: " + e.getStackTrace());
            log.error("<<< createRoleInDB...WITH ERROR 'createRoleInDB': ", e);

            return "Error creating the role: " + getErrorMapResponseEntity(e).toString();
        }
        log.debug("<<< createRoleInDB... WITH result: Role succesfully created!");
        return "Role succesfully created!";
    }

    /**
     * Delete the role with the passed name.
     */
    @RequestMapping(value = "/rest/db/deleteRoleByName",
            method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteRoleByName(String name) {
        try {
            log.debug("INJECTED 'deleteRoleByName' name: " + name);
            Role role = new Role(name);
            log.debug("CREATED 'deleteRoleByName' role: " + role.toString());
            roleService.delete(role);
        } catch (Exception ex) {
            return getError(ex);
        }
        log.debug("MESSAGE 'deleteRoleByName': User succesfully deleted!");

        return "User succesfully deleted!";

    }

    /**
     * Delete the role with the passed id.
     */
    @RequestMapping(value = "/rest/db/role",
            method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteRoleById(Long id) {
        try {
            log.debug("INJECTED 'deleteRoleById' id: " + id);
            Role role = new Role(id);
            log.debug("CREATED 'deleteRoleById' role: " + role.toString());
            roleService.delete(role);
        } catch (Exception ex) {
            return getError(ex);
        }
        log.debug("<<< deleteRoleFromDB... WITH result: Role succesfully deleted!");

        return "Role succesfully deleted!";
    }

    private String getError(Exception ex) {
        log.error("MESSAGE ERROR - " + String.valueOf(ex));

        StringWriter stringWriter = new StringWriter();
        ex.printStackTrace(new PrintWriter(stringWriter));
        log.debug("debug <<< ERROR return: " + stringWriter.toString());
        return "Error deleting the role: " + ex.toString();
    }

    /**
     * Retrieve the id for the role with the passed name.
     */
    @RequestMapping(value = "/rest/db/role",
            method = RequestMethod.GET)
    @ResponseBody
    public String getRoleByName(String name) {
        String roleId;
        try {
            Role role = roleService.getByName(name);
            roleId = String.valueOf(role.getId());
        } catch (Exception ex) {
            return "Role not found: " + ex.toString();
        }
        return "The role id is: " + roleId;
    }

    /**
     * Update the name for the role indentified by the passed id.
     */
    @RequestMapping(value = "/rest/db/role",
            method = RequestMethod.PUT)
    @ResponseBody
    public String updateRoleInDB(long id, String name) {
        try {
            log.debug(">>> updateUserInDB...");
            Role role = roleService.getById(id);
            role.setName(name);
            roleService.update(role);
        } catch (Exception ex) {
            StringWriter stringWriter = new StringWriter();
            ex.printStackTrace(new PrintWriter(stringWriter));
            log.debug("debug <<< createRoleInDB...WITH ERROR: " + stringWriter.toString());
            return "Error updating the role: " + ex.toString();
        }
        log.debug("<<< updateRoleInDB... WITH result: Role succesfully updated!");

        return "Role succesfully updated!";
    }

    private ResponseEntity getErrorMapResponseEntity(Exception e) {
        log.error(">>> MESSAGE ERROR - " + String.valueOf(e));

        String description = String.valueOf(e);

        FalseResult falseResult = new FalseResult(e.getCause().toString(), description, HttpStatus.BAD_REQUEST);
        log.debug(">>> ERROR returns: " + e.toString());
        return falseResult.getResult();
    }
}
