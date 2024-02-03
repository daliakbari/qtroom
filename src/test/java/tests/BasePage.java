package tests;

import com.adak.ir.LoggingUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

@Listeners(BasePage.TestListener.class)
public class BasePage {
    public static final Logger LOGGER = LoggerFactory.getLogger(BasePage.class);
    public static AppiumDriver driver;


    @BeforeClass
    public void Android_setUp() throws MalformedURLException {
        LOGGER.info("آماده سازی دستگاه");


        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("appium:automationName" , "UIAutomator2");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "11");
        //  capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "5.1.1");
        // capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
       // capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "AQ8MCP2C25400098");
       capabilities.setCapability("udid", "R9WRC0DMSJJ");
      //  capabilities.setCapability(MobileCapabilityType.APP, "/Users/qtroom/Documents/mymci/mymci.apk");
        //capabilities.setCapability(MobileCapabilityType.APP, "http://192.168.2.112:3333/mymci.apk");
        capabilities.setCapability("appPackage", "ir.mci.ecareapp");
        capabilities.setCapability("appActivity", "ir.mci.ecareapp.ui.activity.LauncherActivity");

        capabilities.setCapability("autoGrantPermissions", "true");
        capabilities.setCapability(MobileCapabilityType.NO_RESET, false);

        capabilities.setCapability("autoAcceptAlerts", "true");
        driver = new AndroidDriver(new URL("http://192.168.2.185:8765/mcloud/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

    }

    public static class TestListener implements ITestListener {
        @Override
        public void onTestStart(ITestResult result) {
            LOGGER.info("Test Started: " + result.getName());
        }

        @Override
        public void onTestSuccess(ITestResult result) {
            LOGGER.info("Test Passed: " + result.getName());
        }

        @Override
        public void onTestFailure(ITestResult result) {
            LOGGER.info("Test Failed: " + result.getName());
            captureScreenshot(result.getMethod().getMethodName());
        }

        private void captureScreenshot(String methodName1) {


        }
        }



    @AfterClass
    public void tearDown() {
        if(driver != null) {
            ((AndroidDriver) driver).closeApp();
            driver.quit();
        }
    }
}