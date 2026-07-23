const reminderService = require("../services/reminderService");

// ==============================
// Ambil semua reminder
// ==============================

const getAll = async (req, res) => {

    try {

        const reminders =
            await reminderService.getAll();

        return res.json({

            status: true,

            message: "Data reminder berhasil diambil",

            data: reminders

        });

    } catch (error) {

        console.log(error);

        return res.status(500).json({

            status: false,

            message: error.message

        });

    }

};

// ==============================
// Ambil reminder berdasarkan user
// ==============================

const getByUser = async (req, res) => {

    try {

        const { user_id } = req.params;

        const reminders =
            await reminderService.getByUser(user_id);

        return res.json({

            status: true,

            message: "Reminder user berhasil diambil",

            data: reminders

        });

    } catch (error) {

        console.log(error);

        return res.status(500).json({

            status: false,

            message: error.message

        });

    }

};

// ==============================
// Ambil reminder berdasarkan device
// ==============================

const getByDevice = async (req, res) => {
    try {
        const { device_id } = req.params;
        const reminders = await reminderService.getByDevice(device_id);
        return res.json({
            status: true,
            message: "Reminder device berhasil diambil",
            data: reminders
        });
    } catch (error) {
        console.log(error);
        return res.status(500).json({
            status: false,
            message: error.message
        });
    }
};

// ==============================
// Ambil reminder berdasarkan ID
// ==============================

const getById = async (req, res) => {

    try {

        const { id } = req.params;

        const reminder =
            await reminderService.getById(id);

        if (reminder.length === 0) {

            return res.status(404).json({

                status: false,

                message: "Reminder tidak ditemukan"

            });

        }

        return res.json({

            status: true,

            data: reminder[0]

        });

    } catch (error) {

        console.log(error);

        return res.status(500).json({

            status: false,

            message: error.message

        });

    }

};

// ==============================
// Tambah reminder
// ==============================

const create = async (req, res) => {

    try {

        console.log("======================================");
        console.log("BODY DARI ANDROID:");
        console.log(req.body);
        console.log("======================================");

        await reminderService.create(req.body);

        return res.status(201).json({

            status: true,

            message: "Reminder berhasil ditambahkan"

        });

    } catch (error) {

        console.log("======================================");
        console.log("ERROR CREATE REMINDER");
        console.log(error);
        console.log("======================================");

        return res.status(500).json({

            status: false,

            message: error.message

        });

    }

};

// ==============================
// Update reminder
// ==============================

const update = async (req, res) => {

    try {

        const { id } = req.params;

        const reminder =
            await reminderService.getById(id);

        if (reminder.length === 0) {

            return res.status(404).json({

                status: false,

                message: "Reminder tidak ditemukan"

            });

        }

        await reminderService.update(id, req.body);

        return res.json({

            status: true,

            message: "Reminder berhasil diubah"

        });

    } catch (error) {

        console.log(error);

        return res.status(500).json({

            status: false,

            message: error.message

        });

    }

};

// ==============================
// Hapus reminder
// ==============================

const remove = async (req, res) => {

    try {

        const { id } = req.params;

        const reminder =
            await reminderService.getById(id);

        if (reminder.length === 0) {

            return res.status(404).json({

                status: false,

                message: "Reminder tidak ditemukan"

            });

        }

        await reminderService.remove(id);

        return res.json({

            status: true,

            message: "Reminder berhasil dihapus"

        });

    } catch (error) {

        console.log(error);

        return res.status(500).json({

            status: false,

            message: error.message

        });

    }

};

// ==============================
// Tandai selesai
// ==============================

const complete = async (req, res) => {

    try {

        const { id } = req.params;

        await reminderService.complete(id);

        return res.json({

            status: true,

            message: "Reminder berhasil diselesaikan"

        });

    } catch (error) {

        console.log(error);

        return res.status(500).json({

            status: false,

            message: error.message

        });

    }

};

// ==============================
// Arsipkan reminder
// ==============================

const archive = async (req, res) => {

    try {

        const { id } = req.params;

        await reminderService.archive(id);

        return res.json({

            status: true,

            message: "Reminder berhasil diarsipkan"

        });

    } catch (error) {

        console.log(error);

        return res.status(500).json({

            status: false,

            message: error.message

        });

    }

};

module.exports = {

    getAll,
    getByUser,
    getByDevice,
    getById,
    create,
    update,
    remove,
    complete,
    archive

};