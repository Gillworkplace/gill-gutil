package com.gill.gutil.log;

import static org.mockito.Mockito.mock;

import org.apache.logging.slf4j.Log4jLogger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.slf4j.helpers.NOPLogger;

/**
 * LogTest
 *
 * @author gill
 * @version 2023/12/08
 **/
public class LogTest {

    @Test
    public void testGetLogger_ReturnsSoutLoggerWhenLoggerIsNOPLogger() {
        Class<TestClass> cls = TestClass.class;
        try (MockedStatic<org.slf4j.LoggerFactory> mockedStatic = Mockito.mockStatic(org.slf4j.LoggerFactory.class)) {
            mockedStatic.when(() -> org.slf4j.LoggerFactory.getLogger(cls)).thenReturn(mock(NOPLogger.class));
            ILogger logger = LoggerFactory.getLogger(cls);
            Assertions.assertInstanceOf(SoutLogger.class, logger);
        }
    }

    @Test
    public void testGetLogger_ReturnsSlf4jAdapterLoggerWhenLoggerIsNotNOPLogger() {
        Class<TestClass> cls = TestClass.class;
        try (MockedStatic<org.slf4j.LoggerFactory> mockedStatic = Mockito.mockStatic(org.slf4j.LoggerFactory.class)) {
            mockedStatic.when(() -> org.slf4j.LoggerFactory.getLogger(cls)).thenReturn(mock(Log4jLogger.class));
            ILogger logger = LoggerFactory.getLogger(cls);
            Assertions.assertInstanceOf(Slf4jAdapter.class, logger);
        }
    }

    private static class TestClass {}
}
