package com.provectus.proveng.repository;

import com.provectus.proveng.domain.Material;

import java.util.List;

public interface MaterialRepository {

    void create(Material material);

    List<Material> getAllActive();

    Material getActiveById(Long id);

    Material update(Material material);


}
