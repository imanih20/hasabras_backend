const request = require('supertest');
const {User} = require('../../models/user');
const {Shareholder} = require('../../models/shareholder');
const {Token} = require('../../models/token');

let server
describe('auth middleware',()=>{
    let user;
    let token;

    beforeEach(async ()=>{
        server = require('../../index');
        user = new User({
            name: 'mohyeddin',
            phone: '09374691756'
        });
        token = user.generateAuthToken();
    });
    afterEach(async ()=>{
        await server.close();
    });

    const exec = ()=> request(server).get('/api/user/me').set('x-auth-token',token);

    it('should return 401 if user not authenticated.',async ()=>{
        token = '';
        const res = await exec();
        expect(res.status).toBe(401);
    });
    it('should return 400 if invalid token was provided.', async ()=>{
        token = 'jaldfjaljfdsjdf';
        const res = await exec();
        expect(res.status).toBe(400);
    })
    it('should return 404 if token was valid.',async ()=>{
        const res = await exec();
        expect(res.status).toBe(404);
    });
});

describe('/api/auth',()=>{
    beforeEach(async ()=>{
        server = require('../../index');
    });
    afterEach(async ()=>{
        await server.close();
        await User.deleteMany({});
        await Shareholder.deleteMany({});
        await Token.deleteMany({});
    });

    describe('POST /api/auth/signOwner',()=>{
        let name;
        let phone;
        let token;
        const exec = function (){
            return request(server)
                .post('/api/auth/signOwner')
                .send({
                    name: name,
                    phone: phone
                })
                .set('x-auth-token',token);
        }

        beforeEach(async ()=>{
            name = 'mohyeddin';
            phone = '09374691756';
            const user = new User({name: name,phone: phone});
            await user.save();
            token = user.generateAuthToken();
        });
        it('should return 400 if invalid request body was send',async ()=>{
            name = '';
            phone = '';
            const res = await exec();
            expect(res.status).toBe(400);
        });
        it('should return 200 if request body was valid',async ()=>{
            const res = await exec();
            expect(res.status).toBe(200);
            expect(res.body).toHaveProperty('token');
        });
    });

    describe('POST /api/auth/loginOwner',()=>{
        let user;
        let token;
        const exec = function (){
            return request(server)
                .post('/api/auth/loginOwner')
                .set('x-auth-token',token)
                .send({
                    phone: user.phone
                });
        }
        beforeEach(async ()=>{
            user = new User({name: "mohyeddin",phone: "09374691756"});
            await user.save();
            token = user.generateAuthToken();
        });

        it('should return 404 if user not found',async ()=>{
            user.phone = "09331428554";
            const res = await exec();
            expect(res.status).toBe(404);
        });
        it('should return 200 and jwt token if user was found.',async ()=>{
            const res = await exec();
            expect(res.status).toBe(200);
            expect(res.body).toHaveProperty('token');
        })
    });

    describe('POST /api/auth/loginShareholder',()=>{
        let shareholder;
        let token;
        const exec = function (){
            return request(server)
                .post('/api/auth/loginShareholder')
                .set('x-auth-token',token)
                .send({
                    phone: shareholder.phone
                });
        }
        beforeEach(async ()=>{
            shareholder = new Shareholder({name: "mohyeddin",phone: "09374691756",share:0.5});
            await shareholder.save();
            token = shareholder.generateAuthToken();
        });

        it('should return 404 if user not found',async ()=>{
            shareholder.phone = "09331428554";
            const res = await exec();
            expect(res.status).toBe(404);
        });
        it('should return 200 and jwt token if user was found.',async ()=>{
            const res = await exec();
            expect(res.status).toBe(200);
            expect(res.body).toHaveProperty('token');
        })
    });

    describe('POST /api/auth/verifyOwner',()=>{
        let user;
        let user2;
        let jwtToken;
        let smsToken;
        const exec = function (st){
            return request(server)
                .post('/api/auth/verifyOwner')
                .set('x-auth-token',jwtToken)
                .send({
                    token: st?st:smsToken
                });
        }
        beforeEach(async ()=>{
            user = new User({
                name: "mohyeddin",
                phone: "09374681756"
            });
            user2 = new User({
                name: 'zey',
                phone: '09331452335'
            });
            await user.save();
            jwtToken = user.generateAuthToken();
            smsToken = "1234";
            await new Token({
                code: smsToken,
                phone: user.phone
            }).save();
            await new Token({
                code: "1111",
                phone: user2.phone
            }).save();
        });

        it('should return 404 if token was not found,',async ()=>{
            smsToken = "";
            const res = await exec();
            expect(res.status).toBe(404);
        });
        it('should return 401 if token and user save token was not same.',async ()=>{
            user.phone = "09331402335";
            jwtToken = user.generateAuthToken();
            const res = await exec();
            expect(res.status).toBe(401);
        });
        it('should return the user if user exists.',async ()=>{
            const res = await exec();
            expect(res.status).toBe(200);
            expect(res.body).toHaveProperty('name',user.name);
        });
        it('should return the new user if user not exist.',async ()=>{
            jwtToken = user2.generateAuthToken();
            const res = await exec("1111");
            expect(res.status).toBe(200);
            expect(res.body).toHaveProperty('phone',user2.phone);
        });

    });

    describe('POST /api/auth/verifyShareholder',()=>{
        let shareholder;
        let token;
        let smsToken;
        const exec = function(){
            return request(server)
                .post('/api/auth/verifyShareholder')
                .set('x-auth-token',token)
                .send({
                    token: smsToken
                });
        }
        beforeEach(async ()=>{
            shareholder = new Shareholder({
                name: 'mohyeddin',
                phone: '09345218751',
                share: 0.5
            });
            await shareholder.save();
            token = shareholder.generateAuthToken();
            smsToken = "1234";
            await new Token({
                code: smsToken,
                phone: shareholder.phone
            }).save();
        });

        it('should return 404 if sms token was not valid',async ()=>{
            smsToken = "";
            const res = await exec();
            expect(res.status).toBe(404);
        });
        it('should return 401 if sms token and user saved sms token was not the same',async ()=>{
            shareholder.phone = "09345475455";
            token = shareholder.generateAuthToken();
            const res = await exec();
            expect(res.status).toBe(401);
        });
        it('should return shareholder',async() =>{
            const res = await exec();
            expect(res.status).toBe(200);
            expect(res.body).toHaveProperty('share',shareholder.share);
        });
    });
});