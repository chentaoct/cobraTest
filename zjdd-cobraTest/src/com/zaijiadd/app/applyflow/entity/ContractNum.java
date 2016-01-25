package com.zaijiadd.app.applyflow.entity;

import java.util.Date;

public class ContractNum {
    private Integer contractNumId;

    private Date createdDate;

    private Integer contractNumSum;

    private Date contractNumTime;

    public Integer getContractNumId() {
        return contractNumId;
    }

    public void setContractNumId(Integer contractNumId) {
        this.contractNumId = contractNumId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getContractNumSum() {
        return contractNumSum;
    }

    public void setContractNumSum(Integer contractNumSum) {
        this.contractNumSum = contractNumSum;
    }

    public Date getContractNumTime() {
        return contractNumTime;
    }

    public void setContractNumTime(Date contractNumTime) {
        this.contractNumTime = contractNumTime;
    }
}