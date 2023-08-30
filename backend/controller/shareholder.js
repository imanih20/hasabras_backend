const mongoose = require('mongoose');
const {Shareholder,validate} = require('../models/shareholder');

module.exports.addShareholder = async (req,res) => {
    const {error} = validate(req.body);
    if(error) return res.status(400).send(error.details[0].message);

    const shareholder = new Shareholder({
        name: req.body.name,
        share: req.body.share,
        phone: req.body.phone,
        market: new mongoose.Types.ObjectId(req.body.marketId)
    });

    await shareholder.save();

    res.send(shareholder);
}

//id param
module.exports.deleteShareholder = async (req,res) => {
    const shareholder = await Shareholder.findByIdAndRemove(req.params.id);
    if(!shareholder) return res.status(404).send('Shareholder not found.');
    res.send(shareholder);
}

module.exports.editShareholder = async (req,res) => {
    const {error} = validate(req.body);
    if(error) return res.status(400).send(error.details[0].message);

    const shareholder = await Shareholder.findByIdAndUpdate(req.params.id,{
        name: req.body.name,
        phone: req.body.phone,
        share: req.body.share
    },{new: true});
    if (!shareholder) return res.status(404).send('Shareholder not found.')
    res.send(shareholder);
}

module.exports.getMarketShareholders = async (req,res) => {
    const shareholders = await Shareholder.find({
        market: req.params.id,
    });
    res.send(shareholders);
}

