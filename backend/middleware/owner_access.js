

module.exports = (req,res,next) =>{
    if(req.user.role === 'owner') {
        next();
    } else {
        res.status(403).send('Access denied.');
    }
}