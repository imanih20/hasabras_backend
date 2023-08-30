const Joi = require('joi');
const {MonthStatistic} = require('../models/month_statistic');

// id params => shareholder id
module.exports.getAllShareHolderStatistics = async (req,res) => {
    const statistics = await MonthStatistic.find({
        user: req.params.id
    });
    res.send(statistics);
}

//id params => shareholder id
module.exports.getStatistic = async (req,res) => {
    const statistic = await MonthStatistic.findOne({
        user: req.params.id,
        month: req.params.month,
        year: req.params.year
    });
    if (!statistic) return res.status(404).send('Statistic not found.');
    res.send(statistic);
}

//put request , id params => statistic id
module.exports.payProfit = async (req,res) => {
    const {error} = Joi.object({isPaid: Joi.boolean().required()}).validate(req.body);
    if(error) return res.status(400).send(error.details[0].message);
    const statistic = await MonthStatistic.findByIdAndUpdate(req.params.id,{
        isPaid: req.body.isPaid
    },{new: true});
    if (!statistic) return res.status(404).send('Statistic not found.')
    res.send(statistic);
}