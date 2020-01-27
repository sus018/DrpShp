package com.susmita.drpshp;

public class Products {
    String productId, customerId, brandCode, brandName, productCode, productDesc, mrp, expiry;

    public Products(String productId, String customerId, String brandCode, String brandName, String productCode, String productDesc, String mrp, String expiry) {
        this.productId = productId;
        this.customerId = customerId;
        this.brandCode = brandCode;
        this.brandName = brandName;
        this.productCode = productCode;
        this.productDesc = productDesc;
        this.mrp = mrp;
        this.expiry = expiry;
    }
    public Products(){

    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getBrandCode() {
        return brandCode;
    }

    public void setBrandCode(String brandCode) {
        this.brandCode = brandCode;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }
}
