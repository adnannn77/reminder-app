const express = require("express");
const router = express.Router();

const repeatTypeController = require("../controllers/repeatTypeController");

// ==============================
// Repeat Type
// ==============================

router.get(
    "/repeat-types",
    repeatTypeController.getAll
);

router.get(
    "/repeat-types/:id",
    repeatTypeController.getById
);

router.post(
    "/repeat-types",
    repeatTypeController.create
);

router.put(
    "/repeat-types/:id",
    repeatTypeController.update
);

router.delete(
    "/repeat-types/:id",
    repeatTypeController.remove
);

module.exports = router;