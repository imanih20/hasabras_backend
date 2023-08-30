const express = require('express');
const auth = require('../middleware/auth');
const ownerAccess = require('../middleware/owner_access');
const userController = require('../controller/user');

const router = express.Router();

router.get('/me',[auth,ownerAccess],userController.getUser);

module.exports = router;