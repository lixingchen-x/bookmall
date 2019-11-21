package com.lxc.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDGenerator {

    public String generate() {

        return UUID.randomUUID().toString().replace("-", "");
    }
}
