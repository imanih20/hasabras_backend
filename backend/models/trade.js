const mongoose = require('mongoose');
const Joi = require('joi');
const _ = require('lodash');

const tradeSchema = new mongoose.Schema({
    product: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Product',
    },
    type: {
        type: String,
        enum: ['p','s'],//p stands for purchase & s stands for sale
        required: true,
    },
    quantity: {
        type: Number,
        required: true,
        min: 0
    },
    totalPrice: {
        type: Number,
        min: 0,
        required: true,
    },
    date: {
        type: String,
        required: true,
    }
});

tradeSchema.statics.filterFindOutput = async function(find_query,user){
    const queries = await this.find(find_query).populate({
        path: 'product',
        populate: [
            {
                path: 'market',
            },
    ]
	});
    const trades = [];
    queries.forEach(function(trade){
        const realTrade = {
            product: trade.product._id,
            type: trade.type,
            quantity: trade.quantity,
            totalPrice: trade.totalPrice,
            date: trade.date
        };
        if(user.role === 'owner'){
            if (trade.product.market.owner.toString() === user._id.toString()){
                trades.push(realTrade);
            }
        }else {
             if(trade.product.owner.toString() === user._id.toString()){
                trades.push(realTrade);
             }
        }
    });
    return trades;
}
const Trade = mongoose.model('Sale',tradeSchema);



function validatePurchase(purchase){
    const schema = Joi.object({
        title: Joi.string().required(),
        quantity: Joi.number().min(0).required(),
        totalPrice: Joi.number().min(0).required(),
        unit: Joi.string(),
        date: Joi.string().required(),
        marketId: Joi.objectId().required(),
        ownerId: Joi.objectId().required(),
        salePrice: Joi.number().min(0).required(),
    });
    return schema.validate(purchase);
}

function validateSale(sale) {
    const schema = Joi.object({
        quantity: Joi.number().min(0).required(),
        totalPrice: Joi.number().min(0).required(),
        date: Joi.string().required()
    });
    return schema.validate(sale);
}

module.exports.Trade = Trade;
module.exports.validatePurchase = validatePurchase;
module.exports.validateSale = validateSale;