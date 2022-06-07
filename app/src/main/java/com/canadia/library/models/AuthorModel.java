package com.canadia.library.models;


import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthorModel {
    private long id;
    private String name;
    private long age;
    private String province;

    public AuthorModel(long id,String name, long age, String province) {
        this.name = name;
        this.age = age;
        this.province = province;
        this.id = id;
    }


    @JsonProperty("id")
    public long getID() { return id; }
    @JsonProperty("id")
    public void setID(long value) { this.id = value; }

    @JsonProperty("name")
    public String getName() { return name; }
    @JsonProperty("name")
    public void setName(String value) { this.name = value; }

    @JsonProperty("age")
    public long getAge() { return age; }
    @JsonProperty("age")
    public void setAge(long value) { this.age = value; }

    @JsonProperty("province")
    public String getProvince() { return province; }
    @JsonProperty("province")
    public void setProvince(String value) { this.province = value; }

}

