package com.lastlysly.annotations;

import java.lang.annotation.*;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2021-04-08 09:37
 *
 * 同步锁
 **/
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SyncLockMethod {
    /**
     * 锁类型
     * @return
     */
    LockTypeEnum lockType() default LockTypeEnum.REDIS_LOCK;

    /**
     * 毫秒，锁生存时间
     * @return
     */
    int timeOut() default 5000;

    int[] argsIndex() default {};

    enum LockTypeEnum {
//        REENTRANT_LOCK,
        REDIS_LOCK;
    }
}
