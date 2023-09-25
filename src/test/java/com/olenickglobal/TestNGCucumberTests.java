package com.olenickglobal;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = {"classpath:features"}, glue = {"classpath:com.olenickglobal.steps"}, plugin = {"com.olenickglobal.CucumberListener"})
public class TestNGCucumberTests extends AbstractTestNGCucumberTests {
}
