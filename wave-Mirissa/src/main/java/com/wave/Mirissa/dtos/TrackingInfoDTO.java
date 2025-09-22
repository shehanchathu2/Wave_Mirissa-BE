package com.wave.Mirissa.dtos;

import java.util.Date;

public class TrackingInfoDTO {
    private String trackingNumber;
    private Date estimateDate;

    public TrackingInfoDTO(String trackingNumber, Date estimateDate) {
        this.trackingNumber = trackingNumber;
        this.estimateDate = estimateDate;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public Date getEstimateDate() {
        return estimateDate;
    }
}