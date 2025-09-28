package com.wave.Mirissa.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "producttype"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Ring.class, name = "ring"),
        @JsonSubTypes.Type(value = Necklace.class, name = "neckless"),
        @JsonSubTypes.Type(value = WristBand.class, name = "wristband"),
        @JsonSubTypes.Type(value = Bracelet.class, name = "bracelet"),
        @JsonSubTypes.Type(value = Earring.class, name = "earring"),
        @JsonSubTypes.Type(value = Anklet.class, name = "anklet"),

})
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long product_id;

    @Column(unique = true, nullable = false, updatable = false)
    private UUID uuid = UUID.randomUUID(); // exposed in URL

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    private String name;
    private String material;
    private BigDecimal price;
    private String category;
    private boolean available;
    private String description;
    private String customization;
    private String gender;

    @JsonProperty("image_url1")
    private String imageUrl1;

    @JsonProperty("image_url2")
    private String imageUrl2;

    @JsonProperty("image_url3")
    private String imageUrl3;

    // Add this field inside your Products class
    @JsonProperty("model_url")
    @Column(name = "model_url")
    private String modelUrl;

    // Getter and Setter
    public String getModelUrl() {
        return modelUrl;
    }

    public void setModelUrl(String modelUrl) {
        this.modelUrl = modelUrl;
    }




    @ManyToMany
    @JoinTable(
            name = "product_customization",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Customization> customizations;

    @JsonProperty("producttype")
    @Column(name = "producttype")
    private String typeForDb;

    public String getTypeForDb() {
        return typeForDb;
    }

    public void setTypeForDb(String typeForDb) {
        this.typeForDb = typeForDb;
    }



    @Column(name = "face_shape_tags")
    private String faceShapeTags;

    @Column(name = "skin_tone_tags")
    private String skinToneTags;


    @Column(name = "personalize")
    private String personalize;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Review> reviews;

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

// Getters and Setters


    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCustomization() {
        return customization;
    }

    public void setCustomization(String customization) {
        this.customization = customization;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImageUrl1() {
        return imageUrl1;
    }

    public void setImageUrl1(String imageUrl1) {
        this.imageUrl1 = imageUrl1;
    }

    public String getImageUrl2() {
        return imageUrl2;
    }

    public void setImageUrl2(String imageUrl2) {
        this.imageUrl2 = imageUrl2;
    }

    public String getImageUrl3() {
        return imageUrl3;
    }

    public void setImageUrl3(String imageUrl3) {
        this.imageUrl3 = imageUrl3;
    }



    public List<Customization> getCustomizations() {
        return customizations;
    }

    public void setCustomizations(List<Customization> customizations) {
        this.customizations = customizations;
    }

    public String getFaceShapeTags() {
        return faceShapeTags;
    }

    public void setFaceShapeTags(String faceShapeTags) {
        this.faceShapeTags = faceShapeTags;
    }

    public String getSkinToneTags() {
        return skinToneTags;
    }

    public void setSkinToneTags(String skinToneTags) {
        this.skinToneTags = skinToneTags;
    }

    public String getPersonalize() {
        return personalize;
    }

    public void setPersonalize(String personalize) {
        this.personalize = personalize;
    }
}