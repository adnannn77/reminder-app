const db = require("../config/database");

const getAll = async () => {

    const [rows] = await db.query(
        "SELECT * FROM repeat_types ORDER BY id ASC"
    );

    return rows;

};

const getById = async (id) => {

    const [rows] = await db.query(
        "SELECT * FROM repeat_types WHERE id=?",
        [id]
    );

    return rows;

};

const create = async (name) => {

    await db.query(
        "INSERT INTO repeat_types(name) VALUES(?)",
        [name]
    );

};

const update = async (id, name) => {

    await db.query(
        "UPDATE repeat_types SET name=? WHERE id=?",
        [name, id]
    );

};

const remove = async (id) => {

    await db.query(
        "DELETE FROM repeat_types WHERE id=?",
        [id]
    );

};

module.exports = {
    getAll,
    getById,
    create,
    update,
    remove
};