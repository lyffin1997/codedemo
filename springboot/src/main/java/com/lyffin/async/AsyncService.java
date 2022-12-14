package com.lyffin.async;

//@Service
public class AsyncService {

    //@Async
    public void asyncTask() throws InterruptedException {
        Thread.sleep(3000);
        System.out.println("异步测试");
    }
}
