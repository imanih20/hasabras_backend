const express = require('express');
const auth = require('../middleware/auth');
const validateId = require('../middleware/validate_id');
const ownerAccess = require('../middleware/owner_access');
const shareholderController = require('../controller/shareholder');

const router = express.Router();

router.post('/',[auth,ownerAccess],shareholderController.addShareholder);
router.delete('/:id',[auth,ownerAccess,validateId],shareholderController.deleteShareholder);
router.put('/:id',[auth,ownerAccess,validateId],shareholderController.editShareholder);
router.get('/market/:id',[auth,ownerAccess,validateId],shareholderController.getMarketShareholders);

module.exports = router;