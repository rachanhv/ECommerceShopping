package vinformax.vinmart.model;

/**
 * Created by Mobtech-04 on 7/8/2016.
 */
public class ProductCart {

    private String cartProductId;
    private String cartProductName;
    private String cartQuantity;
    private String cartActualPrice;
    private String cartDiscountPrice;
    private String cartDiscountPercentage;
    private String cartTotal;
    private String cartImage;
    private String cartOrderStatus;


    public String getCartProductId() {
        return cartProductId;
    }

    public void setCartProductId(String cartProductId) {
        this.cartProductId = cartProductId;
    }

    public String getCartActualPrice() {
        return cartActualPrice;
    }

    public void setCartActualPrice(String cartActualPrice) {
        this.cartActualPrice = cartActualPrice;
    }

    public String getCartDiscountPercentage() {
        return cartDiscountPercentage;
    }

    public void setCartDiscountPercentage(String cartDiscountPercentage) {
        this.cartDiscountPercentage = cartDiscountPercentage;
    }

    public String getCartDiscountPrice() {
        return cartDiscountPrice;
    }

    public void setCartDiscountPrice(String cartDiscountPrice) {
        this.cartDiscountPrice = cartDiscountPrice;
    }

    public String getCartImage() {
        return cartImage;
    }

    public void setCartImage(String cartImage) {
        this.cartImage = cartImage;
    }

    public String getCartOrderStatus() {
        return cartOrderStatus;
    }

    public void setCartOrderStatus(String cartOrderStatus) {
        this.cartOrderStatus = cartOrderStatus;
    }

    public String getCartProductName() {
        return cartProductName;
    }

    public void setCartProductName(String cartProductName) {
        this.cartProductName = cartProductName;
    }

    public String getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(String cartQuantity) {
        this.cartQuantity = cartQuantity;
    }

    public String getCartTotal() {
        return cartTotal;
    }

    public void setCartTotal(String cartTotal) {
        this.cartTotal = cartTotal;
    }


}
