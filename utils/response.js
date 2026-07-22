const success = (res, message, data = null) => {

    return res.status(200).json({
        status: true,
        message,
        data
    });

};

const created = (res, message, data = null) => {

    return res.status(201).json({
        status: true,
        message,
        data
    });

};

const badRequest = (res, message) => {

    return res.status(400).json({
        status: false,
        message
    });

};

const unauthorized = (res, message) => {

    return res.status(401).json({
        status: false,
        message
    });

};

const notFound = (res, message) => {

    return res.status(404).json({
        status: false,
        message
    });

};

const error = (res, message) => {

    return res.status(500).json({
        status: false,
        message
    });

};

module.exports = {

    success,
    created,
    badRequest,
    unauthorized,
    notFound,
    error

};