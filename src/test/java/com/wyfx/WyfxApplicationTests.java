package com.wyfx;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WyfxApplicationTests {

    @Test
    public void contextLoads() {
        System.err.println(System.getProperty("java.libaray.path"));
    }

}
