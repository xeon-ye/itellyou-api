package com.itellyou.model.upload;

import com.itellyou.util.CacheEntity;
import com.itellyou.util.BaseEnum;

public enum UploadSource implements BaseEnum<UploadSource,Integer> , CacheEntity {
    DEFAULT(0,"default"),
    AVATAR(1,"avatar"),
    ARTICLE(2,"article"),
    QUESTION(3,"question"),
    ANSWER(4,"answer"),
    SOFTWARE(5,"software");

    private Integer value;
    private String name;
    UploadSource(Integer value,String name){
        this.value = value;
        this.name = name;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    public String getName(){
        return this.name;
    }

    public String toString(){
        return getName();
    }

    public static UploadSource valueOf(Integer value){
        switch (value){
            case 1:
                return AVATAR;
            case 2:
                return ARTICLE;
            case 3:
                return QUESTION;
            case 4:
                return ANSWER;
            case 5:
                return SOFTWARE;
            default:
                return DEFAULT;
        }
    }

    @Override
    public String cacheKey() {
        return name;
    }
}
