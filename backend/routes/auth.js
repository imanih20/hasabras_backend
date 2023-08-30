const express = require('express');
const authController = require('../controller/auth');
const auth = require('../middleware/auth');
const ownerAccess = require('../middleware/owner_access');
const router = express.Router();

router.post('/signOwner',authController.signInOwner);
router.post('/loginOwner',authController.loginOwner);
router.post('/loginShareholder',authController.loginShareholder);
router.post('/verifyOwner',[auth,ownerAccess],authController.verifyOwnerSmsToken);
router.post('/verifyShareholder',auth,authController.verifyShareholderSmsToken);

module.exports = router;