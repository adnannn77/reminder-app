const externalService = require("../services/externalService");

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

module.exports = { getWeather };