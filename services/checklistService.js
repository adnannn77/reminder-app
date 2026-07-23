const db = require("../config/database");

const getAll = async () => {
    const { rows } = await db.query(`
        SELECT
            checklists.id,
            checklists.reminder_id,
            checklists.title,
            checklists.is_checked,
            checklists.sort_order,

            TO_CHAR(checklists.created_at,'YYYY-MM-DD HH24:MI:SS') AS created_at,
            TO_CHAR(checklists.updated_at,'YYYY-MM-DD HH24:MI:SS') AS updated_at,

            reminders.title AS reminder_title

        FROM checklists

        LEFT JOIN reminders
            ON reminders.id = checklists.reminder_id

        ORDER BY
            checklists.sort_order ASC,
            checklists.id ASC
    `);
    return rows;
};

const getById = async (id) => {
    const { rows } = await db.query(`
        SELECT
            checklists.id,
            checklists.reminder_id,
            checklists.title,
            checklists.is_checked,
            checklists.sort_order,

            TO_CHAR(checklists.created_at,'YYYY-MM-DD HH24:MI:SS') AS created_at,
            TO_CHAR(checklists.updated_at,'YYYY-MM-DD HH24:MI:SS') AS updated_at,

            reminders.title AS reminder_title

        FROM checklists

        LEFT JOIN reminders
            ON reminders.id = checklists.reminder_id

        WHERE checklists.id = $1
    `, [id]);
    return rows;
};

const create = async (data) => {
    await db.query(`
        INSERT INTO checklists (reminder_id, title, sort_order)
        VALUES ($1, $2, $3)
    `, [data.reminder_id, data.title, data.sort_order]);
};

const update = async (id, data) => {
    await db.query(`
        UPDATE checklists SET
            reminder_id=$1,
            title=$2,
            sort_order=$3
        WHERE id=$4
    `, [data.reminder_id, data.title, data.sort_order, id]);
};

const remove = async (id) => {
    await db.query("DELETE FROM checklists WHERE id=$1", [id]);
};

const checked = async (id, is_checked) => {
    await db.query("UPDATE checklists SET is_checked=$1 WHERE id=$2", [is_checked, id]);
};

module.exports = {
    getAll, getById, create, update, remove, checked
};