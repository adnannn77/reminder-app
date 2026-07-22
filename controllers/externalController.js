const externalService = require("../services/externalService");

// ======================================
// Holiday API
// ======================================

const getHolidays = async (req, res) => {

    try {

        const { year } = req.params;

        const holidays = await externalService.getHolidays(year);

        return res.json({

            status: true,
            message: "Data hari libur berhasil diambil",
            data: holidays

        });

    } catch (err) {

        return res.status(500).json({

            status: false,
            message: err.message

        });

    }

};

// ======================================
// Geocoding API
// ======================================

const searchLocation = async (req, res) => {

    try {

        const { q } = req.query;

        if (!q) {

            return res.status(400).json({

                status: false,
                message: "Parameter q wajib diisi"

            });

        }

        const locations = await externalService.searchLocation(q);

        return res.json({

            status: true,
            message: "Lokasi berhasil ditemukan",
            data: locations

        });

    } catch (err) {

        return res.status(500).json({

            status: false,
            message: err.message

        });

    }

};

// ======================================
// Weather API
// ======================================

const getWeather = async (req, res) => {

    try {

        const { lat, lon } = req.query;

        if (!lat || !lon) {

            return res.status(400).json({

                status: false,
                message: "Parameter lat dan lon wajib diisi"

            });

        }

        const weather = await externalService.getWeather(lat, lon);

        return res.json({

            status: true,
            message: "Data cuaca berhasil diambil",
            data: weather

        });

    } catch (err) {

        return res.status(500).json({

            status: false,
            message: err.message

        });

    }

};

module.exports = {

    getHolidays,
    searchLocation,
    getWeather

};