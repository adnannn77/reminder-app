const db = require("../config/database");

const login = async (email, password) => {
    const { rows } = await db.query(
        "SELECT * FROM users WHERE email = $1",
        [email]
    );

    if (rows.length === 0) return null;
    const user = rows[0];
    if (user.password !== password) return null;

    return {
        id: user.id,
        nama: user.nama,
        email: user.email,
        photo: user.photo
    };
};

const register = async (nama, email, password) => {
    const check = await checkEmail(email);
    if (check.length > 0) {
        return { status: false, message: "Email sudah digunakan" };
    }

    await db.query(
        "INSERT INTO users (nama, email, password, photo) VALUES ($1, $2, $3, '')",
        [nama, email, password]
    );

    return { status: true, message: "Register berhasil" };
};

const checkEmail = async (email) => {
    const { rows } = await db.query(
        "SELECT * FROM users WHERE email = $1",
        [email]
    );
    return rows;
};

module.exports = {
    login,
    register,
    checkEmail
};