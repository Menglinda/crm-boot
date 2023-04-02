package com.ldh.crm.pojo;


import com.ldh.crm.resultEnum.IResultCode;
import com.ldh.crm.resultEnum.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.Optional;

@Data
@NoArgsConstructor
@Slf4j
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code;

    private boolean success;

    private T data;

    private String msg;

    private R(IResultCode resultCode) {
        this(resultCode, (T) null, resultCode.getMessage());
    }

    private R(IResultCode resultCode, String msg) {
        this(resultCode, (T) null, msg);
    }

    private R(IResultCode resultCode, T data, String msg) {
        this(resultCode.getCode(), data, msg);
    }

    private R(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.success = ResultCode.SUCCESS.code == code;
    }

    public static boolean isSuccess(@Nullable R<?> result) {
        return (Boolean) Optional.ofNullable(result).map((x) -> {
            return ObjectUtils.nullSafeEquals(ResultCode.SUCCESS.code, x.code);
        }).orElse(Boolean.FALSE);
    }

    public static boolean isNotSuccess(@Nullable R<?> result) {
        return !isSuccess(result);
    }

    public static <T> R<T> data(T data) {
        return data(data, "操作成功");
    }

    public static <T> R<T> data(T data, String msg) {
        return data(ResultCode.SUCCESS.getCode(), data, msg);
    }

    public static <T> R<T> data(int code, T data, String msg) {
        return new R<T>(code, data, data == null ? "暂无承载数据" : msg);
    }

    public static <T> R<T> success() {
        return new R<T>(ResultCode.SUCCESS, ResultCode.SUCCESS.getMessage());
    }

    public static <T> R<T> success(String msg) {
        return new R<T>(ResultCode.SUCCESS, msg);
    }

    public static <T> R<T> success(IResultCode resultCode) {
        return new R<T>(resultCode);
    }

    public static <T> R<T> success(IResultCode resultCode, String msg) {
        return new R<T>(resultCode, msg);
    }

    public static <T> R<T> fail() {
        return new R<T>(ResultCode.FAILURE, ResultCode.FAILURE.getMessage());
    }

    public static <T> R<T> fail(String msg) {
        log.error(msg);
        return new R<T>(ResultCode.FAILURE, msg);
    }

    public static <T> R<T> fail(IResultCode resultCode) {
        return new R<T>(resultCode);
    }

    public static <T> R<T> fail(IResultCode resultCode, String msg) {
        log.error(resultCode.getMessage(), msg);
        return new R<T>(resultCode, msg);
    }

    public static <T> R<T> unAuthorized() {
        return new R<T>(ResultCode.UN_AUTHORIZED, ResultCode.UN_AUTHORIZED.getMessage());
    }

    public static <T> R<T> status(boolean flag) {
        return flag ? success("操作成功") : fail("操作失败");
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(final int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(final boolean success) {
        this.success = success;
    }

    public T getData() {
        return this.data;
    }

    public void setData(final T data) {
        this.data = data;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(final String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "R(code=" + this.getCode() + ", success=" + this.isSuccess() + ", data=" + this.getData() + ", msg="
                + this.getMsg() + ")";
    }

}
