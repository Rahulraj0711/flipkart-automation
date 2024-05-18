package demo;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WrapperMethods {

    public static List<WebElement> tc1_resultList_BasedOnRating(WebDriver driver, By by) {
        List<WebElement> resultList=wrap_findElements(driver, by)
        .stream()
        .filter(e->Double.parseDouble(e.getText())<=4.0)
        .collect(Collectors.toList());

        return resultList;
    }

    public static HashMap<String,String> tc2_resultMap_BasedOnDiscount(List<WebElement> resultList) {
        HashMap<String, String> resultMap=resultList
            .stream()
            .filter(result->Integer.parseInt(result.findElement(By.xpath("div[2]/div[1]/div[1]/div[3]/span")).getText().split("%")[0])>17)
            .map(item -> {
                String title=item.findElement(By.xpath("div[1]/div[1]")).getText();
                String discount=item.findElement(By.xpath("div[2]/div[1]/div[1]/div[3]/span")).getText();
                return new String[]{title, discount};
            })
            .collect(Collectors.toMap(e->e[0], e->e[1], (a,b)->b, HashMap::new));
        
        return resultMap;
    }

    public static List<WebElement> tc3_resultList_BasedOnNumOfReviews(WebDriver driver, By by) {
        List<WebElement> resultList=WrapperMethods.wrap_findElements(driver, by)
            .stream()
            .sorted((e1,e2) -> {
                String reviewsString1=e1.getText().replace("(", "").replace(")", ""). replace(",", "");
                String reviewsString2=e2.getText().replace("(", "").replace(")", ""). replace(",", "");
                int numOfReviews1=Integer.parseInt(reviewsString1);
                int numOfReviews2=Integer.parseInt(reviewsString2);
                return numOfReviews2-numOfReviews1;
            })
            .limit(5)
            .collect(Collectors.toList());

        return resultList;
    }

    public static HashMap<String,String> tc3_resultMap(List<WebElement> resultList) {
        HashMap<String, String> resultMap=resultList.stream()
            .map(item -> {
                String title=item.findElement(By.xpath("../../a[2]")).getText();
                String imageURL=item.findElement(By.xpath("../../a[1]//img")).getAttribute("src");
                return new String[]{title, imageURL};
            })
            .collect(Collectors.toMap(e->e[0], e->e[1], (a,b)->b, HashMap::new));
        
        return resultMap;
    }

    public static void searchProcess(WebDriver driver, String itemName, By by) throws InterruptedException {
        WebElement searchBox=wrap_findElement(driver, by);
        wrap_sendKeys(searchBox, itemName);
        wrap_wait(5000);
    }

    public static void wrap_printMessage(String message) {
        System.out.println(message);
    }

    public static WebElement wrap_findElement(WebDriver driver, By by) {
        return driver.findElement(by);
    }

    public static List<WebElement> wrap_findElements(WebDriver driver, By by) {
        return driver.findElements(by);
    }

    public static void wrap_sendKeys(WebElement element, String itemName) throws InterruptedException {
        // element.clear();
        element.sendKeys(Keys.chord(Keys.CONTROL,"a", Keys.DELETE));
        element.sendKeys(itemName);
        element.sendKeys(Keys.ENTER);
    }

    public static void wrap_clickElement(WebElement element) {
        element.click();
    }
    
    public static void wrap_advancedWait(WebDriver driver, By by) {
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public static void wrap_wait(long time) throws InterruptedException {
        Thread.sleep(time);
    }
}
