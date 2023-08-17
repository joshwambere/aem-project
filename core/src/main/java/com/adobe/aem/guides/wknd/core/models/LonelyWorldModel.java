package com.adobe.aem.guides.wknd.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Model(adaptables = Resource.class)
public class LonelyWorldModel {
    @Inject
    @Named("jcr:title")
    @Default(values = "No title")
    private String title;

    @Inject
    @Named("description")
    @Default(values = "No custom description")
    @Optional
    private String customDescription;

    @PostConstruct
    protected void init() {
        title = title.toUpperCase();
        System.out.println("Title: " + title);
        System.out.println("Custom Description: " + customDescription);
        customDescription = customDescription.concat(" - ").concat(title);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCustomDescription() {
        return customDescription;
    }

    public void setCustomDescription(String customDescription) {
        this.customDescription= customDescription;
    }

}