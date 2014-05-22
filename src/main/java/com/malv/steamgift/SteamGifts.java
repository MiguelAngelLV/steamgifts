package com.malv.steamgift;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;

public class SteamGifts {
	
	
	
	private FirefoxDriver firefox;
	private ArrayList<String> favorites;
	
	
	private static final String URL = "http://www.steamgifts.com";
	private static final String URL_SEARCH = "http://www.steamgifts.com/open/search/";
	
	public SteamGifts() {
		ProfilesIni profilesIni = new ProfilesIni();
		FirefoxProfile profile = profilesIni.getProfile("default");
		firefox = new FirefoxDriver(profile);
		favorites = new ArrayList<String>();
	}
	
	
	public void loadFavorites() {
		try {
			
			BufferedReader file = new BufferedReader(new FileReader("favorities.txt"));
			for (String line = file.readLine(); line != null; line = file.readLine())
				favorites.add(line);
			
				
			file.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
				
	}
	
	
	public void open(String page) {
		
		firefox.get(page);
		List<WebElement> enlaces = firefox.findElements(By.cssSelector(".post:not(.fade) .title a"));
		
		ArrayList<String> links = new ArrayList<String>(enlaces.size());
		
		for (WebElement a : enlaces)
			links.add(a.getAttribute("href"));
		
		
		for (String a : links) {
			
			firefox.get(a);
			
			if (firefox.findElementsByCssSelector(".submit_entry").size() > 0)
				firefox.findElementByCssSelector(".submit_entry").click();
					
		
		}
		
		
	}
	

	public void start() {
		for (String s : favorites)
			open(URL_SEARCH + s.replace(" ", "%20"));
		
		open(URL);
		
		firefox.close();
	}
	
	
	public static void main (String argv[]) {
		SteamGifts steamgifts = new SteamGifts();
		steamgifts.loadFavorites();
		steamgifts.start();
		
	}
	
	
	
}
