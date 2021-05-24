package com.rishabh.concurrent.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServiceResponse {
    private String serviceOneResponse;
    private String serviceTwoResponse;
    private String serviceThreeResponse;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Service Response [");
        builder.append("Service One Response =").append(serviceOneResponse).append(",");
        builder.append("Service Two Response =").append(serviceTwoResponse).append(",");
        builder.append("Service Two Response =").append(serviceThreeResponse);
        builder.append("]");
        return builder.toString();
    }
}
