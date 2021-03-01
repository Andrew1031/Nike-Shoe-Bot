package org.example.untitled;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.Scanner;

import java.util.List;

public class MainPage {
    public static boolean isInteger(double input) {
        if (input % 1 == 0) {
            return true;
        }

        return false;
    }

    public static void main (String[] args) {
        //prompts user for shoe link and desired size
        Scanner shoeScan = new Scanner(System.in);
        System.out.println("Paste shoe link");
        String shoeLink = shoeScan.nextLine();
        Scanner sizeScan = new Scanner(System.in);
        System.out.println("Type shoe size");
        double shoeSize = sizeScan.nextDouble();

        //opens nike shoe page
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\15102\\Desktop\\chromedriver.exe");
        WebDriver driver=new ChromeDriver();
        driver.navigate().to(shoeLink);
        driver.manage().window().maximize();

        //iterate through div elements to find the desired size
        String desiredSize = "";
        int count = 0;
        List<WebElement> sizes = driver.findElements(By.className("css-xf3ahq"));
        java.util.Iterator<WebElement> sizeIterate = sizes.iterator();
        while (sizeIterate.hasNext()) {
            WebElement sizeElement = sizeIterate.next();
            count++;
            int parsedSize = -1;
            if (isInteger(shoeSize)) {
                parsedSize = (int)shoeSize;
            }

            //if we have found our desired size
            if (parsedSize == -1) {
                if (sizeElement.getText().contains("M " + shoeSize + " / W")) {
                    desiredSize = sizeElement.getAttribute("for");
                    break;
                }
            }

            else {
                if (sizeElement.getText().contains("M " + parsedSize + " / W")) {
                    desiredSize = sizeElement.getAttribute("for");
                    break;
                }
            }
        }

        //clicks desired size, adds to cart and routes to cart page
        String xpath = "//*[@id=\"buyTools\"]/div[1]/fieldset/div/div[" + count + "]/label";
        driver.findElement(By.xpath(xpath)).click();
        driver.findElement(By.className("css-15oagn2")).click();
        driver.findElement(By.cssSelector("button.ncss-btn-primary-dark.btn-lg.add-to-cart-btn")).click();
        driver.findElement(By.cssSelector("a.icon-btn.ripple.d-sm-b")).click();
    }
}