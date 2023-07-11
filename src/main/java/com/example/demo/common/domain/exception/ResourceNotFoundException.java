package com.example.demo.common.domain.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String datasource, long id) {
        super(datasource + "에서 ID " + id + "를 찾을 수 없습니다.");
    }
}