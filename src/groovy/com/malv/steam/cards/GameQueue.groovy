package com.malv.steam.cards

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxProfile
import org.openqa.selenium.firefox.internal.ProfilesIni

class GameQueue {


    public static final String URL = "http://store.steampowered.com/"

    int points;
    WebDriver browser;

    public GameQueue() {

        ProfilesIni profilesIni = new ProfilesIni();
        FirefoxProfile profile = profilesIni.getProfile("default");

        browser = new FirefoxDriver(profile)
    }


    void seeQueue() {

        browser.get(URL)
        browser.findElement(By.cssSelector("#discovery_queue_start_link")).click()

        while (true) {

            browser.findElements(By.cssSelector("#ageYear + a")).each {
                it.click()
            }


            List<WebElement> elements = browser.findElements(By.cssSelector("div.btn_next_in_queue"))

            if (elements.isEmpty())
                return

            elements.first().click()

            println("Nueva")

        }


    }


    public void sleep() {
        Thread.sleep(6*1000)
    }
}
