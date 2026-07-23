const { Pool } = require("pg");
const { URL } = require("url");

let dbUrl = process.env.DATABASE_URL;

// Otomatis ubah koneksi Supabase default (IPv6) menjadi Connection Pooler (IPv4)
// agar Vercel Serverless Functions bisa terhubung ke database.
if (dbUrl && dbUrl.includes("db.nqrcylavorzhkwkbsqws.supabase.co")) {
    try {
        const urlObj = new URL(dbUrl);
        urlObj.hostname = "aws-0-ap-southeast-1.pooler.supabase.com";
        urlObj.port = "6543";
        urlObj.username = "postgres.nqrcylavorzhkwkbsqws";
        dbUrl = urlObj.toString();
    } catch (e) {
        console.error("Gagal mem-parsing DATABASE_URL:", e);
    }
}

const db = new Pool({
    connectionString: dbUrl,
    // Diperlukan untuk koneksi ke Supabase / Cloud Provider
    ssl: {
        rejectUnauthorized: false
    }
});

module.exports = db;