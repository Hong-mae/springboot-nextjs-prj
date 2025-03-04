package com.hong_mae.nextjs_prj.global.ReturnData;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReturnData<T> {
    private String resultCode;
    private String msg;
    private T data;

    public static <T> ReturnData<T> of(String rc, String msg, T data) {
        return new ReturnData<>(rc, msg, data);
    }

    public static <T> ReturnData<T> of(String rc, String msg) {
        return of(rc, msg, null);
    }

    @JsonIgnore
    public boolean isSuccess() {
        return resultCode.startsWith("S-");
    }

    @JsonIgnore
    public boolean isFailer() {
        return !isSuccess();
    }
}
