const express = require("express");
const router = express.Router();

const labelController = require("../controllers/labelController");

// ==============================
// Label
// ==============================

router.get(
    "/labels",
    labelController.getAll
);

router.get(
    "/labels/:id",
    labelController.getById
);

router.post(
    "/labels",
    labelController.create
);

router.put(
    "/labels/:id",
    labelController.update
);

router.delete(
    "/labels/:id",
    labelController.remove
);

module.exports = router;