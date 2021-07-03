db.createCollection("users");
db.createCollection("roles");

db.roles.insertMany([
    {
        _id : ObjectId("60cb966e48837e8385f29d76"),
        role : "ADMIN",
        _class : "org.alex256.model.Role"
    },
    {
        _id : ObjectId("60cb967f48837e8385f29d77"),
        role : "USER",
        _class : "org.alex256.model.Role"
    }
]);
db.users.insertMany([
    {
        _id : ObjectId("60d4cee747339d8377e3f2fb"),
        username : "alex256",
        password : "$2y$12$5n/Qv9alkRKU7/4K1fvkLuvuXLKQKm1dg6vMtpMkh/Ic.sPl.cjxu",
        email : "email@email.com",
        _class : "org.alex256.application.model.User",
        roles : [
            DBRef('roles', ObjectId("60cb966e48837e8385f29d76")),
            DBRef('roles', ObjectId("60cb967f48837e8385f29d77"))
        ]
    },
    {
        _id : ObjectId("60d77830437de7579d084944"),
        username : "moderator",
        password : "$2a$10$hdVkWoWRv.cOxRmrDUPa5ufOP0MHSx8bnvwBB2KfYx9ZD6ftZHWre",
        email : "moderator@domain.com",
        _class : "org.alex256.application.model.User",
        roles : [
            DBRef("roles", ObjectId("60cb966e48837e8385f29d76")),
            DBRef("roles", ObjectId("60cb967f48837e8385f29d77"))
        ]
    }
]);