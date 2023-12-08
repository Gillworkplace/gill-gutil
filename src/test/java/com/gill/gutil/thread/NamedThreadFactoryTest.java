package com.gill.gutil.thread;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * NamedThreadFactoryTest
 *
 * @author gill
 * @version 2023/12/08
 **/
public class NamedThreadFactoryTest {

    @Test
    public void testNewThreadWithUniqueThreadName() {
        NamedThreadFactory threadFactory = new NamedThreadFactory("prefix", true);
        Thread thread = threadFactory.newThread(() -> System.out.println("Test"));
        Assertions.assertEquals("prefix", thread.getName());
    }

    @Test
    public void testNewThreadWithoutUniqueThreadName() {
        NamedThreadFactory threadFactory = new NamedThreadFactory("prefix", false);
        Thread thread = threadFactory.newThread(() -> System.out.println("Test"));
        Thread secondThread = threadFactory.newThread(() -> System.out.println("Test"));
        Assertions.assertNotEquals("prefix", thread.getName());
        Assertions.assertNotEquals("prefix", secondThread.getName());
    }

    @Test
    public void testNewThreadWithIncrementingThreadNumber() {
        NamedThreadFactory threadFactory = new NamedThreadFactory("prefix", false);
        Thread thread1 = threadFactory.newThread(() -> System.out.println("Test1"));
        Thread thread2 = threadFactory.newThread(() -> System.out.println("Test2"));
        Assertions.assertNotEquals("prefix", thread1.getName());
        Assertions.assertNotEquals("prefix", thread2.getName());
        Assertions.assertEquals("prefix-1", thread1.getName());
        Assertions.assertNotEquals("prefix-1", thread2.getName());
        Assertions.assertEquals("prefix-2", thread2.getName());
    }
}
