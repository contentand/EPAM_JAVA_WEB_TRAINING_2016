package com.daniilyurov.training.patterns.observer.postoffice.test;

import com.daniilyurov.training.patterns.observer.postoffice.*;
import com.daniilyurov.training.patterns.observer.postoffice.issues.KyivPostIssue;
import com.daniilyurov.training.patterns.observer.postoffice.issues.SegodnyaIssue;
import com.daniilyurov.training.patterns.observer.postoffice.publications.KyivPostPublication;
import com.daniilyurov.training.patterns.observer.postoffice.publications.Publication;
import com.daniilyurov.training.patterns.observer.postoffice.publications.SegodnyaPublication;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import static org.junit.Assert.*;

public class PostOfficeIntegrationTest {

    @Rule
    public SystemOutRule outRule = new SystemOutRule().enableLog().mute();

    @Test
    public void test() {
        PostOffice office = new KyivPostOffice();
        Publication kyivPost = new KyivPostPublication(office);
        Publication segodnya = new SegodnyaPublication(office);

        Subscriber subscriber1 = new KyivSubscriber("Vasiliy Petrovich");
        Subscriber subscriber2 = new KyivSubscriber("Maria Fiodorovna");

        office.registerSubscriber(subscriber1, "segodnya");
        office.registerSubscriber(subscriber1, "kyivPost");
        office.registerSubscriber(subscriber2, "kyivPost");

        kyivPost.publish(new KyivPostIssue("234"));
        assertEquals("Vasiliy Petrovich received KyivPost Issue 234\n" +
                "Maria Fiodorovna received KyivPost Issue 234\n", outRule.getLogWithNormalizedLineSeparator());
        outRule.clearLog();

        segodnya.publish(new SegodnyaIssue("1230"));
        assertEquals("Vasiliy Petrovich received Segodnya Issue 1230\n", outRule.getLogWithNormalizedLineSeparator());
        outRule.clearLog();

        office.removeSubscriber(subscriber1, "kyivPost");

        kyivPost.publish(new KyivPostIssue("235"));
        assertEquals("Maria Fiodorovna received KyivPost Issue 235\n", outRule.getLogWithNormalizedLineSeparator());
        outRule.clearLog();

        segodnya.publish(new SegodnyaIssue("1231"));
        assertEquals("Vasiliy Petrovich received Segodnya Issue 1231\n", outRule.getLogWithNormalizedLineSeparator());
        outRule.clearLog();

    }
}
