const request = require('supertest');
const {User} = require('../../models/user');
const { default: mongoose } = require('mongoose');

describe('/api/user',()=>{
    let server;
    beforeEach(()=>{
        server = require('../../index');
    });
    afterEach(async ()=>{
        await server.close();
        await User.deleteMany({});
    });

    describe('GET /api/user/me',()=>{
        let user;
        let token;
        const exec = function (){
            return request(server)
                .get('/api/user/me')
                .set('x-auth-token',token);
        }
        beforeEach(async()=>{
            user = new User({name:'nemo',phone: '09374666666'});
            await user.save();
            token = user.generateAuthToken();
        })
        it('should return 404 if user not found.',async ()=>{
            user._id = new mongoose.Types.ObjectId();
            token = user.generateAuthToken();
            const res = await exec();
            expect(res.status).toBe(404);
        });
        it('should return user info',async()=>{
            const res = await exec();
            expect(res.status).toBe(200);
            expect(res.body).toHaveProperty('name','nemo');
        });
    });
});