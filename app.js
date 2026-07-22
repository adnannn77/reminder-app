require("dotenv").config();

const express = require("express");
const cors = require("cors");

const app = express();

// ==============================
// Middleware
// ==============================

app.use(cors());
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// ==============================
// Database
// ==============================

require("./config/database");

// ==============================
// Routes
// ==============================

const authRoutes = require("./routes/authRoutes");
const reminderRoutes = require("./routes/reminderRoutes");
const checklistRoutes = require("./routes/checklistRoutes");
const labelRoutes = require("./routes/labelRoutes");
const repeatTypeRoutes = require("./routes/repeatTypeRoutes");
const externalRoutes = require("./routes/externalRoutes");

// ==============================
// API
// ==============================

app.use("/api", authRoutes);
app.use("/api", reminderRoutes);
app.use("/api", checklistRoutes);
app.use("/api", labelRoutes);
app.use("/api", repeatTypeRoutes);
app.use("/api", externalRoutes);

// ==============================
// Root
// ==============================

app.get("/", (req, res) => {

    res.json({

        status: true,
        message: "Reminder App REST API Berjalan 🚀"

    });

});

// ==============================
// 404
// ==============================

app.use((req, res) => {

    res.status(404).json({

        status: false,
        message: "Endpoint tidak ditemukan"

    });

});

// ==============================
// Run Server
// ==============================

const PORT = process.env.PORT || 3000;

app.listen(PORT, () => {

    console.log(`Server running di http://localhost:${PORT}`);

});