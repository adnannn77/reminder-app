const db = require("../config/database");

// ==============================
// Ambil semua reminder
// ==============================

const getAll = async () => {

    const [rows] = await db.query(`
        SELECT
            r.id,
            r.user_id,
            r.label_id,
            r.repeat_type_id,
            r.title,
            r.note,
            r.url,

            DATE_FORMAT(r.reminder_date,'%Y-%m-%d') AS reminder_date,
            TIME_FORMAT(r.reminder_time,'%H:%i:%s') AS reminder_time,

            r.location_name,
            r.latitude,
            r.longitude,

            r.priority,
            r.reminder_before,
            r.color,
            r.is_completed,
            r.is_archived,

            DATE_FORMAT(r.created_at,'%Y-%m-%d %H:%i:%s') AS created_at,
            DATE_FORMAT(r.updated_at,'%Y-%m-%d %H:%i:%s') AS updated_at,

            l.name  AS label_name,
            l.color AS label_color,

            rt.name AS repeat_type_name

        FROM reminders r

        LEFT JOIN labels l
            ON r.label_id = l.id

        LEFT JOIN repeat_types rt
            ON r.repeat_type_id = rt.id

        ORDER BY
            r.reminder_date,
            r.reminder_time
    `);

    return rows;

};

// ==============================
// Ambil reminder berdasarkan user
// ==============================

const getByUser = async (userId) => {

    const [rows] = await db.query(`
        SELECT
            r.id,
            r.user_id,
            r.label_id,
            r.repeat_type_id,
            r.title,
            r.note,
            r.url,

            DATE_FORMAT(r.reminder_date,'%Y-%m-%d') AS reminder_date,
            TIME_FORMAT(r.reminder_time,'%H:%i:%s') AS reminder_time,

            r.location_name,
            r.latitude,
            r.longitude,

            r.priority,
            r.reminder_before,
            r.color,
            r.is_completed,
            r.is_archived,

            DATE_FORMAT(r.created_at,'%Y-%m-%d %H:%i:%s') AS created_at,
            DATE_FORMAT(r.updated_at,'%Y-%m-%d %H:%i:%s') AS updated_at,

            l.name  AS label_name,
            l.color AS label_color,

            rt.name AS repeat_type_name

        FROM reminders r

        LEFT JOIN labels l
            ON r.label_id = l.id

        LEFT JOIN repeat_types rt
            ON r.repeat_type_id = rt.id

        WHERE
            r.user_id = ?
            AND r.is_archived = 0

        ORDER BY
            r.reminder_date,
            r.reminder_time
    `, [userId]);

    return rows;

};

// ==============================
// Ambil reminder berdasarkan ID
// ==============================

const getById = async (id) => {

    const [rows] = await db.query(`
        SELECT
            r.id,
            r.user_id,
            r.label_id,
            r.repeat_type_id,
            r.title,
            r.note,
            r.url,

            DATE_FORMAT(r.reminder_date,'%Y-%m-%d') AS reminder_date,
            TIME_FORMAT(r.reminder_time,'%H:%i:%s') AS reminder_time,

            r.location_name,
            r.latitude,
            r.longitude,

            r.priority,
            r.reminder_before,
            r.color,
            r.is_completed,
            r.is_archived,

            DATE_FORMAT(r.created_at,'%Y-%m-%d %H:%i:%s') AS created_at,
            DATE_FORMAT(r.updated_at,'%Y-%m-%d %H:%i:%s') AS updated_at,

            l.name  AS label_name,
            l.color AS label_color,

            rt.name AS repeat_type_name

        FROM reminders r

        LEFT JOIN labels l
            ON r.label_id = l.id

        LEFT JOIN repeat_types rt
            ON r.repeat_type_id = rt.id

        WHERE r.id = ?
    `, [id]);

    return rows;

};

// ==============================
// Tambah reminder
// ==============================

const create = async (data) => {

    await db.query(`
        INSERT INTO reminders (
            user_id,
            label_id,
            repeat_type_id,
            title,
            note,
            url,
            reminder_date,
            reminder_time,
            location_name,
            latitude,
            longitude,
            priority,
            reminder_before,
            color
        )
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    `, [
        data.user_id,
        data.label_id || null,
        data.repeat_type_id || null,
        data.title,
        data.note,
        data.url,
        data.reminder_date,
        data.reminder_time,
        data.location_name,
        data.latitude,
        data.longitude,
        data.priority,
        data.reminder_before,
        data.color
    ]);

};

// ==============================
// Update reminder
// ==============================

const update = async (id, data) => {

    await db.query(`
        UPDATE reminders SET

            label_id = ?,
            repeat_type_id = ?,
            title = ?,
            note = ?,
            url = ?,
            reminder_date = ?,
            reminder_time = ?,
            location_name = ?,
            latitude = ?,
            longitude = ?,
            priority = ?,
            reminder_before = ?,
            color = ?

        WHERE id = ?
    `, [

        data.label_id || null,
        data.repeat_type_id || null,
        data.title,
        data.note,
        data.url,
        data.reminder_date,
        data.reminder_time,
        data.location_name,
        data.latitude,
        data.longitude,
        data.priority,
        data.reminder_before,
        data.color,
        id

    ]);

};

// ==============================
// Hapus reminder
// ==============================

const remove = async (id) => {

    await db.query(
        "DELETE FROM reminders WHERE id=?",
        [id]
    );

};

// ==============================
// Tandai selesai
// ==============================

const complete = async (id) => {

    await db.query(
        "UPDATE reminders SET is_completed=1 WHERE id=?",
        [id]
    );

};

// ==============================
// Arsipkan reminder
// ==============================

const archive = async (id) => {

    await db.query(
        "UPDATE reminders SET is_archived=1 WHERE id=?",
        [id]
    );

};

module.exports = {
    getAll,
    getByUser,
    getById,
    create,
    update,
    remove,
    complete,
    archive
};