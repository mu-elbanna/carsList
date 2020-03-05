package com.eo.cars.model;


public class NetworkState {
    private String status;
    private String payLoad;

    public NetworkState(String status, String payLoad) {
        this.status = status;
        this.payLoad = payLoad;
    }

    public NetworkState(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getPayLoad() {
        return payLoad;
    }
}
