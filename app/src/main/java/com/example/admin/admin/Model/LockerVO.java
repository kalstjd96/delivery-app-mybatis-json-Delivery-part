package com.example.admin.admin.Model;

import java.io.Serializable;

public class LockerVO implements Serializable {

    private String sno;
    private String detail_no;
    private String nfcid;
    private String product_chk;
    private String locker_chk;

    private String lno;
    private String l_name;
    private String l_addr1;
    private String l_addr2;
    private String l_addr3;

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getDetail_no() {
        return detail_no;
    }

    public void setDetail_no(String detail_no) {
        this.detail_no = detail_no;
    }

    public String getNfcid() {
        return nfcid;
    }

    public void setNfcid(String nfcid) {
        this.nfcid = nfcid;
    }

    public String getProduct_chk() {
        return product_chk;
    }

    public void setProduct_chk(String product_chk) {
        this.product_chk = product_chk;
    }

    public String getLocker_chk() {
        return locker_chk;
    }

    public void setLocker_chk(String locker_chk) {
        this.locker_chk = locker_chk;
    }

    public String getLno() {
        return lno;
    }

    public void setLno(String lno) {
        this.lno = lno;
    }

    public String getL_name() {
        return l_name;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
    }

    public String getL_addr1() {
        return l_addr1;
    }

    public void setL_addr1(String l_addr1) {
        this.l_addr1 = l_addr1;
    }

    public String getL_addr2() {
        return l_addr2;
    }

    public void setL_addr2(String l_addr2) {
        this.l_addr2 = l_addr2;
    }

    public String getL_addr3() {
        return l_addr3;
    }

    public void setL_addr3(String l_addr3) {
        this.l_addr3 = l_addr3;
    }
}
