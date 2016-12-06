package com.provectus.proveng.repository;

import javax.transaction.Transactional;

//@Repository
@Transactional
public interface RepositoryInterface {

    void create();

    Object findById();

    void update();

    void delete();

}
