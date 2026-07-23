const db = require("../config/database");

const getAll = async () => {
    const { rows } = await db.query(
        "SELECT * FROM labels ORDER BY id ASC"
    );
    return rows;
};

const getById = async (id) => {
    const { rows } = await db.query(
        "SELECT * FROM labels WHERE id=$1",
        [id]
    );
    return rows;
};

const create = async (name, color) => {
    await db.query(
        "INSERT INTO labels(name,color) VALUES($1,$2)",
        [name, color]
    );
};

const update = async (id, name, color) => {
    await db.query(
        "UPDATE labels SET name=$1, color=$2 WHERE id=$3",
        [name, color, id]
    );
};

const remove = async (id) => {
    await db.query(
        "DELETE FROM labels WHERE id=$1",
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