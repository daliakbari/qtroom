package tests;

import com.adak.ir.LoggingUtils;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class Screenshot {

    public static void takeScreenshotIfSnackbarVisible(WebDriver driver, String pageName) {
        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        By snackbarTextLocator = By.id("ir.mci.ecareapp:id/snackbar_text");
        boolean isElementPresent = driver.findElements(snackbarTextLocator).size() > 0;

        if (isElementPresent) {
            // ساعت فعلی را دریافت کنید
            LocalDateTime currentTime = LocalDateTime.now();

            // تبدیل ساعت فعلی به فرمت مورد نظر (برای مثال: yyyy-MM-dd_HH-mm-ss)
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
            String formattedTime = currentTime.format(formatter);

            // تولید نام فایل با استفاده از ساعت فعلی و نام صفحه
            String fileName = pageName + "_" + formattedTime + ".png";

            // ایجاد فایل اسکرین‌شات
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            // ذخیره اسکرین‌شات با نام مورد نظر
            try {

                FileUtils.copyFile(screenshotFile, new File("path/to/Sup/" + fileName));
                System.out.println("Screenshot saved successfully.");
                	String screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
				LoggingUtils.logBase64(screenshot, pageName);

            } catch (IOException e) {
                System.out.println("Failed to save screenshot: " + e.getMessage());
            }

            System.out.println("Test failed");
            Assert.fail("Test failed");
        }
    }
}