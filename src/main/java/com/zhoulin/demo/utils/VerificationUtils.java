package com.zhoulin.demo.utils;


import org.springframework.stereotype.Component;

/**
 * 删除/更新/判断操作使用
 */
@Component
public class VerificationUtils {

    public Integer verification(Integer status){
        if ( status == 1 ){
            status = 1;
        }else if( status == 0 ){
            status = 0;
        }else {
            status = -1;
        }
        return status;
    }
}
