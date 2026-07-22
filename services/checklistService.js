const db = require("../config/database");

// ==============================
// Ambil semua checklist
// ==============================

const getAll = async () => {

    const [rows] = await db.query(`
        SELECT
            checklists.id,
            checklists.reminder_id,
            checklists.title,
            checklists.is_checked,
            checklists.sort_order,

            DATE_FORMAT(checklists.created_at,'%Y-%m-%d %H:%i:%s') AS created_at,
            DATE_FORMAT(checklists.updated_at,'%Y-%m-%d %H:%i:%s') AS updated_at,

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

// ==============================
// Ambil checklist berdasarkan ID
// ==============================

const getById = async (id) => {

    const [rows] = await db.query(`
        SELECT
            checklists.id,
            checklists.reminder_id,
            checklists.title,
            checklists.is_checked,
            checklists.sort_order,

            DATE_FORMAT(checklists.created_at,'%Y-%m-%d %H:%i:%s') AS created_at,
            DATE_FORMAT(checklists.updated_at,'%Y-%m-%d %H:%i:%s') AS updated_at,

            reminders.title AS reminder_title

        FROM checklists

        LEFT JOIN reminders
            ON reminders.id = checklists.reminder_id

        WHERE checklists.id = ?
    `,[id]);

    return rows;

};

// ==============================
// Tambah checklist
// ==============================

const create = async (data) => {

    await db.query(`
        INSERT INTO checklists
        (
            reminder_id,
            title,
            sort_order
        )
        VALUES
        (
            ?,?,?
        )
    `,[
        data.reminder_id,
        data.title,
        data.sort_order
    ]);

};

// ==============================
// Update checklist
// ==============================

const update = async (id,data) => {

    await db.query(`
        UPDATE checklists SET

            reminder_id=?,
            title=?,
            sort_order=?

        WHERE id=?
    `,[
        data.reminder_id,
        data.title,
        data.sort_order,
        id
    ]);

};

// ==============================
// Hapus checklist
// ==============================

const remove = async (id) => {

    await db.query(
        "DELETE FROM checklists WHERE id=?",
        [id]
    );

};

// ==============================
// Check / Uncheck
// ==============================

const checked = async (id,is_checked) => {

    await db.query(
        "UPDATE checklists SET is_checked=? WHERE id=?",
        [is_checked,id]
    );

};

module.exports = {

    getAll,
    getById,
    create,
    update,
    remove,
    checked

};