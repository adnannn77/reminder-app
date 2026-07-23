const express = require("express");
const router = express.Router();
const externalController = require("../controllers/externalController");

router.get("/external/weather", externalController.getWeather);

module.exports = router;