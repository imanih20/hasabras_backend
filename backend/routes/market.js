const express = require('express');
const auth = require('../middleware/auth');
const validateId = require('../middleware/validate_id');
const ownerAccess = require('../middleware/owner_access');
const marketController = require('../controller/market');

const router = express.Router();

router.post('/',[auth,ownerAccess],marketController.addMarket);
router.get('/',auth,marketController.getMarket);
router.delete('/:id',[auth,ownerAccess,validateId],marketController.deleteMarket);
router.put('/:id',[auth,ownerAccess,validateId],marketController.updateMarket);

module.exports = router;