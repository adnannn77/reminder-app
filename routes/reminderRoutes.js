const express = require("express");

const router = express.Router();

const reminderController = require("../controllers/reminderController");

// ==============================
// Reminder
// ==============================

// Semua reminder (opsional)
router.get(
    "/reminders",
    reminderController.getAll
);

// Reminder berdasarkan user
router.get(
    "/reminders/user/:user_id",
    reminderController.getByUser
);

// Reminder berdasarkan ID
router.get(
    "/reminders/:id",
    reminderController.getById
);

// Tambah reminder
router.post(
    "/reminders",
    reminderController.create
);

// Update reminder
router.put(
    "/reminders/:id",
    reminderController.update
);

// Delete reminder
router.delete(
    "/reminders/:id",
    reminderController.remove
);

// Complete reminder
router.put(
    "/reminders/:id/complete",
    reminderController.complete
);

// Archive reminder
router.patch(
    "/reminders/:id/archive",
    reminderController.archive
);

module.exports = router;