const express = require('express');
const auth = require('../middleware/auth');
const validateId = require('../middleware/validate_id');
const ownerAccess = require('../middleware/owner_access');
const productController = require('../controller/product');

const router = express.Router();

router.delete('/:id',[auth,ownerAccess,validateId],productController.deleteProduct);
// router.put('/:id',[auth,ownerAccess,validateId],productController.editProduct);
router.get('/market/:id',[auth,ownerAccess,validateId],productController.getMarketProducts);
router.get('/shareholder/:id',[auth,validateId],productController.getOwnerProducts);

module.exports = router;