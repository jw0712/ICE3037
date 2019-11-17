package com.example.weather;

import androidx.annotation.NonNull;

public class HttpResponseDto {
    private WeatherDto[] weather = null;
    private SysDto sys = null;
    private static String name="";

    public WeatherDto[] getWeather() {
        return weather;
    }

    public void setWeather(WeatherDto[] weather) {
        this.weather = weather;
    }

    public SysDto getSys() {
        return sys;
    }

    public void setSys(SysDto sys) {
        this.sys = sys;
    }

    public static String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }

    public class WeatherDto{
        private String main="";
        private String description="";
        private String icon="";

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString();
        }
    }

    public class SysDto{
        private String country="";
        private int sunrise;
        private int sunset;

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public int getSunrise() {
            return sunrise;
        }

        public void setSunrise(int sunrise) {
            this.sunrise = sunrise;
        }

        public int getSunset() {
            return sunset;
        }

        public void setSunset(int sunset) {
            this.sunset = sunset;
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString();
        }
    }
}
