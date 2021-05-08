import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class FirstTestClass {
    String pId,pp;
    @Test
    public void openBrowser() throws InterruptedException {
        String baseURL="https://www.gittigidiyor.com/";
        Path projDirectory= Paths.get(System.getProperty("user.dir"));
        Path driverDirectory=Paths.get(projDirectory.toString(),"driver","chromedriver.exe");
        System.setProperty("webdriver.chrome.driver",driverDirectory.toString());
        WebDriver webDriver=new ChromeDriver();
        webDriver.manage().window().maximize();
        webDriver.get(baseURL);
        acceptCookie(webDriver);
        hover(webDriver);
        login(webDriver);
        ara(webDriver);
        secondPage(webDriver);
        Thread.sleep(2000);
        selectOneItem(webDriver);
        productPrice(webDriver);
        addToBasket(webDriver);
        goToBasketHover(webDriver);
        boolean equal=comparePrices(webDriver);
        if(!equal){
            System.out.println("Basket Price and product price are different");
        }else{
            plusOneItem(webDriver);
            Thread.sleep(3000);
            deleteItem(webDriver);
        }
        Thread.sleep(3000);
        webDriver.close();
    }
    @Test
    public void hover(WebDriver webDriver){
        String xpath="//*[@id=\"main-header\"]/div[3]/div/div/div/div[3]/div/div[1]/div";
        Actions actions=new Actions(webDriver);
        WebElement webElement=webDriver.findElement(By.xpath(xpath));
        actions.moveToElement(webElement).perform();
        click(webDriver);
    }
    @Test
    public void click(WebDriver webDriver){
        String xpath="//*[@id=\"main-header\"]/div[3]/div/div/div/div[3]/div/div[1]/div[2]/div/div/div";
        WebDriverWait wait=new WebDriverWait(webDriver,3);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        webDriver.findElement(By.xpath(xpath)).click();
    }
    @Test
    public void login(WebDriver webDriver){
        webDriver.findElement(By.id("L-UserNameField")).sendKeys("test_deneme_1234@outlook.com");
        webDriver.findElement(By.id("L-PasswordField")).sendKeys("4321_emened_tset");
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        webDriver.findElement(By.id("gg-login-enter")).click();
    }
    @Test
    public void ara(WebDriver webDriver){
        String searchBarXpath="//*[@id=\"main-header\"]/div[3]/div/div/div/div[2]/form/div/div[1]/div[2]/input";
        String buttonXpath="//*[@id=\"main-header\"]/div[3]/div/div/div/div[2]/form/div/div[2]/button";
        webDriver.findElement(By.xpath(searchBarXpath)).sendKeys("bilgisayar");
        try {
            Thread.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        webDriver.findElement(By.xpath(buttonXpath)).click();
    }
    @Test
    public void acceptCookie(WebDriver webDriver){
        webDriver.findElement(By.xpath("//*[@id=\"__next\"]/main/section/section/a")).click();
    }
    @Test
    public void secondPage(WebDriver webDriver){
        String buttonXpath="#best-match-right > div.pager.pt30.hidden-m.gg-d-24 > ul > li.next-link";
        Actions actions=new Actions(webDriver);
        actions.moveToElement(webDriver.findElement(By.cssSelector(buttonXpath))).click().perform();
    }
    @Test
    public void selectOneItem(WebDriver webDriver){
        Random random=new Random();
        int rand=random.nextInt(47)+1;
        webDriver.findElement(By.xpath("//ul[@class='catalog-view clearfix products-container']/li["+rand+"]")).click();
    }
    @Test
    public void productPrice(WebDriver webDriver){
        String productPrice="";
        String product_id=webDriver.findElement(By.xpath("//*[@id=\"productId\"]")).getAttribute("value"); //product id bulunur
        WebElement webElement=webDriver.findElement(By.xpath("//*[@id=\"sp-price-lowPrice\"]"));
        if(webElement.getText() !=null && !webElement.getText().isEmpty()){ //seçilen elemanın indirimli fiyatı
            productPrice=webElement.getText();
        }else{                                                              //seçilen oğe indirimde değil
            webElement=webDriver.findElement(By.xpath("//*[@id=\"sp-price-highPrice\"]"));
            productPrice=webElement.getText();
        }
        pId=product_id;
        pp=productPrice;
    }
    @Test
    public void addToBasket(WebDriver webDriver){
        webDriver.findElement(By.xpath("//*[@id=\"add-to-basket\"]")).click();
    }
    @Test
    public void goToBasketHover(WebDriver webDriver){
        String xPath="//*[@id=\"header_wrapper\"]/div[4]/div[3]";
        Actions actions=new Actions(webDriver);
        WebElement webElement=webDriver.findElement(By.xpath(xPath));
        actions.moveToElement(webElement).perform();
        gotoBasketClick(webDriver);
    }
    @Test
    public void gotoBasketClick(WebDriver webDriver){
        String xPath="//*[@id=\"header_wrapper\"]/div[4]/div[3]/div/div/div/div[2]/div[4]/div[1]";
        WebDriverWait wait=new WebDriverWait(webDriver,3);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xPath)));
        webDriver.findElement(By.xpath(xPath)).click();
        //FIX-> !!!
    }
    @Test
    public String getBasketPrice(WebDriver webDriver){
        String basketPrice=webDriver.findElement(By.xpath("//*[@id=\"cart-items-container\"] /div/div[6]/div[2]/div[2]/div/div[5]/div/div")).getText();
        return basketPrice;
    }
    @Test
    public boolean comparePrices(WebDriver webDriver){
        String basketPrice=getBasketPrice(webDriver);
        return basketPrice.equals(pp);
    }
    @Test
    public void plusOneItem(WebDriver webDriver){
        Select dropdownSelect=new Select(webDriver.findElement(
                By.xpath("//*[@id=\"cart-items-container\"] /div/div[6]/div[2]/div[2]/div/div[4]/div/div[2]/select")));
        int value= Integer.parseInt(dropdownSelect.getFirstSelectedOption().getAttribute("value"));
        dropdownSelect.selectByIndex(value);
    }
    @Test
    public void deleteItem(WebDriver webDriver){
        webDriver.findElement(By.xpath("//*[@id=\"cart-items-container\"] /div/div[6]/div[2]/div[2]/div/div[3]/div/div[2]/div/a")).click();
    }
}
