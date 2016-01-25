package com.zaijiadd.app.applyflow.entity;

import java.util.Date;

public class SerialNumber {
    private Long serialNumber;

    private Date createdDate;

    public Long getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Long serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}