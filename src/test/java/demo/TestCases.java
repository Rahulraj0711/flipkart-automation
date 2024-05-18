package demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class TestCases {
    static WebDriver driver;

    @BeforeSuite(alwaysRun = true)
    public void createDriver()
    {
        System.out.println("Creating Driver and Starting Test Execution...");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.flipkart.com/");
    }

    @Test(description = "Search Washing Machine", enabled = true, priority = 1)
    public void testCase01() throws InterruptedException{
        System.out.println("Start Test Case: testCase01");
        
        // Search for "Washing Machine" product
        WrapperMethods.searchProcess(driver, "Washing Machine", By.xpath("//input[contains(@title, 'Search')]"));

        // Sort the results by popularity
        WebElement popularityTab=WrapperMethods.wrap_findElement(driver, By.xpath("//div[text()='Popularity']"));
        WrapperMethods.wrap_clickElement(popularityTab);
        WrapperMethods.wrap_wait(3000);
        
        // Get the list of products with rating less than or equal to 4
        List<WebElement> resultList=WrapperMethods.tc1_resultList_BasedOnRating(driver, By.xpath("//div[@class='tUxRFH']//div[@class='yKfJKb row']/div[1]/div[2]//div"));

        // Print the number of products with rating less than or equal to 4
        WrapperMethods.wrap_printMessage("Number of items with rating <=4: "+resultList.size());

        System.out.println("End Test Case: testCase01");
    }

    @Test(description = "Search iPhone", enabled = true, priority = 2)
    public  void testCase02() throws InterruptedException {
        System.out.println("Start Test Case: testCase02");
        
        // Search for "iPhone" product
        WrapperMethods.searchProcess(driver, "iPhone", By.xpath("//input[contains(@title, 'Search')]"));
        
        // Get the list of products
        List<WebElement> resultList=WrapperMethods.wrap_findElements(driver, By.xpath("//div[@class='tUxRFH']//div[@class='yKfJKb row']"));
        
        // Get the map of products with "Title" as key and "Discount" as value having discount more than 17%
        HashMap<String, String> resultMap=WrapperMethods.tc2_resultMap_BasedOnDiscount(resultList);

        // Print the title and discount of all products having discount more than 17%
        int x=0;
        for(Map.Entry<String, String> e:resultMap.entrySet()) {
            WrapperMethods.wrap_printMessage(++x+") Title: "+e.getKey()+"\nDiscount: "+e.getValue());
        }
            
        System.out.println("End Test Case: testCase02");
    }
    
    @Test(description = "Search Coffee Mug", enabled = true, priority = 3)
    public  void testCase03() throws InterruptedException {
        System.out.println("Start Test Case: testCase03");
        
        // Search for "Coffee Mug" product
        WrapperMethods.searchProcess(driver, "Coffee Mug", By.xpath("//input[contains(@title, 'Search')]"));

        // Select filter customer rating 4 stars and above
        WebElement customerRating=WrapperMethods.wrap_findElement(driver, By.xpath("//section/div/div[text()='Customer Ratings']/../../div[2]//label/div[contains(text(), '4')]"));
        WrapperMethods.wrap_clickElement(customerRating);
        WrapperMethods.wrap_wait(5000);

        // Get the list of top 5 products with highest number of reviews
        List<WebElement> resultList=WrapperMethods.tc3_resultList_BasedOnNumOfReviews(driver, By.xpath("//div[@class='slAVV4']//span[@class='Wphh3N']"));
        
        // Get the map of products with "Title" as key and "Image URL" as value having highest number of reviews
        HashMap<String, String> map=WrapperMethods.tc3_resultMap(resultList);
            
        // Print the title and image url of top 5 products having highest number of reviews
        int x=0;
        for(Map.Entry<String, String> e:map.entrySet()) {
            WrapperMethods.wrap_printMessage(++x+") Title: "+e.getKey()+"\nImage URL: "+e.getValue());
        }

        System.out.println("End Test Case: testCase03");
    }

    @AfterSuite
    public static void endTest()
    {
        System.out.println("Closing Browser and Ending Test Execution.");
        driver.close();
        driver.quit();

    }
}
