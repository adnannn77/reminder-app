const checklistService = require("../services/checklistService");

// ==============================
// GET Semua Checklist
// ==============================

const getAll = async (req, res) => {

    try {

        const data = await checklistService.getAll();

        res.json({
            status: true,
            message: "Data checklist berhasil diambil",
            data
        });

    } catch (err) {

        res.status(500).json({
            status: false,
            message: err.message
        });

    }

};

// ==============================
// GET Checklist Berdasarkan ID
// ==============================

const getById = async (req, res) => {

    try {

        const data = await checklistService.getById(req.params.id);

        if (data.length === 0) {

            return res.status(404).json({
                status: false,
                message: "Checklist tidak ditemukan"
            });

        }

        res.json({
            status: true,
            message: "Detail checklist berhasil diambil",
            data: data[0]
        });

    } catch (err) {

        res.status(500).json({
            status: false,
            message: err.message
        });

    }

};

// ==============================
// POST Tambah Checklist
// ==============================

const create = async (req, res) => {

    try {

        await checklistService.create(req.body);

        res.status(201).json({
            status: true,
            message: "Checklist berhasil ditambahkan"
        });

    } catch (err) {

        res.status(500).json({
            status: false,
            message: err.message
        });

    }

};

// ==============================
// PUT Update Checklist
// ==============================

const update = async (req, res) => {

    try {

        const data = await checklistService.getById(req.params.id);

        if (data.length === 0) {

            return res.status(404).json({
                status: false,
                message: "Checklist tidak ditemukan"
            });

        }

        await checklistService.update(req.params.id, req.body);

        res.json({
            status: true,
            message: "Checklist berhasil diperbarui"
        });

    } catch (err) {

        res.status(500).json({
            status: false,
            message: err.message
        });

    }

};

// ==============================
// DELETE Checklist
// ==============================

const remove = async (req, res) => {

    try {

        const data = await checklistService.getById(req.params.id);

        if (data.length === 0) {

            return res.status(404).json({
                status: false,
                message: "Checklist tidak ditemukan"
            });

        }

        await checklistService.remove(req.params.id);

        res.json({
            status: true,
            message: "Checklist berhasil dihapus"
        });

    } catch (err) {

        res.status(500).json({
            status: false,
            message: err.message
        });

    }

};

// ==============================
// PATCH Check / Uncheck
// ==============================

const checked = async (req, res) => {

    try {

        const data = await checklistService.getById(req.params.id);

        if (data.length === 0) {

            return res.status(404).json({
                status: false,
                message: "Checklist tidak ditemukan"
            });

        }

        await checklistService.checked(
            req.params.id,
            req.body.is_checked
        );

        res.json({
            status: true,
            message: "Status checklist berhasil diperbarui"
        });

    } catch (err) {

        res.status(500).json({
            status: false,
            message: err.message
        });

    }

};

module.exports = {

    getAll,
    getById,
    create,
    update,
    remove,
    checked

};