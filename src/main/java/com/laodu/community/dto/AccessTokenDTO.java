package com.laodu.community.dto;

public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String rediect_url;
    private String state;

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRediect_url() {
        return rediect_url;
    }

    public void setRediect_url(String rediect_url) {
        this.rediect_url = rediect_url;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
