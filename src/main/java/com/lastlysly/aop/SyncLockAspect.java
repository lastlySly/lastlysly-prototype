package com.lastlysly.aop;

import com.lastlysly.annotations.SyncLockMethod;
import com.lastlysly.handler.exception.MyCustomException;
import com.lastlysly.uitls.RedisLockUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2021-04-08 10:01
 **/
@Aspect
@Component
public class SyncLockAspect {


    @Around(value = "@annotation(syncLockMethod)")
    public void SyncLockAround(ProceedingJoinPoint joinPoint, SyncLockMethod syncLockMethod) throws Throwable {
        String lockKey = UUID.randomUUID().toString().replace("-","");
        String lockValue = null;
        Object[] args = joinPoint.getArgs();
        int[] argsIndex = syncLockMethod.argsIndex();
        /**
         * 如果没有设置 锁值，则根据业务取默认值，或 放弃加锁，这边放弃加锁
         */
        if (argsIndex.length == 0) {
            joinPoint.proceed();
            return;
        }
        /**
         * 如果 指定的 锁值 参数索引 溢出参数数组边界， 则根据业务取默认值，或 放弃加锁，这边放弃加锁
         */
        if (argsIndex[0] >= args.length) {
            joinPoint.proceed();
            return;
        }
        lockValue = args[argsIndex[0]].toString();

        try {
            boolean isLock = RedisLockUtils.lock(lockValue,lockKey,syncLockMethod.timeOut());
            if (isLock){
                joinPoint.proceed();
                return;
            } else {
                throw new MyCustomException("访问请求限制");
            }
        } finally {
            RedisLockUtils.releaseLock(lockValue,lockKey);
        }
//        switch (syncLockMethod.lockType()) {
//            case REDIS_LOCK:
//                break;
//            default:
//                break;
//        }
    }
}
