package com.provectus.proveng.service;

import com.provectus.proveng.domain.Material;

import java.util.List;

public interface MaterialService {

    Material create(Material material);

    List<Material> getMaterialsForUser(Long userId);

    Material getActiveById(Long id);

    Material update(Material material);

}
