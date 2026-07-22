const repeatTypeService = require("../services/repeatTypeService");
const response = require("../utils/response");

// ==============================
// GET Semua Repeat Type
// ==============================

const getAll = async (req, res) => {

    try {

        const data = await repeatTypeService.getAll();

        return response.success(
            res,
            "Data repeat type berhasil diambil",
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
// GET Repeat Type Berdasarkan ID
// ==============================

const getById = async (req, res) => {

    try {

        const data = await repeatTypeService.getById(req.params.id);

        if (data.length === 0) {

            return response.notFound(
                res,
                "Repeat type tidak ditemukan"
            );

        }

        return response.success(
            res,
            "Detail repeat type berhasil diambil",
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
// POST Tambah Repeat Type
// ==============================

const create = async (req, res) => {

    try {

        await repeatTypeService.create(req.body.name);

        return response.created(
            res,
            "Repeat type berhasil ditambahkan"
        );

    } catch (err) {

        return response.error(
            res,
            err.message
        );

    }

};

// ==============================
// PUT Update Repeat Type
// ==============================

const update = async (req, res) => {

    try {

        const data = await repeatTypeService.getById(req.params.id);

        if (data.length === 0) {

            return response.notFound(
                res,
                "Repeat type tidak ditemukan"
            );

        }

        await repeatTypeService.update(
            req.params.id,
            req.body.name
        );

        return response.success(
            res,
            "Repeat type berhasil diperbarui"
        );

    } catch (err) {

        return response.error(
            res,
            err.message
        );

    }

};

// ==============================
// DELETE Repeat Type
// ==============================

const remove = async (req, res) => {

    try {

        const data = await repeatTypeService.getById(req.params.id);

        if (data.length === 0) {

            return response.notFound(
                res,
                "Repeat type tidak ditemukan"
            );

        }

        await repeatTypeService.remove(req.params.id);

        return response.success(
            res,
            "Repeat type berhasil dihapus"
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
    getById,
    create,
    update,
    remove

};