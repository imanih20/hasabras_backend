const {Market,validate} = require('../models/market');
const mongoose = require('mongoose');

//post
module.exports.addMarket = async (req,res)=>{
    const {error} = validate(req.body);
    if (error) return res.status(400).send(error.details[0].message);
    const market = new Market({
        name : req.body.name,
        owner: new mongoose.Types.ObjectId(req.user._id)
    });

    await market.save();
    res.send(market);
}

//id param
module.exports.deleteMarket = async (req,res)=>{
    const market = await Market.findByIdAndRemove(req.params.id);
    if (!market) return res.status(404).send('market not found.');
    res.send(market);
}

module.exports.updateMarket = async (req,res)=>{
    const {error} = validate(req.body);
    if (error) return res.status(400).send(error.details[0].message);

    const market =await Market.findByIdAndUpdate(req.params.id,{name: req.body.name},{new:true});
    if(!market) return res.status(404).send('market not found.');
    
    res.send(market);

}

module.exports.getMarket = async (req,res)=>{
    const market = await Market.findOne({
        owner: req.user._id
    });
    if(!market) return res.status(404).send('Market not found.');
    res.send(market)
}