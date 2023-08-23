package com.adobe.aem.guides.wknd.core.models;

        import org.apache.sling.api.resource.Resource;
        import org.apache.sling.models.annotations.Default;
        import org.apache.sling.models.annotations.Model;
        import org.apache.sling.models.annotations.Optional;
        import javax.inject.Inject;
        import javax.inject.Named;

@Model(adaptables = Resource.class)
public class LonelySliderModel {
    @Inject
    @Named("jcr:imageUrl")
    @Default(values = "https://placehold.co/600x400/000000/FFFFFF/png")
    private String imageUrl;

    @Inject
    @Named("description")
    @Default(values = "Some representative placeholder content for the third slide. edited.....")
    @Optional
    private String customDescription;


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCustomDescription() {
        return customDescription;
    }

    public void setCustomDescription(String customDescription) {
        this.customDescription= customDescription;
    }

}