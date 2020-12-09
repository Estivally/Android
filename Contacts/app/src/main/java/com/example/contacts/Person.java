package com.example.contacts;
/*联系人构造类*/
public class Person {
    private int id;
    private String name;
    private String tel;
    private String comp;
    private String email;
/*名字的get方法*/
    public String getName() {
        return name;
    }
    /*名字的set方法*/
    public void setName(String name) {
        this.name = name;
    }
    /*电话的get方法*/
    public String getTel() {
        return tel;
    }
    /*电话的set方法*/
    public void setTel(String tel) {
        this.tel = tel;
    }
    /*公司的get方法*/
    public String getComp() {
        return  comp;
    }
    /*公司的set方法*/
    public void setComp(String comp) {
        this.comp = comp;
    }
    /*id的get方法*/
    public int getId() {
        return id;
    }
    /*id的set方法*/
    public void setId(int id) {
        this.id = id;
    }
    /*电邮的get方法*/
    public String getEmail() {
        return email;
    }
    /*电邮的set方法*/
    public void setEmail(String email) {
        this.email = email;
    }
}
