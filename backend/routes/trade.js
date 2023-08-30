const express = require('express');
const auth = require('../middleware/auth');
const validateId = require('../middleware/validate_id');
const ownerAccess = require('../middleware/owner_access');
const tradeController = require('../controller/trade');

const router = express.Router();

router.delete('/:id',[auth,ownerAccess,validateId],tradeController.deleteTrade);
router.post('/purchase/:id',[auth,ownerAccess,validateId],tradeController.purchaseProduct);
router.get('/purchase/',[auth],tradeController.getAllPurchases);
router.get('/purchase/:date',[auth],tradeController.getDayPurchases);
router.post('/sale/:id',[auth,ownerAccess,validateId],tradeController.saleProduct);
router.get('/sale/',[auth],tradeController.getAllSales);
router.get('/sale/:date',[auth],tradeController.getDaySales);

module.exports = router;