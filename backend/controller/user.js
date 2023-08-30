const mongoose = require('mongoose');
const {User} = require('../models/user');

//get request
module.exports.getUser = async (req,res)=>{
    const user = await User.findById(req.user._id);
    if(!user) return res.status(404).send('User not found.');

    res.send(user);
}

