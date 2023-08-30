const mongoose = require('mongoose');
const Joi = require('joi');
const jwt = require('jsonwebtoken');
const config = require('config');


const shareholderSchema = new mongoose.Schema({
    name:{
        type: String,
        required: true,
    },
    phone: {
        type: String,
        unique: true,
        required: true,
    },
    share:{
        type: Number,
        min: 0,
        max: 0.8,
        required: true
    },
    market: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Market'
    }
});

shareholderSchema.methods.generateAuthToken = function(){
    const token = jwt.sign({
        _id: this._id,
        phone: this.phone,
        name: this.name,
        role: 'shareholder'
    },config.get('jwtPrivateKey'));
    return token;
}


const Shareholder = mongoose.model('Shareholder',shareholderSchema);

function validateShareholder(shareholder){
    const schema = Joi.object({
        name: Joi.string().required(),
        phone: Joi.string().required(),
        share: Joi.number().min(0).max(1).required(),
        marketId: Joi.objectId()
    });

    return schema.validate(shareholder);
}

module.exports.Shareholder = Shareholder;
module.exports.validate = validateShareholder;