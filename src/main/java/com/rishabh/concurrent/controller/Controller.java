package com.rishabh.concurrent.controller;

import com.rishabh.concurrent.model.ServiceResponse;
import com.rishabh.concurrent.service.core.GetResultOfAllTask;
import com.rishabh.concurrent.utils.ExecutorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/v1/")
public class Controller {

    @Autowired
    private GetResultOfAllTask getResultOfAllTask;

    @GetMapping("/execute")
    public CompletableFuture<ServiceResponse> Execute() {
        return CompletableFuture.supplyAsync(() ->
                getResultOfAllTask.runAllTask()
        ,ExecutorUtil.getExecutor());

        /*executorUtil.getExecutor().submit(() -> {
               processLog.process(logModel, logstashBaseUrl);
             });*/
        //return getResultOfAllTask.runAllTask();
    }
}
