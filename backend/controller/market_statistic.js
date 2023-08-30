const {MarketStatistic} = require('../models/market_statistic');
const mongoose = require('mongoose');

//id params => market id
module.exports.getMarketStatistic = async (req,res) => {
    const marketStatistic = await MarketStatistic.findOne({
        market: req.params.id,
        month: req.params.month,
        year: req.params.year
    });
    if (!marketStatistic) return res.status(404).send('Statistic not found.')
    res.send(marketStatistic);
}

//id params => market id
module.exports.getAllMarketStatistics = async (req,res) => {
    const statistics = await MarketStatistic.find({
        market: req.params.id
    });
    res.send(statistics);
}

