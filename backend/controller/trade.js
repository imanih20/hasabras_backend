const mongoose = require('mongoose');
const {getDate} = require('../utils/date');
const {Trade,validatePurchase,validateSale} = require('../models/trade');
const {MarketStatistic} = require('../models/market_statistic');
const {MonthStatistic} = require('../models/month_statistic');
const { Product } = require('../models/product');


//params id => product_id
module.exports.purchaseProduct = async (req,res) => {
    const {error} = validatePurchase(req.body);
    if (error) return res.status(400).send(error.details[0].message);

    const date = getDate(req.body.date);
    if(!date) return res.status(400).send('date is invalid.');

    let product = await Product.findById(req.params.id)
    if(!product) {
        product = new Product({
            title: req.body.title,
            quantity: 0,
            price: 0,
            profit: req.body.salePrice - req.body.totalPrice/req.body.quantity,
            unit: req.body.unit,
            market: new mongoose.Types.ObjectId(req.body.marketId),
            owner: new mongoose.Types.ObjectId(req.body.ownerId)
        });
    }
    product.quantity += req.body.quantity;
    product.price = req.body.salePrice;
    await product.save();
    const trade = new Trade({
        product: product._id,
        type: 'p',
        quantity: req.body.quantity,
        totalPrice: req.body.totalPrice,
        date: req.body.date
    });
    await trade.save();
    const marketStatQuery = {
        market: product.market,
        month: date.month,
        year: date.year
    }
    let marketStatistic = await MarketStatistic.findOne(marketStatQuery);
    if(!marketStatistic){
        marketStatistic = new MarketStatistic(marketStatQuery);
    }
    if(product.owner){
        const monthStatQuery = {
            user: product.owner,
            month: date.month,
            year: date.year
        }
        let monthStatistic = await MonthStatistic.findOne(monthStatQuery);
        
        if(!monthStatistic) {
            monthStatistic = new MonthStatistic(monthStatQuery);
        }
        monthStatistic.totalPurchase += req.body.totalPrice;
        await monthStatistic.save();
    }else {
        marketStatistic.monthOwnerPurchase += req.body.totalPrice;
    }

    marketStatistic.monthPurchase += req.body.totalPrice;
    await marketStatistic.save();
    res.send(trade);
}

//id params => product_id
module.exports.saleProduct = async (req,res) =>{
    const {error} = validateSale(req.body);
    if (error) return res.status(400).send(error.details[0].message);

    const date = getDate(req.body.date);
    if(!date) return res.status(400).send('date is invalid.');

    const product = await Product.findById(req.params.id);
    if(!product) return res.status(404).send('Product not found.');
    product.quantity -= req.body.quantity;

    await product.save();

    const trade = new Trade({
        product: product._id,
        quantity: req.body.quantity,
        type: 's',
        date: req.body.date,
        totalPrice: req.body.totalPrice
    });
    await trade.save();


    const totalProfit = product.profit * req.body.quantity;

    const marketStatQuery = {
        market: product.market,
        month: date.month,
        year: date.year
    }
    let marketStatistic = await MarketStatistic.findOne(marketStatQuery);
    if(!marketStatistic){
        marketStatistic = new MarketStatistic(marketStatQuery);
    }
    if(product.owner){
        const monthStatQuery = {
            user: product.owner,
            month: date.month,
            year: date.year
        }
        await product.populate('owner');
        let monthStatistic = await MonthStatistic.findOne(monthStatQuery);
        
        if(!monthStatistic) {
            monthStatistic = new MonthStatistic(monthStatQuery);
        }
        monthStatistic.totalSale += req.body.totalPrice;
        monthStatistic.profit += totalProfit*product.owner.share;
        await monthStatistic.save();
        marketStatistic.monthOwnerProfit += totalProfit - totalProfit*product.owner.share;
    }else {
        marketStatistic.monthOwnerSale += req.body.totalPrice;
        marketStatistic.monthOwnerProfit += totalProfit;
    }

    marketStatistic.monthSale += req.body.totalPrice;
    marketStatistic.monthProfit += totalProfit;
    await marketStatistic.save();
    res.send(trade);
}


module.exports.getAllPurchases = async (req,res) => {
    const trades = await Trade.filterFindOutput({
        type: 'p',
    },req.user);

    res.send(trades);
}

module.exports.getAllSales = async (req,res) => {
    const trades = await Trade.filterFindOutput(
        {type: 's'},req.user
    );
    // let userTrades;
    // if(req.user.role === 'owner'){
    //     userTrades = await trades.where(
    //         function(){
    //             return this.product.market.owner.toString() === req.user._id.toString();
    //         }
    //     );
    //     console.log(userTrades);
    // } else {
        
    //     userTrades = await trades.where(function(){
    //         return this.product.owner.toString() === req.user._id.toString();
    //     });
    // }

    res.send(trades);
}

module.exports.getDayPurchases = async (req,res) => {
    const trades = await Trade.filterFindOutput({
        type: 'p',
        date: req.params.date
    },req.user)

    res.send(trades);
}

module.exports.getDaySales = async (req,res) => {
    const trades = await Trade.filterFindOutput({
        type: 's',
        date: req.params.date
    },req.user);
    res.send(trades);
}

//delete
module.exports.deleteTrade = async (req,res) => {
    const trade = await Trade.findByIdAndRemove(req.params.id);
    if(!trade) return res.status(404).send('Trade not found.');
    const product = await Product.findById(trade.product);
    if(!product) return res.status(404).send('Product not found.');
    product.quantity -= trade.quantity;
    await product.save();
    const date = getDate(trade.date);
    const totalPrice = trade.totalPrice;
    const marketStat = await MarketStatistic.findOne({
        market: product.market,
        year: date.year,
        month: date.month
    });
    if (!marketStat) return res.status(404).send('MarketStatistic not found.')
    if(product.owner){
        const ms = await MonthStatistic.findOne({
            user: product.owner,
            year: date.year,
            month: date.month
        });
        if(!ms) return res.status(404).send('MonthStatistic not found.')

        if(trade.type === 's'){
            ms.totalSale -= totalPrice;
            ms.profit -= product.profit*trade.quantity;
        }else{
            ms.totalPurchase -= totalPrice;
        }
        await ms.save();
    } else{
        if(trade.type === 's'){
            console.log(marketStat)
            marketStat.monthOwnerSale -= totalPrice;
            marketStat.monthOwnerProfit -= product.profit*trade.quantity;
        }else{
            marketStat.monthOwnerProfit -= totalPrice;
        }
    }
    if(trade.type === 's'){
        marketStat.monthSale -= totalPrice;
        marketStat.monthProfit -= product.profit*trade.quantity;
    }else{
        marketStat.monthPurchase -= totalPrice;
    }
    await marketStat.save();
    res.send(trade);  
}
