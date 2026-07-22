const express = require("express");

const router = express.Router();

const externalController = require("../controllers/externalController");

// ======================================
// Holiday API
// ======================================

router.get(
    "/external/holidays/:year",
    externalController.getHolidays
);

// ======================================
// Geocoding API
// ======================================

router.get(
    "/external/geocode",
    externalController.searchLocation
);

// ======================================
// Weather API
// ======================================

router.get(
    "/external/weather",
    externalController.getWeather
);

module.exports = router;