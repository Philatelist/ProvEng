package com.provectus.proveng.repository;

import com.provectus.proveng.domain.Location;

import java.util.List;

public interface LocationRepository {

    void create(Location location);

    void delete(Location location);

    List getAll();

    Location getByName(String name);

    Location getById(long id);

    void update(Location location);
}
