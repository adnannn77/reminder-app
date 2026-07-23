const db = require("../config/database");

const getAll = async () => {
    const { rows } = await db.query(`
        SELECT
            r.id,
            r.user_id,
            r.label_id,
            r.repeat_type_id,
            r.title,
            r.note,
            r.url,

            TO_CHAR(r.reminder_date,'YYYY-MM-DD') AS reminder_date,
            TO_CHAR(r.reminder_time,'HH24:MI:SS') AS reminder_time,

            r.location_name,
            r.latitude,
            r.longitude,

            r.priority,
            r.reminder_before,
            r.color,
            r.is_completed,
            r.is_archived,

            TO_CHAR(r.created_at,'YYYY-MM-DD HH24:MI:SS') AS created_at,
            TO_CHAR(r.updated_at,'YYYY-MM-DD HH24:MI:SS') AS updated_at,

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

const getByUser = async (userId) => {
    const { rows } = await db.query(`
        SELECT
            r.id,
            r.user_id,
            r.label_id,
            r.repeat_type_id,
            r.title,
            r.note,
            r.url,

            TO_CHAR(r.reminder_date,'YYYY-MM-DD') AS reminder_date,
            TO_CHAR(r.reminder_time,'HH24:MI:SS') AS reminder_time,

            r.location_name,
            r.latitude,
            r.longitude,

            r.priority,
            r.reminder_before,
            r.color,
            r.is_completed,
            r.is_archived,

            TO_CHAR(r.created_at,'YYYY-MM-DD HH24:MI:SS') AS created_at,
            TO_CHAR(r.updated_at,'YYYY-MM-DD HH24:MI:SS') AS updated_at,

            l.name  AS label_name,
            l.color AS label_color,

            rt.name AS repeat_type_name

        FROM reminders r

        LEFT JOIN labels l
            ON r.label_id = l.id

        LEFT JOIN repeat_types rt
            ON r.repeat_type_id = rt.id

        WHERE
            r.user_id = $1
            AND r.is_archived = FALSE

        ORDER BY
            r.reminder_date,
            r.reminder_time
    `, [userId]);
    return rows;
};

const getById = async (id) => {
    const { rows } = await db.query(`
        SELECT
            r.id,
            r.user_id,
            r.label_id,
            r.repeat_type_id,
            r.title,
            r.note,
            r.url,

            TO_CHAR(r.reminder_date,'YYYY-MM-DD') AS reminder_date,
            TO_CHAR(r.reminder_time,'HH24:MI:SS') AS reminder_time,

            r.location_name,
            r.latitude,
            r.longitude,

            r.priority,
            r.reminder_before,
            r.color,
            r.is_completed,
            r.is_archived,

            TO_CHAR(r.created_at,'YYYY-MM-DD HH24:MI:SS') AS created_at,
            TO_CHAR(r.updated_at,'YYYY-MM-DD HH24:MI:SS') AS updated_at,

            l.name  AS label_name,
            l.color AS label_color,

            rt.name AS repeat_type_name

        FROM reminders r

        LEFT JOIN labels l
            ON r.label_id = l.id

        LEFT JOIN repeat_types rt
            ON r.repeat_type_id = rt.id

        WHERE r.id = $1
    `, [id]);
    return rows;
};

const create = async (data) => {
    await db.query(`
        INSERT INTO reminders (
            user_id, label_id, repeat_type_id, title, note, url,
            reminder_date, reminder_time, location_name, latitude, longitude,
            priority, reminder_before, color
        )
        VALUES ($1, $2, $3, $4, $5, $6, $7, $8, $9, $10, $11, $12, $13, $14)
    `, [
        data.user_id || 1, // Default to user 1
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

const update = async (id, data) => {
    await db.query(`
        UPDATE reminders SET
            label_id = $1,
            repeat_type_id = $2,
            title = $3,
            note = $4,
            url = $5,
            reminder_date = $6,
            reminder_time = $7,
            location_name = $8,
            latitude = $9,
            longitude = $10,
            priority = $11,
            reminder_before = $12,
            color = $13
        WHERE id = $14
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

const remove = async (id) => {
    await db.query("DELETE FROM reminders WHERE id=$1", [id]);
};

const complete = async (id) => {
    await db.query("UPDATE reminders SET is_completed=TRUE WHERE id=$1", [id]);
};

const archive = async (id) => {
    await db.query("UPDATE reminders SET is_archived=TRUE WHERE id=$1", [id]);
};

module.exports = {
    getAll, getByUser, getById, create, update, remove, complete, archive
};