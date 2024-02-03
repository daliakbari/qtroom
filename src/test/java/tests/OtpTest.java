package tests;

import com.epam.reportportal.annotations.Step;
import com.epam.reportportal.testng.ReportPortalTestNGListener;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;
@Listeners({ReportPortalTestNGListener.class})
public class OtpTest extends tests.BasePage {
    public static final Logger LOGGER = LoggerFactory.getLogger(OtpTest.class);


    @Test
    void TB7() throws IOException, InterruptedException {
        navigateToSplashPage();
        OTP();
        tutorial();
        Home();
  /*      Scroll();
        servicess();
        mcaTest2();*/
    }
    @Step
    public void navigateToSplashPage() {
        driver.findElement(By.id("next_fragment_tv_intro_activity")).click();
        LOGGER.trace("driver.manage().logs().get(driver)");
        driver.findElement(By.id("next_fragment_tv_intro_activity")).click();
        driver.findElement(By.id("next_fragment_tv_intro_activity")).click();
        driver.findElement(By.id("next_fragment_tv_intro_activity")).click();
        driver.findElement(By.id("finish_tv_intro_activity")).click();
        LOGGER.info(" صفحه معرفی");
    }
    @Step
    public void OTP() throws IOException, InterruptedException {
        {
            String baseUrl = "http://91.92.209.41:8091/api/v1/%s";
            String assignBodyTemplate = "{\"operator\": \"%s\", \"simType\": \"%s\", \"appName\": \"%s\"}";
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(String.format(baseUrl, "assign")))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(String.format(assignBodyTemplate, "MCI", "CREDIT", "MY_MCI")))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response.body());
             System.out.println(jsonNode);
            driver.findElement(By.id("phone_number_edt")).sendKeys(jsonNode.get("number").asText());
            driver.findElement(By.id("login_btn_login_fragment")).click();
            request = HttpRequest.newBuilder()
                    .uri(URI.create(String.format(baseUrl, "watch?token=" + jsonNode.get("token").asText())))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            objectMapper = new ObjectMapper();
            jsonNode = objectMapper.readTree(response.body());
            String code = jsonNode.get("code").asText();
            driver.findElement(By.id("ir.mci.ecareapp:id/verify_et_activation_code_activity")).sendKeys(code);
            LOGGER.info("لاگین");
        }

    }
    @Step
    public void tutorial() {
        Screenshot.takeScreenshotIfSnackbarVisible(driver, "tutorial");
        driver.findElement(By.id("ir.mci.ecareapp:id/deny_btn_soft_bio_metric")).click();
        driver.findElement(By.id("ir.mci.ecareapp:id/tv_skip")).click();
        driver.findElement(By.id("ir.mci.ecareapp:id/pro_mode_hint_tv_simple_home_activity")).click();
        LOGGER.info("صفحه آموزش");
    }
    @Step
    public void Home() {
        Screenshot.takeScreenshotIfSnackbarVisible(driver, "Home");
        LOGGER.warn("صحت صنجی صفحه اصلی");
        String test1 = driver.findElement(By.id("ir.mci.ecareapp:id/loyaltyScoreTvHomeFragment")).getText();
        String numbers = test1.replaceAll("[^0-9]", "");
        if (Integer.parseInt(numbers) > 0) {
            LOGGER.error("Failed to save");
            LOGGER.info("The score home is: " + numbers);
        }



    }

}






