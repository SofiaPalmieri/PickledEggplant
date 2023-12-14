package com.olenickglobal;

import com.olenickglobal.configuration.SUTAwarePicoFactory;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(objectFactory = SUTAwarePicoFactory.class)
public class TestNGCucumberTests extends AbstractTestNGCucumberTests {
}
