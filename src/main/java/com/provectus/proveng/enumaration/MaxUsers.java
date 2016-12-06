package com.provectus.proveng.enumaration;

import org.springframework.beans.factory.annotation.Value;

public abstract class MaxUsers {

    @Value("${max_users.workshop}")
    public static final int MAX_WORKSHOP_MEMBERS = 10;

    @Value("${max_users.primary_group}")
    public static final int MAX_GROUP_MEMBERS = 10;

}
