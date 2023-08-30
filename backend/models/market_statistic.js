const mongoose = require('mongoose');

const marketStatSchema = new mongoose.Schema({
    market: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Market'
    },
    monthSale: {
        type: Number,
        min: 0,
        default: 0
    },
    monthPurchase: {
        type: Number,
        min: 0,
        default: 0
    },
    monthProfit: {
        type: Number,
        min: 0,
        default: 0
    },
    monthOwnerSale: {
        type: Number,
        min: 0,
        default: 0
    },
    monthOwnerPurchase: {
        type: Number,
        min: 0,
        default: 0
    },
    monthOwnerProfit: {
        type: Number,
        min: 0,
        default: 0
    },
    month: {
        type: Number,
        min: 0,
        max: 12,
        required: true,
    },
    year: {
        type: Number,
        min: 1390,
        required: true,
    }
});

const MarketStatistic = mongoose.model('MarketStatistics',marketStatSchema);

module.exports.MarketStatistic = MarketStatistic;