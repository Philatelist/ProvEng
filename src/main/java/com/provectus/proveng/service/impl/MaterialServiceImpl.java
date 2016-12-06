package com.provectus.proveng.service.impl;

import com.provectus.proveng.domain.Material;
import com.provectus.proveng.repository.MaterialRepository;
import com.provectus.proveng.service.MaterialService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service("materialService")
@Transactional
public class MaterialServiceImpl implements MaterialService {

    private static final Logger logger = LogManager.getLogger(MaterialService.class);

    @Autowired
    private MaterialRepository materialRepository;

    @Override
    public Material create(Material material) {
        logger.debug(">>> create");

        materialRepository.create(material);

        logger.debug("<<< create");
        return material;
    }

    @Override
    public List<Material> getMaterialsForUser(Long userId) {
        logger.debug(">>> getMaterialsForUser");
        List<Material> materials = new ArrayList<>();

        materials.addAll(materialRepository.getAllActive());

        logger.debug("<<< getMaterialsForUser");
        return materials;
    }

    @Override
    public Material getActiveById(Long id) {
        logger.debug(">>> getActiveById");
        Material material = null;

        material = materialRepository.getActiveById(id);

        logger.debug("<<< getActiveById");
        return material;
    }

    @Override
    public Material update(Material material) {

        return materialRepository.update(material);
    }

}
