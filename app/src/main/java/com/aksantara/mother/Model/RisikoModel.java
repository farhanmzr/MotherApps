package com.aksantara.mother.Model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class RisikoModel {

    String totalSkor, kelompokRisiko, perawat, rujukan, tempat, penolong, userId, pemeriksaanId;

    @ServerTimestamp
    private Date date;

    public RisikoModel() {
    }

    public RisikoModel(String totalSkor, String kelompokRisiko, String perawat, String rujukan, String tempat, String penolong, String userId, String pemeriksaanId, Date date) {
        this.totalSkor = totalSkor;
        this.kelompokRisiko = kelompokRisiko;
        this.perawat = perawat;
        this.rujukan = rujukan;
        this.tempat = tempat;
        this.penolong = penolong;
        this.userId = userId;
        this.pemeriksaanId = pemeriksaanId;
        this.date = date;
    }

    public String getTotalSkor() {
        return totalSkor;
    }

    public void setTotalSkor(String totalSkor) {
        this.totalSkor = totalSkor;
    }

    public String getKelompokRisiko() {
        return kelompokRisiko;
    }

    public void setKelompokRisiko(String kelompokRisiko) {
        this.kelompokRisiko = kelompokRisiko;
    }

    public String getPerawat() {
        return perawat;
    }

    public void setPerawat(String perawat) {
        this.perawat = perawat;
    }

    public String getRujukan() {
        return rujukan;
    }

    public void setRujukan(String rujukan) {
        this.rujukan = rujukan;
    }

    public String getTempat() {
        return tempat;
    }

    public void setTempat(String tempat) {
        this.tempat = tempat;
    }

    public String getPenolong() {
        return penolong;
    }

    public void setPenolong(String penolong) {
        this.penolong = penolong;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPemeriksaanId() {
        return pemeriksaanId;
    }

    public void setPemeriksaanId(String pemeriksaanId) {
        this.pemeriksaanId = pemeriksaanId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
