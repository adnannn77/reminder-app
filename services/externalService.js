const axios = require("axios");

const getWeather = async (latitude, longitude) => {
    const response = await axios.get(
        "https://api.open-meteo.com/v1/forecast",
        {
            params: {
                latitude,
                longitude,
                current: [
                    "temperature_2m",
                    "relative_humidity_2m",
                    "weather_code",
                    "wind_speed_10m"
                ].join(",")
            }
        }
    );

    const current = response.data.current;

    return {
        temperature: current.temperature_2m,
        humidity: current.relative_humidity_2m,
        weather_code: current.weather_code,
        wind_speed: current.wind_speed_10m
    };
};

module.exports = { getWeather };