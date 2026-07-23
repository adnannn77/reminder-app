const db = require("../config/database");

const getAll = async () => {
    const { rows } = await db.query(
        "SELECT * FROM repeat_types ORDER BY id ASC"
    );
    return rows;
};

const getById = async (id) => {
    const { rows } = await db.query(
        "SELECT * FROM repeat_types WHERE id=$1",
        [id]
    );
    return rows;
};

const create = async (name) => {
    await db.query(
        "INSERT INTO repeat_types(name) VALUES($1)",
        [name]
    );
};

const update = async (id, name) => {
    await db.query(
        "UPDATE repeat_types SET name=$1 WHERE id=$2",
        [name, id]
    );
};

const remove = async (id) => {
    await db.query(
        "DELETE FROM repeat_types WHERE id=$1",
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