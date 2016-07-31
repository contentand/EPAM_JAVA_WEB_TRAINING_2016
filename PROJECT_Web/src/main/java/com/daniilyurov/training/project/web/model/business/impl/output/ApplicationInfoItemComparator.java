package com.daniilyurov.training.project.web.model.business.impl.output;

import java.util.Comparator;


public class ApplicationInfoItemComparator implements Comparator<ApplicantInfoItem> {
    @Override
    public int compare(ApplicantInfoItem o1, ApplicantInfoItem o2) {
        int value = Double.compare(o2.totalScore, o1.totalScore);
        return value == 0 ? -1 : value;
    }
}
