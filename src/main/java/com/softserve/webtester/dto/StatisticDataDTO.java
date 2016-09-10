package com.softserve.webtester.dto;

import java.io.Serializable;
import java.util.List;

/**
 * DTO class with data of the {@link Service} testing results,
 * using for generating statistic reports.
 * 
 * @author Yura Lubinec
 *
 */
public class StatisticDataDTO implements Serializable {

    private static final long serialVersionUID = 569307305008042909L;

    private String serviceName;
    private int sla;
    private List<Integer> responseTimes;
    private int averageResponseTime;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getSla() {
        return sla;
    }

    public void setSla(int sla) {
        this.sla = sla;
    }

    public List<Integer> getResponseTimes() {
        return responseTimes;
    }

    public void setResponseTimes(List<Integer> responseTimes) {
        this.responseTimes = responseTimes;
    }

    public int getAverageResponseTime() {
        return averageResponseTime;
    }

    public void setAverageResponseTime(int averageResponseTime) {
        this.averageResponseTime = averageResponseTime;
    }
}