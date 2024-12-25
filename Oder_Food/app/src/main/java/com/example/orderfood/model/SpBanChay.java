package com.example.orderfood.model;

import java.io.Serializable;

public class SpBanChay  implements Serializable {
    int MAMONAN;
    String TENMONAN;
    String GIA;
    String HINHANH;
    String LOAI;
    String MOTA;

    public int getMAMONAN() {
        return MAMONAN;
    }

    public void setMAMONAN(int MAMONAN) {
        this.MAMONAN = MAMONAN;
    }

    public String getTENMONAN() {
        return TENMONAN;
    }

    public void setTENMONAN(String TENMONAN) {
        this.TENMONAN = TENMONAN;
    }

    public String getGIA() {
        return GIA;
    }

    public void setGIA(String GIA) {
        this.GIA = GIA;
    }

    public String getHINHANH() {
        return HINHANH;
    }

    public void setHINHANH(String HINHANH) {
        this.HINHANH = HINHANH;
    }

    public String getLOAI() {
        return LOAI;
    }

    public void setLOAI(String LOAI) {
        this.LOAI = LOAI;
    }

    public String getMOTA() {
        return MOTA;
    }

    public void setMOTA(String MOTA) {
        this.MOTA = MOTA;
    }
}
