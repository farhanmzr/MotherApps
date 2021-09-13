package com.aksantara.mother.Model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class PemeriksaanModel {

    private String nama, ttl, umur, kehamilanKe, usiaAnakTerakhir, golonganDarah, tglHPHT, usiaKehamilan, taksiranPersalinan, tinggiBadan, bbSebelumHamil, bbSesudahHamil, tekananDarah, ukuranLILA, tinggiRahim,
            gerakJaninSehari, intensitasKontraksi, hemogoblin, tetanus1, tetanus2, urine, feses, swabVagina, pemeriksaanId, userId;

    @ServerTimestamp
    private Date date;

    public PemeriksaanModel() {
    }

    public PemeriksaanModel(String nama, String ttl, String umur, String kehamilanKe, String usiaAnakTerakhir, String golonganDarah, String tglHPHT, String usiaKehamilan, String taksiranPersalinan, String tinggiBadan, String bbSebelumHamil, String bbSesudahHamil, String tekananDarah, String ukuranLILA, String tinggiRahim, String gerakJaninSehari, String intensitasKontraksi, String hemogoblin, String tetanus1, String tetanus2, String urine, String feses, String swabVagina, String pemeriksaanId, String userId, Date date) {
        this.nama = nama;
        this.ttl = ttl;
        this.umur = umur;
        this.kehamilanKe = kehamilanKe;
        this.usiaAnakTerakhir = usiaAnakTerakhir;
        this.golonganDarah = golonganDarah;
        this.tglHPHT = tglHPHT;
        this.usiaKehamilan = usiaKehamilan;
        this.taksiranPersalinan = taksiranPersalinan;
        this.tinggiBadan = tinggiBadan;
        this.bbSebelumHamil = bbSebelumHamil;
        this.bbSesudahHamil = bbSesudahHamil;
        this.tekananDarah = tekananDarah;
        this.ukuranLILA = ukuranLILA;
        this.tinggiRahim = tinggiRahim;
        this.gerakJaninSehari = gerakJaninSehari;
        this.intensitasKontraksi = intensitasKontraksi;
        this.hemogoblin = hemogoblin;
        this.tetanus1 = tetanus1;
        this.tetanus2 = tetanus2;
        this.urine = urine;
        this.feses = feses;
        this.swabVagina = swabVagina;
        this.pemeriksaanId = pemeriksaanId;
        this.userId = userId;
        this.date = date;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    public String getUmur() {
        return umur;
    }

    public void setUmur(String umur) {
        this.umur = umur;
    }

    public String getKehamilanKe() {
        return kehamilanKe;
    }

    public void setKehamilanKe(String kehamilanKe) {
        this.kehamilanKe = kehamilanKe;
    }

    public String getUsiaAnakTerakhir() {
        return usiaAnakTerakhir;
    }

    public void setUsiaAnakTerakhir(String usiaAnakTerakhir) {
        this.usiaAnakTerakhir = usiaAnakTerakhir;
    }

    public String getGolonganDarah() {
        return golonganDarah;
    }

    public void setGolonganDarah(String golonganDarah) {
        this.golonganDarah = golonganDarah;
    }

    public String getTglHPHT() {
        return tglHPHT;
    }

    public void setTglHPHT(String tglHPHT) {
        this.tglHPHT = tglHPHT;
    }

    public String getUsiaKehamilan() {
        return usiaKehamilan;
    }

    public void setUsiaKehamilan(String usiaKehamilan) {
        this.usiaKehamilan = usiaKehamilan;
    }

    public String getTaksiranPersalinan() {
        return taksiranPersalinan;
    }

    public void setTaksiranPersalinan(String taksiranPersalinan) {
        this.taksiranPersalinan = taksiranPersalinan;
    }

    public String getTinggiBadan() {
        return tinggiBadan;
    }

    public void setTinggiBadan(String tinggiBadan) {
        this.tinggiBadan = tinggiBadan;
    }

    public String getBbSebelumHamil() {
        return bbSebelumHamil;
    }

    public void setBbSebelumHamil(String bbSebelumHamil) {
        this.bbSebelumHamil = bbSebelumHamil;
    }

    public String getBbSesudahHamil() {
        return bbSesudahHamil;
    }

    public void setBbSesudahHamil(String bbSesudahHamil) {
        this.bbSesudahHamil = bbSesudahHamil;
    }

    public String getTekananDarah() {
        return tekananDarah;
    }

    public void setTekananDarah(String tekananDarah) {
        this.tekananDarah = tekananDarah;
    }

    public String getUkuranLILA() {
        return ukuranLILA;
    }

    public void setUkuranLILA(String ukuranLILA) {
        this.ukuranLILA = ukuranLILA;
    }

    public String getTinggiRahim() {
        return tinggiRahim;
    }

    public void setTinggiRahim(String tinggiRahim) {
        this.tinggiRahim = tinggiRahim;
    }

    public String getGerakJaninSehari() {
        return gerakJaninSehari;
    }

    public void setGerakJaninSehari(String gerakJaninSehari) {
        this.gerakJaninSehari = gerakJaninSehari;
    }

    public String getIntensitasKontraksi() {
        return intensitasKontraksi;
    }

    public void setIntensitasKontraksi(String intensitasKontraksi) {
        this.intensitasKontraksi = intensitasKontraksi;
    }

    public String getHemogoblin() {
        return hemogoblin;
    }

    public void setHemogoblin(String hemogoblin) {
        this.hemogoblin = hemogoblin;
    }

    public String getTetanus1() {
        return tetanus1;
    }

    public void setTetanus1(String tetanus1) {
        this.tetanus1 = tetanus1;
    }

    public String getTetanus2() {
        return tetanus2;
    }

    public void setTetanus2(String tetanus2) {
        this.tetanus2 = tetanus2;
    }

    public String getUrine() {
        return urine;
    }

    public void setUrine(String urine) {
        this.urine = urine;
    }

    public String getFeses() {
        return feses;
    }

    public void setFeses(String feses) {
        this.feses = feses;
    }

    public String getSwabVagina() {
        return swabVagina;
    }

    public void setSwabVagina(String swabVagina) {
        this.swabVagina = swabVagina;
    }

    public String getPemeriksaanId() {
        return pemeriksaanId;
    }

    public void setPemeriksaanId(String pemeriksaanId) {
        this.pemeriksaanId = pemeriksaanId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
