const mongoose = require('mongoose');
const Joi = require('joi');

const marketSchema = new mongoose.Schema({
    name: {
        type: String,
        required: true
    },
    owner: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'User'
    }
});

const Market = mongoose.model('Market',marketSchema);

function validateMarket(market){
    const schema = Joi.object({
        name: Joi.string().required(),
    });
    return schema.validate(market);
}

module.exports.Market = Market;
module.exports.validate = validateMarket;