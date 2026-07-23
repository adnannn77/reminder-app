const labelService = require("../services/labelService");
const response = require("../utils/response");

// ==============================
// GET Semua Label
// ==============================

const getAll = async (req, res) => {

    try {

        const data = await labelService.getAll();

        return response.success(
            res,
            "Data label berhasil diambil",
            data
        );

    } catch (err) {

        return response.error(
            res,
            err.message
        );

    }

};

// ==============================
// GET Label Berdasarkan Device
// ==============================

const getByDevice = async (req, res) => {
    try {
        const { device_id } = req.params;
        const data = await labelService.getByDevice(device_id);
        return response.success(res, "Label device berhasil diambil", data);
    } catch (err) {
        return response.error(res, err.message);
    }
};

// ==============================
// GET Label Berdasarkan ID
// ==============================

const getById = async (req, res) => {

    try {

        const data = await labelService.getById(req.params.id);

        if (data.length === 0) {

            return response.notFound(
                res,
                "Label tidak ditemukan"
            );

        }

        return response.success(
            res,
            "Detail label berhasil diambil",
            data[0]
        );

    } catch (err) {

        return response.error(
            res,
            err.message
        );

    }

};

// ==============================
// POST Tambah Label
// ==============================

const create = async (req, res) => {

    try {

        await labelService.create(
            req.body.name,
            req.body.color,
            req.body.device_id
        );

        return response.created(
            res,
            "Label berhasil ditambahkan"
        );

    } catch (err) {

        return response.error(
            res,
            err.message
        );

    }

};

// ==============================
// PUT Update Label
// ==============================

const update = async (req, res) => {

    try {

        const data = await labelService.getById(req.params.id);

        if (data.length === 0) {

            return response.notFound(
                res,
                "Label tidak ditemukan"
            );

        }

        await labelService.update(
            req.params.id,
            req.body.name,
            req.body.color
        );

        return response.success(
            res,
            "Label berhasil diperbarui"
        );

    } catch (err) {

        return response.error(
            res,
            err.message
        );

    }

};

// ==============================
// DELETE Label
// ==============================

const remove = async (req, res) => {

    try {

        const data = await labelService.getById(req.params.id);

        if (data.length === 0) {

            return response.notFound(
                res,
                "Label tidak ditemukan"
            );

        }

        await labelService.remove(req.params.id);

        return response.success(
            res,
            "Label berhasil dihapus"
        );

    } catch (err) {

        return response.error(
            res,
            err.message
        );

    }

};

module.exports = {

    getAll,
    getByDevice,
    getById,
    create,
    update,
    remove

};