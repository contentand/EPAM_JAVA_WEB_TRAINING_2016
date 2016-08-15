package com.daniilyurov.training.project.web.model.dao.api.entity;

import com.daniilyurov.training.project.web.i18n.NameLocalizable;

import java.util.Optional;

/**
 * Subject contains names of the subject.
 */
public class Subject extends Entity implements NameLocalizable {

    private String enName;
    private String ruName;
    private String deName;

    @Override
    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    @Override
    public String getRuName() {
        return ruName;
    }

    public void setRuName(String ruName) {
        this.ruName = ruName;
    }

    @Override
    public String getDeName() {
        return deName;
    }

    public void setDeName(String deName) {
        this.deName = deName;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Subject)) return false;
        Subject instance = (Subject) obj;
        return instance.getId().equals(this.id);
    }

    @Override
    public int hashCode() {
        return Optional.ofNullable(id).orElse(0L).intValue();
    }
}
