package com.mindiqo.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private Integer code; // 1: success, 0: error

    private String msg; // error message or success
    private Object data; // data

    public static Result success(){
        return new Result(1, "success", null);
    }
    public static Result success(Object data){
        return new Result(1, "success", data);
    }
    public static Result error(String msg){
        return new Result(0, msg, null);
    }

}
