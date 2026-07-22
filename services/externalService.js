const axios = require("axios");

// ======================================
// Holiday API (Nager.Date)
// ======================================

const getHolidays = async (year) => {

    const response = await axios.get(
        `https://date.nager.at/api/v3/PublicHolidays/${year}/ID`
    );

    return response.data;

};

// ======================================
// Geocoding API (OpenStreetMap Nominatim)
// ======================================

const searchLocation = async (keyword) => {

    const response = await axios.get(
        "https://nominatim.openstreetmap.org/search",
        {
            params: {
                q: keyword,
                format: "jsonv2",
                limit: 5
            },
            headers: {
                "User-Agent": "Reminder-App/1.0"
            }
        }
    );

    return response.data.map(item => ({
        display_name: item.display_name,
        latitude: item.lat,
        longitude: item.lon
    }));

};

// ======================================
// Weather API (Open-Meteo)
// ======================================

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

module.exports = {

    getHolidays,
    searchLocation,
    getWeather

};