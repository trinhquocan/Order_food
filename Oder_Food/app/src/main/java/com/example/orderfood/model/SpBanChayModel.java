package com.example.orderfood.model;

import java.util.List;

public class SpBanChayModel {
    boolean success;
    String message;
    List<SpBanChay> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<SpBanChay> getResult() {
        return result;
    }

    public void setResult(List<SpBanChay> result) {
        this.result = result;
    }
}
