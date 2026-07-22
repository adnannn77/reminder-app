const db = require("../config/database");

// ==============================
// LOGIN
// ==============================

const login = async (email, password) => {

    const sql = `
        SELECT *
        FROM users
        WHERE email = ?
    `;

    const [rows] = await db.query(sql, [email]);

    if (rows.length === 0) {
        return null;
    }

    const user = rows[0];

    if (user.password !== password) {
        return null;
    }

    return {
        id: user.id,
        nama: user.nama,
        email: user.email,
        photo: user.photo
    };

};

// ==============================
// REGISTER
// ==============================

const register = async (nama, email, password) => {

    const check = await checkEmail(email);

    if (check.length > 0) {

        return {
            status: false,
            message: "Email sudah digunakan"
        };

    }

    const sql = `
        INSERT INTO users
        (nama, email, password, photo)
        VALUES (?, ?, ?, '')
    `;

    await db.query(sql, [
        nama,
        email,
        password
    ]);

    return {
        status: true,
        message: "Register berhasil"
    };

};

// ==============================
// CHECK EMAIL
// ==============================

const checkEmail = async (email) => {

    const sql = `
        SELECT *
        FROM users
        WHERE email = ?
    `;

    const [rows] = await db.query(sql, [email]);

    return rows;

};

module.exports = {

    login,
    register,
    checkEmail

};