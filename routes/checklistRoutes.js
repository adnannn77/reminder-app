const express = require("express");

const router = express.Router();

const checklistController = require("../controllers/checklistController");

// ==============================
// Checklist
// ==============================

// Ambil semua checklist
router.get(
    "/checklists",
    checklistController.getAll
);

// Ambil checklist berdasarkan ID
router.get(
    "/checklists/:id",
    checklistController.getById
);

// Tambah checklist
router.post(
    "/checklists",
    checklistController.create
);

// Update checklist
router.put(
    "/checklists/:id",
    checklistController.update
);

// Hapus checklist
router.delete(
    "/checklists/:id",
    checklistController.remove
);

// Checklist selesai / belum selesai
router.patch(
    "/checklists/:id/check",
    checklistController.checked
);

module.exports = router;