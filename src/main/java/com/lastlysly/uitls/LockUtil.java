package com.lastlysly.uitls;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2021-04-08 10:55
 *
 * 可重入锁
 **/
public class LockUtil{
    private volatile static Map<String, MyCustomReentrantLock> lockMap = new ConcurrentHashMap<>(16);

    /**
     * 加锁
     * @param lockValue
     * @param lockKey
     * @return
     */
    public static boolean tryLock(String lockValue,String lockKey) {
        String mapKey = lockValue;
        synchronized (mapKey.intern()) {
            if (lockMap.containsKey(mapKey)) {
                Lock reentrantLock = lockMap.get(mapKey);
                return reentrantLock.tryLock();
            } else {
                MyCustomReentrantLock reentrantLock = new MyCustomReentrantLock();
                if (reentrantLock.tryLock()) {
                    reentrantLock.setLockKey(lockKey);
                    lockMap.put(mapKey,reentrantLock);
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * 解锁
     * @param lockValue
     * @param lockKey
     */
    public static void unLock(String lockValue,String lockKey) {
        if (lockKey == null) {
            return;
        }
        synchronized (lockValue.intern()) {
            if (lockMap.containsKey(lockValue)) {
                MyCustomReentrantLock reentrantLock = lockMap.get(lockValue);
                if (!lockKey.equals(reentrantLock.getLockKey())) {
                    return;
                }
                reentrantLock.unlock();
                lockMap.remove(lockValue);
            }
        }
    }
    /**
     * 自定义重入锁
     */
    static class MyCustomReentrantLock extends ReentrantLock{
        private String lockKey;

        public String getLockKey() {
            return lockKey;
        }

        public void setLockKey(String lockKey) {
            this.lockKey = lockKey;
        }
    }



    public static void main(String[] args) {
        String lockValue = "123";
        String lockKey1 = "223";
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    if (LockUtil.tryLock(lockValue,lockKey1)) {
                        System.out.println(lockValue + "获取到锁" + Thread.currentThread().getName());
                        try {
                            TimeUnit.SECONDS.sleep(2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println(lockValue + "没有得到锁" + Thread.currentThread().getName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(lockValue + "解锁" + Thread.currentThread().getName());
                    LockUtil.unLock(lockValue,lockKey1);
                }

            }
        });
        String lockValue2 = "234";
        String lockKey2 = "323";
        Thread thread1 = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    if (LockUtil.tryLock(lockValue2,lockKey2)) {
                        System.out.println(lockValue2 + "获取到锁" + Thread.currentThread().getName());
                        try {
                            TimeUnit.SECONDS.sleep(2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println(lockValue2 + "没有得到锁" + Thread.currentThread().getName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(lockValue2 + "解锁" + Thread.currentThread().getName());
                    LockUtil.unLock(lockValue2,lockKey2);
                }

            }
        });

        thread.setName("th");
        thread1.setName("th1");
        thread.start();
        thread1.start();
        String lockKey5 = "th3";
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    if (LockUtil.tryLock(lockValue,lockKey5)) {
                        System.out.println(lockValue + "获取到锁" + Thread.currentThread().getName());
                        try {
                            TimeUnit.SECONDS.sleep(2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println(lockValue + "没有得到锁" + Thread.currentThread().getName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(lockValue + "解锁" + Thread.currentThread().getName());
                    LockUtil.unLock(lockValue,lockKey5);
                }

            }
        });
        thread3.setName("th33");
        thread3.start();
    }


}
