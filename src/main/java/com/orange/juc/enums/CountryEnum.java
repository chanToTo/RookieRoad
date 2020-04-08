package com.orange.juc.enums;

import lombok.Getter;


public enum CountryEnum {
    ONE(1, "齐"),
    TWO(2, "楚"),
    THREE(3, "燕"),
    FOUR(4, "赵"),
    FIVE(5, "魏"),
    SIX(6, "韩");

    CountryEnum(Integer retCode, String retMessage){
        this.retCode = retCode;
        this.retMessage = retMessage;
    }
    @Getter private Integer retCode;
    @Getter private String retMessage;

    public static  CountryEnum forearch(int index) {
        CountryEnum[] myArray =  CountryEnum.values();
        for (CountryEnum countryEnum: myArray) {
            if (index == countryEnum.getRetCode()){
                return countryEnum;
            }
        }
        return null;
    }
}
