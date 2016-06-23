package com.malv.steamgifts

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxProfile
import org.openqa.selenium.firefox.internal.ProfilesIni

public class SteamGifts {


    private static final String URL = "http://www.steamgifts.com";
    private static final String URL_WISH_LIST = "https://www.steamgifts.com/giveaways/search?type=wishlist";

    int points;
    WebDriver browser;
    List<Game> games

    public SteamGifts() {


        System.setProperty("webdriver.chrome.driver", "/home/miguel/bin/chromedriver");


        ProfilesIni profilesIni = new ProfilesIni();
        FirefoxProfile profile = profilesIni.getProfile("default");

        ChromeOptions options = new ChromeOptions()
        options.addArguments("user-data-dir=/home/miguel/seleniumprofile")
        options.addArguments("start-maximized");


        browser = new ChromeDriver(options)
    }




    void getElements() {
        games = new ArrayList<>()
        browser.get(URL_WISH_LIST)
        updatePoints()

        List<WebElement> elements = browser.findElementsByCssSelector(".page__heading + div [data-game-id]")
        elements.each {
            addGame(it)
        }


        browser.get(URL)

        elements = browser.findElementsByCssSelector("[data-game-id]")
        elements.each {
            addGame(it)
        }


        games.each {
            subscribe(it)
        }


        browser.close()
    }



    void addGame(WebElement element) {

        if(!checkPermission(element))
            return

        Game game = new Game()
        game.url = getURL(element)
        game.cost = getPoints(element)

        games.add(game)

    }


    void subscribe(Game game) {
        if (game.cost > points)
            return


        browser.get(game.url)
        browser.findElements(By.cssSelector(".sidebar__entry-insert")).each { it.click() }

        updatePoints()
    }

    void updatePoints() {
        WebElement element = browser.findElementByCssSelector("span.nav__points")
        points = element.text.toInteger()
    }

    boolean checkPermission(WebElement element) {

        if (element.findElements(By.cssSelector(".is-faded")).size() > 0)
            return false

        return element.findElements(By.cssSelector(".giveaway__column--contributor-level--negative")).isEmpty()
    }


    int getPoints(WebElement element) {
        return  element.findElements(By.cssSelector(".giveaway__heading__thin")).last()
                        .text.replaceAll(/[^\d]/, "").toInteger()


    }


    String getURL(WebElement element) {
        return element.findElement(By.cssSelector(".giveaway__heading__name")).getAttribute("href")
    }

    void updateOptions() {

    }

}
