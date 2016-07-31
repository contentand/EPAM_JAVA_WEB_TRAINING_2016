package com.daniilyurov.training.project.web.model.dao.api.entity;

import java.io.Serializable;

/**
 * Abstract Entity that contains properties
 * shared by all concrete entities.
 *
 * Id property is shared by all entities
 * and thus it is extracted into an abstract class.
 *
 * @author Daniil Yurov
 */

abstract class Entity implements Serializable {

    protected Long id;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
