package com.webws.xpf.model;

public class TbProfession {
    private Integer pid;

    private String professionname;

    private String type;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getProfessionname() {
        return professionname;
    }

    public void setProfessionname(String professionname) {
        this.professionname = professionname == null ? null : professionname.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}