const mongoose = require('mongoose');
const Joi = require('joi')
const jwt = require('jsonwebtoken');
const config = require('config');

const userSchema = new mongoose.Schema(
    {
        name:{
            type: String,
            required: true,
        },
        phone: {
            type: String,
            unique: true,
            required: true,
        },
    }
);

userSchema.methods.generateAuthToken = function(){
    const token = jwt.sign({
        _id: this._id,
        phone: this.phone,
        name: this.name,
        role: 'owner'
    },config.get('jwtPrivateKey'));
    return token;
}

const User = mongoose.model('User',userSchema);

function validateUser(user){
    const schema = Joi.object({
        name:Joi.string().required(),
        phone: Joi.string().required()
    });

    return schema.validate(user);
}

module.exports.User = User;
module.exports.validate = validateUser;