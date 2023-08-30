const mongoose = require('mongoose');

const tokenSchema = new mongoose.Schema({
    phone:{
        type: String,
        required: true
    },
    code: {
        type: String,
        required: true
    },
    createdAt: {
        type: Date,
        default: Date.now(),
        expires: 130
    }
});

const Token = mongoose.model('Token',tokenSchema);

module.exports.Token = Token;