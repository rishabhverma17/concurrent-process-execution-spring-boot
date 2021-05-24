package com.rishabh.concurrent.service.core;

import com.rishabh.concurrent.model.*;
import com.rishabh.concurrent.service.abtraction.IServiceOne;
import com.rishabh.concurrent.service.abtraction.IServiceThree;
import com.rishabh.concurrent.service.abtraction.IServiceTwo;
import com.rishabh.concurrent.model.ConcurrentRequest;
import com.rishabh.concurrent.utils.ExecutorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GetResultOfAllTask {

    Logger LOGGER = LoggerFactory.getLogger(GetResultOfAllTask.class);

    @Autowired
    IServiceOne serviceOne;
    @Autowired
    IServiceTwo serviceTwo;
    @Autowired
    IServiceThree serviceThree;
    @Autowired
    ExecutorUtil executorUtil;

    public ServiceResponse runAllTask(){

        ServiceResponse serviceResponse = null;

        List<ConcurrentRequest> parallelReq = new ArrayList<>();
        ConcurrentRequest serviceOneConcurrentRequest = new ConcurrentRequest("doTaskOne", serviceOne, "ServiceOne");
        parallelReq.add(serviceOneConcurrentRequest);

        ConcurrentRequest serviceTwoConcurrentRequest = new ConcurrentRequest("doTaskTwo", serviceTwo, "ServiceTwo");
        parallelReq.add(serviceTwoConcurrentRequest);

        ConcurrentRequest serviceThreeConcurrentRequest = new ConcurrentRequest("doTaskThree", serviceThree, "ServiceThree");
        parallelReq.add(serviceThreeConcurrentRequest);


        try {
            Map<ConcurrentRequest, Object> concurrentResponse = executorUtil.executeConcurrently(parallelReq, true);
            serviceResponse = populateResponse(concurrentResponse);
        } catch (ClassCastException e) {
            LOGGER.error("Class cast exception occurred in ConcurrentResponse {}", e);
        } catch (Exception e) {
            LOGGER.error("Exception occurred in ConcurrentResponse {}", e);
        }

        return serviceResponse;
    }

    private ServiceResponse populateResponse(Map<ConcurrentRequest, Object> concurrentResponse) {
        String serviceOneResponse = null;
        String serviceTwoResponse = null;
        String serviceThreeResponse = null;
        if (concurrentResponse != null && !concurrentResponse.isEmpty()) {
            for (Map.Entry<ConcurrentRequest, Object> entry : concurrentResponse.entrySet()) {

                if (entry.getValue() instanceof ServiceOneBO) {
                    serviceOneResponse = ((ServiceOneBO) entry.getValue()).getServiceMessage();
                }
                if (entry.getValue() instanceof ServiceTwoBO) {
                    serviceTwoResponse = ((ServiceTwoBO) entry.getValue()).getServiceMessage();
                }
                if (entry.getValue() instanceof ServiceThreeBO) {
                    serviceThreeResponse = ((ServiceThreeBO) entry.getValue()).getServiceMessage();
                }
            }
        }
        return ServiceResponse.builder()
                .serviceOneResponse(serviceOneResponse)
                .serviceTwoResponse(serviceTwoResponse)
                .serviceThreeResponse(serviceThreeResponse)
                .build();
    }
}
