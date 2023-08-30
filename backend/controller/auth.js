const mongoose = require('mongoose');
const {getDigitalCode   } = require('node-verification-code');
const {User,validate} = require('../models/user');
const sms = require('../utils/sms')
const {Token} = require('../models/token');
const {Shareholder} = require('../models/shareholder');



//post request with params name and phone of owner
module.exports.signInOwner = async (req,res) => {
    const {error} = validate(req.body);
    if (error) return res.status(400).send(error.details[0].message);
    
    const user = new User({
        name: req.body.name,
        phone: req.body.phone
    });
    
    const smsVerificationCodeBuffer = getDigitalCode(4) // Will produce Buffer contains 4 random digits
    sms.sendVerificationCode(req.body.phone,smsVerificationCodeBuffer.toString());
    await Token({
        phone: req.body.phone,
        code: smsVerificationCodeBuffer.toString()
    }).save();

    const jwtToken = user.generateAuthToken();

    res.send({
        token: jwtToken,
    });
}

//post request with phone of owner
module.exports.loginOwner = async (req,res) => {
    const user = await User.findOne({phone: req.body.phone});
    if(!user) return res.status(404).send('User not found.');

    const smsVerificationCodeBuffer = getDigitalCode(4) // Will produce Buffer contains 4 random digits
    sms.sendVerificationCode(req.body.phone,smsVerificationCodeBuffer.toString());
    await Token({
        phone: req.body.phone,
        code: smsVerificationCodeBuffer.toString()
    }).save();

    const jwtToken = user.generateAuthToken();

    res.send({
        token: jwtToken
    });
}

//post request with phone of shareholder
module.exports.loginShareholder = async (req,res) => {
    const shareholder = await Shareholder.findOne({phone: req.body.phone});
    if(!shareholder) return res.status(404).send('Shareholder not found.');

    const smsVerificationCodeBuffer = getDigitalCode(4) // Will produce Buffer contains 4 random digits
    sms.sendVerificationCode(req.body.phone,smsVerificationCodeBuffer.toString());
    await Token({
        phone: req.body.phone,
        code: smsVerificationCodeBuffer.toString()
    }).save();

    const jwtToken = shareholder.generateAuthToken();

    res.send({token: jwtToken});
}

/*
post request with sms token
*/
module.exports.verifyOwnerSmsToken = async (req,res) => {
    const token = await Token.findOne({code: req.body.token});
    if(!token) return res.status(404).send('Invalid sms token.');
    if(token.phone !== req.user.phone) return res.status(401).send('Invalid sms token.');
    
    let user = await User.findById(req.user._id);
    if(!user){
        user = new User({
            _id: new mongoose.Types.ObjectId(req.user._id),
            name: req.user.name,
            phone: req.user.phone
        });
        await user.save();
    }

    res.send(user);
}

module.exports.verifyShareholderSmsToken = async (req,res) => {
    const token = await Token.findOne({code: req.body.token});
    if(!token) return res.status(404).send('Invalid sms token.');
    if(token.phone !== req.user.phone) return res.status(401).send('Invalid sms token.');
    
    const shareholder = await Shareholder.findById(req.user._id);
    res.send(shareholder);
}

