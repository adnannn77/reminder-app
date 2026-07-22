const authService = require("../services/authService");

// ==============================
// LOGIN
// ==============================

const login = async (req, res) => {

    try {

        const { email, password } = req.body;

        if (!email || !password) {

            return res.status(400).json({
                status: false,
                message: "Email dan password wajib diisi"
            });

        }

        const user = await authService.login(
            email,
            password
        );

        if (!user) {

            return res.status(401).json({
                status: false,
                message: "Email atau password salah"
            });

        }

        return res.json({
            status: true,
            message: "Login berhasil",
            data: user
        });

    } catch (err) {

        return res.status(500).json({
            status: false,
            message: err.message
        });

    }

};

// ==============================
// REGISTER
// ==============================

const register = async (req, res) => {

    try {

        const { nama, email, password } = req.body;

        if (!nama || !email || !password) {

            return res.status(400).json({
                status: false,
                message: "Semua data wajib diisi"
            });

        }

        const result = await authService.register(
            nama,
            email,
            password
        );

        if (!result.status) {

            return res.status(400).json(result);

        }

        return res.status(201).json(result);

    } catch (err) {

        return res.status(500).json({
            status: false,
            message: err.message
        });

    }

};

module.exports = {

    login,
    register

};