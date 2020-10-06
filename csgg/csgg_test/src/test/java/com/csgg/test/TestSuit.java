package com.csgg.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@SuiteClasses(value = {TestServerTest.class, TestServerTest2.class})
@RunWith(Suite.class)
public class TestSuit {

}
