package com.example.weatherapp;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import com.google.gson.JsonObject;

public class WeatherApp extends Application {
    private TextField locationField;
    private Label weatherInfo;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Weather Information App");

        // Load the new background image
        Image backgroundImage = new Image(getClass().getResourceAsStream("/images/background.jpg"));
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background backgroundStyle = new Background(background);

        // Create the layout pane
        VBox layout = new VBox();
        layout.setSpacing(10);
        layout.setPadding(new Insets(20));
        layout.setBackground(backgroundStyle); // Set the background image

        // Create location input field
        locationField = new TextField();
        locationField.setPromptText("Enter location (e.g., city name)");

        // Create fetch weather button
        Button fetchWeatherButton = new Button("Get Weather");
        fetchWeatherButton.setOnAction(e -> fetchWeather());

        // Create label for weather information
        weatherInfo = new Label();

        // Add nodes to layout
        layout.getChildren().addAll(locationField, fetchWeatherButton, weatherInfo);

        // Set the scene
        Scene scene = new Scene(layout, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void fetchWeather() {
        String location = locationField.getText().trim();
        if (!location.isEmpty()) {
            try {
                // Fetch weather data
                JsonObject weatherData = WeatherAPI.getWeatherData(location);
                String temperature = weatherData.getAsJsonObject("main").get("temp").getAsString();
                String humidity = weatherData.getAsJsonObject("main").get("humidity").getAsString();
                String windSpeed = weatherData.getAsJsonObject("wind").get("speed").getAsString();
                String description = weatherData.getAsJsonArray("weather").get(0).getAsJsonObject().get("description").getAsString();

                // Display weather information
                weatherInfo.setText("Temperature: " + temperature + "°C\n" +
                        "Humidity: " + humidity + "%\n" +
                        "Wind Speed: " + windSpeed + " m/s\n" +
                        "Description: " + description);
            } catch (Exception ex) {
                weatherInfo.setText("Error fetching weather data");
            }
        } else {
            weatherInfo.setText("Please enter a location");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
