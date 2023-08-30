const mongoose = require('mongoose');
const {Product,validate} = require('../models/product');

// module.exports.editProduct = async (req,res) => {
//     const {error} = validate(req.body);
//     if (error) return res.status(400).send(error.details[0].message);

//     const product = await Product.findByIdAndUpdate(req.params.id,{
//         title: req.body.title,
//         quantity: req.body.quantity,
//         price: req.body.price,
//         profit: req.body.profit,
//     },{new: true});
//     if(!product) return res.status(404).send('Product not found.');

//     res.send(product);
// }

module.exports.deleteProduct = async (req,res) => {
    const product = await Product.findByIdAndRemove(req.params.id);
    if (!product) return res.status(404).send('Product not found.');
    res.send(product);
}

module.exports.getMarketProducts = async( req,res) => {
    const products = await Product.find({
        market: req.params.id
    });
    res.send(products);
}
module.exports.getOwnerProducts = async (req,res) => {
    const products = await Product.find({
        owner: req.params.id
    });
    res.send(products);
}