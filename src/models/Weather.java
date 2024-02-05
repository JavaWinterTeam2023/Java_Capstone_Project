package models;

import java.util.Random;

public class Weather {
    private String[] conditions = {
    		"Sunny", 
    		"Cloudy", 
    		"Snowy", 
    		"Rainy"};
    private Random random;
    public String currentWeather;

    public Weather() {
        random = new Random();
        this.currentWeather = setWeatherCondition();
    }

    public String getWeatherCondition() {
    	return this.currentWeather;
    }
    
    public String setWeatherCondition() {
        int index = random.nextInt(conditions.length);
        this.currentWeather = conditions[index];
        return this.currentWeather;
    }
}