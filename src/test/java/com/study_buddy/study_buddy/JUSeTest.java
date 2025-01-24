package com.study_buddy.study_buddy;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class JUSeTest {

    private String driver_path = "C:\\Drivers\\chromedriver-win64\\chromedriver.exe";

    @Test
    public void testGoogleSearch() throws InterruptedException{
        System.setProperty("webdriver.chrome.driver", driver_path);

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com");

        // Optional. If not specified, WebDriver searches the PATH for chromedriver.
        // System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");
        // WebDriver driver = new ChromeDriver();
        driver.get("http://www.google.com/");
        Thread.sleep(5000);  // Let the user actually see something!
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("ChromeDriver");
        searchBox.submit();
        Thread.sleep(5000);  // Let the user actually see something!
        driver.quit();
    }

    @Test
    public void testLoginGoodCreds() {
        System.out.println("Navigating to the login page...");
        WebDriver driver = new ChromeDriver();
        System.setProperty("webdriver.chrome.driver", driver_path);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://localhost:5173/users/login/");

        System.out.println("Write username...");
        WebElement element = driver.findElement(By.name("username"));
        element.sendKeys("student1");

        System.out.println("Write password...");
        element = driver.findElement(By.name("password"));
        element.sendKeys("password123");

        System.out.println("Perform login...");
        driver.findElement(By.cssSelector("button.inputButton")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("home"));
        String redirURL = driver.getCurrentUrl();
        System.out.println("Login successfully! Redirected to URL:"+redirURL);

        // When login is successful user is redirected here: http://localhost:5173/users/home
        boolean compRes= redirURL.contains("home");

        assertEquals(compRes, true);
        System.out.println("Assert successfully!");
        driver.quit();
    }

    @Test
    public void testLoginBadCreds() {
        System.out.println("Navigating to the login page...");
        WebDriver driver = new ChromeDriver();
        System.setProperty("webdriver.chrome.driver", driver_path);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://localhost:5173/users/login/");

        System.out.println("Write username...");
        WebElement element = driver.findElement(By.name("username"));
        element.sendKeys("student1");

        System.out.println("Write (wrong) password...");
        element = driver.findElement(By.name("password"));
        element.sendKeys("password12356789");

        System.out.println("Perform login...");
        driver.findElement(By.cssSelector("button.inputButton")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String redirURL = driver.getCurrentUrl();
        System.out.println("Didn't login! Stayed on URL:"+redirURL);

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("p.errorMessage")));
        String messageText = errorMessage.getText();
        assertEquals(messageText, "Lozinka neispravna");
        System.out.println("Displaying error message to user.");

        // When login is successful user is redirected here: http://localhost:5173/users/home
        boolean compRes= redirURL.contains("home");

        assertEquals(compRes, false);
        System.out.println("Assert successfully!");
        driver.quit();
    }

    @Test
    public void testRegisterGoodCreds() {
        System.out.println("Navigating to the register page...");
        WebDriver driver = new ChromeDriver();
        System.setProperty("webdriver.chrome.driver", driver_path);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://localhost:5173/users/register");

        System.out.println("Write User data...");
        System.out.println("Write firstName...");
        WebElement element = driver.findElement(By.name("firstName"));
        element.sendKeys("Jane");

        System.out.println("Write lastName...");
        element = driver.findElement(By.name("lastName"));
        element.sendKeys("Doe");

        System.out.println("Write username...");
        element = driver.findElement(By.name("username"));
        element.sendKeys("JaneDoe");

        System.out.println("Write email...");
        element = driver.findElement(By.name("email"));
        element.sendKeys("JaneDoe@example.com");

        System.out.println("Write password...");
        element = driver.findElement(By.name("password"));
        element.sendKeys("hahaha12345");

        System.out.println("Write confirmPassword...");
        element = driver.findElement(By.name("confirmPassword"));
        element.sendKeys("hahaha12345");

        System.out.println("Write location...");
        element = driver.findElement(By.name("location"));
        element.sendKeys("Zagreb");

        System.out.println("Write day of birth...");
        element = driver.findElement(By.name("day"));
        Select dayDropdown = new Select(element);
        dayDropdown.selectByValue("15");

        System.out.println("Write month of birth...");
        element = driver.findElement(By.name("month"));
        Select monthDropdown = new Select(element);
        monthDropdown.selectByValue("3");

        System.out.println("Write year of birth...");
        element = driver.findElement(By.name("year"));
        Select yearDropdown = new Select(element);
        yearDropdown.selectByValue("2000");

        System.out.println("Write gender...");
        element = driver.findElement(By.cssSelector("label[for='genderFemale']"));
        element.click();

        System.out.println("Write role...");
        element = driver.findElement(By.cssSelector("label[for='roleStudent']"));
        element.click();

        System.out.println("Perform register...");

        WebElement submitButton = driver.findElement(By.cssSelector(".inputButton"));
        submitButton.click();
        System.out.println("Submit button clicked!");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.urlContains("home"));
        String redirURL = driver.getCurrentUrl();
        System.out.println("Register successfully! Redirected to URL:"+redirURL);

        // When register is successful user is redirected here: http://localhost:5173/users/home
        boolean compRes= redirURL.contains("home");

        assertEquals(compRes, true);
        System.out.println("Assert successfully!");
        driver.quit();
    }

    @Test
    public void testNonExistentPage() {
        WebDriver driver = new ChromeDriver();
        System.setProperty("webdriver.chrome.driver", driver_path);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        System.out.println("Open non-existing URL...");
        driver.get("http://localhost:5173/nonExistingPage");

        System.out.println("Loading the page...");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("error-page")));

        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);

        WebElement errorHeader = driver.findElement(By.cssSelector("h1.er"));
        String headerText = errorHeader.getText();
        System.out.println("Testing page header...");
        assertEquals(headerText,"Ups!");
        System.out.println("Header verified: Ups!");


        WebElement errorMessage = driver.findElement(By.cssSelector("p.errorText"));
        String messageText = errorMessage.getText();
        System.out.println("Testing error message...");
        assertEquals(messageText,"Oprostite, dogodila se neočekivana pogreška.");
        System.out.println("Error message verified: Oprostite, dogodila se neočekivana pogreška.");

        WebElement errorDetails = driver.findElement(By.cssSelector("p.errorText i"));
        String errorDetailsText = errorDetails.getText();
        System.out.println("Testing error details...");
        assertEquals(errorDetailsText,"Not Found");
        System.out.println("Error details vertified: Not Found");

        driver.quit();
    }




    @Test
    public void testJoinAndLeaveStudyGroup() throws InterruptedException{
        System.out.println("Login user...");
        WebDriver driver = new ChromeDriver();
        System.setProperty("webdriver.chrome.driver", driver_path);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://localhost:5173/users/login/");

        WebElement element = driver.findElement(By.name("username"));
        element.sendKeys("SeleniumTest_AddToGroup");

        element = driver.findElement(By.name("password"));
        element.sendKeys("12345678i");

        driver.findElement(By.cssSelector("button.inputButton")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("home"));
        String redirURL = driver.getCurrentUrl();
        System.out.println("Login successfully! Redirected to URL:"+redirURL);

        System.out.println("Check the info of the first group on homepage...");
        WebElement firstButton = driver.findElement(By.cssSelector("div.joinGroup > button"));
        firstButton.click();
        System.out.println("Clicked on \"Pogledaj Info\"");

        WebElement labelElement = driver.findElement(By.xpath("//div[@class='infoGroup']//label[contains(text(), 'Broj prijavljenih')]"));
        String labelText = labelElement.getText();
        String number = labelText.split(":")[1].trim().split("/")[0];
        System.out.println("The number of current members is: " + number);

        System.out.println("Join into the first group on homepage...");
        WebElement joinButton = driver.findElement(By.cssSelector("div.joinGroupButton > button"));
        joinButton.click();
        System.out.println("Clicked on \"Pridružite se!\"");

        Thread.sleep(3000);
        WebElement joinButtonChanged = driver.findElement(By.cssSelector("div.joinGroupButton > button"));
        String buttonText = joinButtonChanged.getText();

        // When someone is enters the group, button changes tex from "Pridruži se!" to "Napusti grupu!"
        assertEquals(buttonText, "Napusti grupu!");
        labelElement = driver.findElement(By.xpath("//div[@class='infoGroup']//label[contains(text(), 'Broj prijavljenih')]"));
        labelText = labelElement.getText();
        String number2 = labelText.split(":")[1].trim().split("/")[0];
        assertEquals(Integer.parseInt(number)+1,Integer.parseInt(number2));
        System.out.println("The number of current members is: " + number2);
        System.out.println("Successfully added into group!");

        // When someone is leaves the group, button changes tex from "Napusti grupu!" to "Pridruži se!"
        Thread.sleep(10000);
        WebElement joinButtonAgain = driver.findElement(By.cssSelector("div.joinGroupButton > button"));
        joinButtonAgain.click();
        System.out.println("Clicked on \"Napusti grupu!\"");

        Thread.sleep(3000);
        joinButtonChanged = driver.findElement(By.cssSelector("div.joinGroupButton > button"));
        buttonText = joinButtonChanged.getText();

        assertEquals(buttonText, "Pridružite se!");
        labelElement = driver.findElement(By.xpath("//div[@class='infoGroup']//label[contains(text(), 'Broj prijavljenih')]"));
        labelText = labelElement.getText();
        String number3 = labelText.split(":")[1].trim().split("/")[0];
        System.out.println("The number of current members is: " + number3);
        assertEquals(Integer.parseInt(number2)-1,Integer.parseInt(number3));
        System.out.println("Successfully left the group!");

        driver.quit();
    }

    @Test
    public void testChangeProfileInfo() throws InterruptedException{
        System.out.println("Login user...");
        WebDriver driver = new ChromeDriver();
        System.setProperty("webdriver.chrome.driver", driver_path);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://localhost:5173/users/login/");

        WebElement element = driver.findElement(By.name("username"));
        element.sendKeys("SeleniumTest_ProfileChange");

        element = driver.findElement(By.name("password"));
        element.sendKeys("12345678i");

        driver.findElement(By.cssSelector("button.inputButton")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("home"));
        String redirURL = driver.getCurrentUrl();
        System.out.println("Login successfully! Redirected to URL:"+redirURL);

        // Find all elements with the "headerSide" class
        List<WebElement> headerSides = driver.findElements(By.className("headerSide"));

        // Access the second "headerSide" element
        WebElement secondHeaderSide = headerSides.get(1); // Index 1 for the second element (0-based indexing)

        // Find the "headerButtons" element within the second "headerSide" and click it
        WebElement firstHeaderButton = secondHeaderSide.findElement(By.className("headerButtons"));
        firstHeaderButton.click();
        System.out.println("Clicked on profile button...");

        WebElement editProfileButton = wait.until(ExpectedConditions.elementToBeClickable(By.className("editProfileButton")));
        editProfileButton.click();
        System.out.println("Clicked on edit profile button...");

        Thread.sleep(3000);

        WebElement editButton = wait.until(ExpectedConditions.elementToBeClickable(By.className("editButton")));
        editButton.click();
        System.out.println("Clicked on edit button...");

        Thread.sleep(3000);
        Random random = new Random();
        int randomNumber = random.nextInt(101);
        String firstName = "ChangeTest" + randomNumber;
        String lastName = "TestChange" + randomNumber;

        WebElement inputFieldFirstName = driver.findElement(By.cssSelector("input.inputEdit[name='FirstName']"));
        inputFieldFirstName.clear();
        inputFieldFirstName.sendKeys(firstName);
        System.out.println("Changed first name...");

        WebElement inputFieldLastName = driver.findElement(By.cssSelector("input.inputEdit[name='LastName']"));
        inputFieldLastName.clear();
        inputFieldLastName.sendKeys(lastName);
        System.out.println("Changed last name...");

        WebElement EditWindowButton = driver.findElement(By.className("EditWindowButton"));
        EditWindowButton.click();
        System.out.println("Clicked on edit window button...");

        Thread.sleep(3000);

        WebElement inputField_FN = driver.findElement(By.name("FirstName"));
        String firstName_changed = inputField_FN.getAttribute("value");
        System.out.println("Changed first name: "+firstName_changed);

        WebElement inputField_LN = driver.findElement(By.name("LastName"));
        String lastName_changed = inputField_LN.getAttribute("value");
        System.out.println("Changed last name: "+lastName_changed);

        assertEquals(firstName_changed,firstName);
        assertEquals(lastName_changed,lastName);
        System.out.println("Successfully changed first name and last name!");
        driver.quit();
    }
}
