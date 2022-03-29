package com.devprotocols.oracledatabase.models;

public class MyModel {
    String articleCode;
    String barcode;
    String descriptionEng;
    String descriptionAr;
    String price;

    public MyModel(String articleCode, String barcode, String descriptionEng, String descriptionAr, String price) {
        this.articleCode = articleCode;
        this.barcode = barcode;
        this.descriptionEng = descriptionEng;
        this.descriptionAr = descriptionAr;
        this.price = price;
    }

    public String getArticleCode() {
        return articleCode;
    }

    public void setArticleCode(String articleCode) {
        this.articleCode = articleCode;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getDescriptionEng() {
        return descriptionEng;
    }

    public void setDescriptionEng(String descriptionEng) {
        this.descriptionEng = descriptionEng;
    }

    public String getDescriptionAr() {
        return descriptionAr;
    }

    public void setDescriptionAr(String descriptionAr) {
        this.descriptionAr = descriptionAr;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
