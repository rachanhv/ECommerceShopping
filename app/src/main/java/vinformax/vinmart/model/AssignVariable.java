package vinformax.vinmart.model;

/**
 * Created by Rachan on 6/29/2016.
 */
public class AssignVariable {

    public String productModelName;
    public String productModelActualPrice;
    public String productModelDiscountPrice;
    public String proudctModelDiscountPercentage;
    public String productImage;
    public String id;
    public String thumbnail;
    public String description;

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductModelName() {
        return productModelName;
    }

    public void setProductModelName(String productModelName) {
        this.productModelName = productModelName;
    }

    public String getProductModelActualPrice() {
        return productModelActualPrice;
    }

    public void setProductModelActualPrice(String productModelActualPrice) {
        this.productModelActualPrice = productModelActualPrice;
    }

    public String getProductModelDiscountPrice() {
        return productModelDiscountPrice;
    }

    public void setProductModelDiscountPrice(String productModelDiscountPrice) {
        this.productModelDiscountPrice = productModelDiscountPrice;
    }

    public String getProudctModelDiscountPercentage() {
        return proudctModelDiscountPercentage;
    }

    public void setProudctModelDiscountPercentage(String proudctModelDiscountPercentage) {
        this.proudctModelDiscountPercentage = proudctModelDiscountPercentage;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

}
