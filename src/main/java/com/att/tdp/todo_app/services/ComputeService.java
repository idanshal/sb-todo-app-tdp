package com.att.tdp.todo_app.services;

import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ComputeService {

    @PostConstruct
    public void init() {
        log.info("ComputeService initialized");
    }

    @SneakyThrows
    @Async
    public void compute() {
        log.info("computing...");
        Thread.sleep(5000);
        log.info("done!");
    }

    @Scheduled(fixedRate = 10000)
    public void doSomething() {
        log.info("doing something...");
    }
}
