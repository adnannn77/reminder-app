require('dotenv').config();
const db = require('./config/database');

async function migrate() {
    try {
        await db.query(`ALTER TABLE reminders ADD COLUMN IF NOT EXISTS device_id VARCHAR(255);`);
        console.log("Added device_id to reminders.");

        await db.query(`ALTER TABLE labels ADD COLUMN IF NOT EXISTS device_id VARCHAR(255);`);
        console.log("Added device_id to labels.");
    } catch (err) {
        console.error("Migration error:", err);
    } finally {
        console.log("Migration finished.");
        process.exit(0);
    }
}

migrate();
