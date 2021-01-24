-- Name: Satinder Sikand
-- Student#: 215558661
-- 3421 Project 2

-- Populate with data
insert INTO Person (sin, name, address, phone) VALUES
    ('123456789', 'Satinder Sikand', '525 Highglen Ave', '4165553333'),
    ('111111111', 'David Lee', '12 Steeles West', '4163333333'),
    ('222222222', 'Parke Godfrey', '12 Steeles West', '4163333333'),
    ('333333333', 'Leon Star', '12 Steeles West', '4163333333'),
    ('444444444', 'Skitter Singh', '12 Steeles West', '4163333333');

insert INTO Method (method) VALUES
    ('contact-tracing phone app'),
    ('surveillance camera'),
    ('registry sign in'),
    ('registry sign out');

insert INTO Time_Slot (time) VALUES
    ('2020-12-20 12:00:00'),
    ('2020-12-20 12:15:00'),
    ('2020-12-20 12:30:00'),
    ('2020-12-20 12:45:00'),
    ('2020-12-20 13:00:00');

insert INTO Place (placename, gps, address, description) values
    ('Walmart', '41.40338, 2.17403', '111 Highglen Ave', 'Shopping centre'),
    ('Costco', '41.40338, 2.17403', 'Golden Dr Ave', 'Shopping centre'),
    ('Test Centre1', '41.41111, 2.11111', '123 High Ave', 'Testing centre'),
    ('Test Centre2', '41.42222, 2.17403', '222 Highglen Ave', 'Testing centre'),
    ('Test Centre3', '41.43333, 2.17403', '333 Highglen Ave', 'Testing centre'),
    ('Gas Station', '41.44444, 2.17403', '444 Highglen Ave', 'Shopping centre'),
    ('Random Store', '41.45555, 2.17403', '555 Highglen Ave', 'Shopping centre'),
    ('Best Buy', '41.46666, 2.17403', '666 Highglen Ave', 'Shopping centre');

insert INTO Test_Centre (placename) values
    ('Test Centre1'),
    ('Test Centre2'),
    ('Test Centre3');


insert INTO Recon (sin, method, placename, "time") VALUES
    ('123456789', 'contact-tracing phone app', 'Walmart', '2020-12-20 12:00:00'),
    ('123456789', 'registry sign in', 'Test Centre1', '2020-12-20 12:15:00'),
    ('123456789', 'registry sign out', 'Test Centre1', '2020-12-20 12:15:00'),
    ('123456789', 'surveillance camera', 'Costco', '2020-12-20 12:30:00'),
    ('123456789', 'contact-tracing phone app', 'Random Store', '2020-12-20 13:00:00'),

--     Person 2
    ('111111111', 'contact-tracing phone app', 'Walmart', '2020-12-20 12:00:00'),
    ('111111111', 'registry sign in', 'Test Centre2', '2020-12-20 12:45:00'),
    ('111111111', 'registry sign out', 'Test Centre2', '2020-12-20 12:45:00'),
    ('111111111', 'surveillance camera', 'Costco', '2020-12-20 12:30:00'),
    ('111111111', 'contact-tracing phone app', 'Random Store', '2020-12-20 13:00:00'),

-- Person 3
    ('333333333', 'contact-tracing phone app', 'Walmart', '2020-12-20 12:00:00'),
    ('333333333', 'registry sign in', 'Test Centre3', '2020-12-20 12:45:00'),
    ('333333333', 'registry sign out', 'Test Centre3', '2020-12-20 12:45:00'),
    ('333333333', 'surveillance camera', 'Costco', '2020-12-20 12:30:00'),
    ('333333333', 'contact-tracing phone app', 'Random Store', '2020-12-20 13:00:00'),

-- Person 4
    ('444444444', 'contact-tracing phone app', 'Walmart', '2020-12-20 12:00:00'),
    ('444444444', 'registry sign in', 'Test Centre2', '2020-12-20 12:45:00'),
    ('444444444', 'registry sign out', 'Test Centre2', '2020-12-20 12:45:00'),
    ('444444444', 'surveillance camera', 'Costco', '2020-12-20 12:30:00'),
    ('444444444', 'contact-tracing phone app', 'Random Store', '2020-12-20 13:00:00'),

-- Person 5
    ('222222222', 'contact-tracing phone app', 'Walmart', '2020-12-20 12:00:00'),
    ('222222222', 'registry sign in', 'Test Centre1', '2020-12-20 12:45:00'),
    ('222222222', 'registry sign out', 'Test Centre1', '2020-12-20 12:45:00'),
    ('222222222', 'surveillance camera', 'Costco', '2020-12-20 12:30:00'),
    ('222222222', 'contact-tracing phone app', 'Random Store', '2020-12-20 13:00:00');

insert into Test_Type (testtype) values
    ('molecular test'),
    ('rapid test'),
    ('antibody test');

insert into Action (action) values
    ('Quarantine'),
    ('Hospitalize'),
    ('Further Testing');

insert into Test (sin, "time", testtype, "action", testcentre) values
    ('123456789', '2020-12-20 12:15:00', 'molecular test', 'Quarantine', 'Test Centre1'),
    ('111111111', '2020-12-20 12:45:00', 'molecular test', 'Hospitalize', 'Test Centre2'),
    ('222222222', '2020-12-20 12:45:00', 'antibody test', 'Hospitalize', 'Test Centre1'),
    ('333333333', '2020-12-20 12:45:00', 'antibody test', 'Quarantine', 'Test Centre3'),
    ('444444444', '2020-12-20 12:45:00', 'rapid test', 'Further Testing', 'Test Centre2'),
    ('444444444', '2020-12-20 12:45:00', 'molecular test', 'Quarantine', 'Test Centre2');

-- insert into relationships
insert into Offer (testtype, testcentre) values
    ('molecular test', 'Test Centre1'),
    ('antibody test', 'Test Centre1'),
    ('molecular test', 'Test Centre2'),
    ('rapid test', 'Test Centre2'),
    ('molecular test', 'Test Centre3'),
    ('rapid test', 'Test Centre3'),
    ('antibody test', 'Test Centre3');

insert into Bubble (psin1, psin2)  values
    ('123456789', '111111111'),
    ('111111111', '123456789'),
    ('222222222', '444444444'),
    ('444444444', '222222222');



