package com.provectus.proveng.repository.impl;

import com.provectus.proveng.domain.Material;
import com.provectus.proveng.enumaration.SysStatus;
import com.provectus.proveng.exception.ErrorResponseException;
import com.provectus.proveng.repository.MaterialRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class MaterialRepositoryImpl implements MaterialRepository {

    private static final Logger logger = LogManager.getLogger(MaterialRepository.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Material material) {
        logger.debug(">>> create");

        em.persist(material);

        logger.debug("<<< create");
    }

    @Override
    public List<Material> getAllActive() {
        logger.debug(">>> getAllActive");

        @SuppressWarnings("unchecked")
        List<Material> materials = em.createQuery("from Material where sysStatus = :status")
                .setParameter("status", SysStatus.ON.getNumber()).getResultList();

        logger.debug("<<< getAllActive");
        return materials;
    }

    @Override
    public Material getActiveById(Long id) {
        logger.debug(">>> getActiveById");
        Material material = null;
        try {
            material = (Material) em
                    .createQuery("from Material where sysStatus = :status and id = :id")
                    .setParameter("status", SysStatus.ON.getNumber()).setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new ErrorResponseException("getActiveByIdError",
                    "material with this id doesn't exist", HttpStatus.NOT_FOUND);
        }
        logger.debug("<<< getAllActive");
        return material;
    }

    @Override
    public Material update(Material material) {

        em.merge(material);
        return material;
    }

}
