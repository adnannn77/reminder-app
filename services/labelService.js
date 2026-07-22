const db = require("../config/database");

const getAll = async () => {

    const [rows] = await db.query(
        "SELECT * FROM labels ORDER BY id ASC"
    );

    return rows;

};

const getById = async (id) => {

    const [rows] = await db.query(
        "SELECT * FROM labels WHERE id=?",
        [id]
    );

    return rows;

};

const create = async (name, color) => {

    await db.query(
        "INSERT INTO labels(name,color) VALUES(?,?)",
        [name, color]
    );

};

const update = async (id, name, color) => {

    await db.query(
        "UPDATE labels SET name=?, color=? WHERE id=?",
        [name, color, id]
    );

};

const remove = async (id) => {

    await db.query(
        "DELETE FROM labels WHERE id=?",
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