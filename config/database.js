const { Pool } = require("pg");

const db = new Pool({
    connectionString: process.env.DATABASE_URL,
    // Diperlukan untuk koneksi ke Supabase / Cloud Provider
    ssl: {
        rejectUnauthorized: false
    }
});

module.exports = db;