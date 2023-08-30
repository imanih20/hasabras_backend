const mongoose = require('mongoose');

const statisticSchema = new mongoose.Schema({
    user:{
        type: mongoose.Schema.Types.ObjectId,
        ref: 'User',
    },
    totalSale:{
        type: Number,
        min: 0,
        default: 0
    },
    totalPurchase: {
        type: Number,
        min: 0,
        default: 0,
    },
    profit: {
        type: Number,
        min: 0,
        default: 0,
    },
    isPaid:{
        type: Boolean,
        default: false
    },
    month: {
        type: Number,
        min: 1,
        max: 12,
        required: true
    },
    year: {
        type: Number,
        min: 1390,
        required: true,
    }
});

const MonthStatistic = mongoose.model('Statistic',statisticSchema);


module.exports.MonthStatistic = MonthStatistic;
