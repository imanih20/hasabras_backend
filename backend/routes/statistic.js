const express = require('express');
const auth = require('../middleware/auth');
const validateId = require('../middleware/validate_id');
const ownerAccess = require('../middleware/owner_access');
const marketState = require('../controller/market_statistic');
const monthStat = require('../controller/month_statistic');

const router = express.Router();

router.get('/market/:id/:year/:month',[auth,ownerAccess,validateId],marketState.getMarketStatistic);
router.get('/market/:id',[auth,ownerAccess,validateId],marketState.getAllMarketStatistics);
router.get('/shareholder/:id/:year/:month',[auth,validateId],monthStat.getStatistic);
router.get('/shareholder/:id',[auth,validateId],monthStat.getAllShareHolderStatistics);
router.put('/shareholder/:id',[auth,ownerAccess,validateId],monthStat.payProfit);

module.exports = router