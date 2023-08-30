const mongoose = require('mongoose');
const Joi = require('joi');

const productSchema = new mongoose.Schema({
    title: {
        type: String,
        required: true,
    },
    quantity: {
        type: Number,
        min: 0,
        required: true
    },
    price: {
        type: Number,
        min: 0,
        required: true
    },
    profit: {
        type: Number,
        min: 0,
        required: true
    },
    unit: {
        type: String,
        enum: ['n','w'],
        default:'n'
    },
    owner: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Shareholder',
        required: false
    },  
    market: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Market'
    }
});

const Product = mongoose.model('Product',productSchema);

// function validateProduct(product){
//     const schema = Joi.object({
//         title: Joi.string().required(),
//         quantity: Joi.number().min(0).required(),
//         price: Joi.number().min(0).required(),
//         profit: Joi.number().min(0).required(),
//         ownerId: Joi.objectId().required(),
//         marketId: Joi.objectId().required()
//     });

//     return schema.validate(product);
// }

module.exports.Product = Product;
// module.exports.validate = validateProduct;