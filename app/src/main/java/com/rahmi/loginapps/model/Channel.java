package com.rahmi.loginapps.model;

public class Channel {

    private int nomor;
    private String nama;
    private String url;
    private boolean status;

    public Channel(){
    }

    public Channel(int nomor, String nama, String url,Boolean status){
        this.nomor = nomor;
        this.nama = nama;
        this.url = url;
        this.status = status;
    }

    public int getNomor() {
        return nomor;
    }

    public void setNomor(int nomor) {
        this.nomor = nomor;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "channel{" +
                "nomor=" + nomor +
                ", nama=" + nama + '\'' +
                ", url=" + url + '\'' +
                ", status=" + status +
                '}';
    }

}
