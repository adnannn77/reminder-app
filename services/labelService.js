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

const getByDevice = async (deviceId) => {
    const { rows } = await db.query(
        "SELECT * FROM labels WHERE device_id=$1 ORDER BY id ASC",
        [deviceId]
    );
    return rows;
};

const create = async (name, color, deviceId) => {
    await db.query(
        "INSERT INTO labels(name, color, device_id) VALUES($1, $2, $3)",
        [name, color, deviceId || null]
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
    getByDevice,
    create,
    update,
    remove
};