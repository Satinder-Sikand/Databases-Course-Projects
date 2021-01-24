-- Name: Satinder Sikand
-- Student#: 215558661
-- 3421 Project 2

create TABLE Action (
    action VARCHAR(300) NOT NULL,
    constraint Action_pk
        PRIMARY KEY (action)
);

create TABLE Person (
    sin char(9) NOT NULL,
    name VARCHAR(150),
    address VARCHAR(300),
    phone VARCHAR(10) not null,

    constraint Person_pk
        PRIMARY KEY (sin),

    constraint  Person_sin_check
        check (char_length (sin)  = 9)
);

create TABLE Method (
    method VARCHAR(300) NOT NULL,

    constraint Method_pk
        PRIMARY KEY (method)
);

create TABLE Place (
    placename VARCHAR(150) NOT NULL,
    gps VARCHAR(30),
    address VARCHAR(400),
    description VARCHAR(400) not null,

    constraint Place_pk
        PRIMARY KEY (placename) 
);

create TABLE Time_Slot (
    time TIMESTAMP NOT NULL,

    constraint Time_Slot_pk
        PRIMARY KEY (time)
);

-- Sub class of Place
create TABLE Test_Centre (
    placename VARCHAR(300) NOT NULL,

    constraint tc_pk
        PRIMARY KEY (placename),
    constraint tc_also_in
        foreign key (placename) references Place(placename)
);

create TABLE Test_Type (
    testtype VARCHAR(30) NOT NULL,

    constraint Test_Type_pk
        PRIMARY KEY (testtype)
);

create TABLE Test (
    sin char(9) NOT NULL,
    time TIMESTAMP NOT NULL,
    testtype VARCHAR(30) NOT NULL,
    action VARCHAR(300),
    testcentre VARCHAR(300) NOT NULL,

    constraint Test_fk_person
        FOREIGN KEY (sin) REFERENCES Person (sin),
    constraint Test_fk_ts
        FOREIGN KEY (time) REFERENCES Time_Slot (time),
    constraint ofTestType
        FOREIGN KEY (testtype) REFERENCES Test_Type (testtype),
    constraint resultingAction
        FOREIGN KEY (action) REFERENCES Action (action),
    constraint inTestCentre
        FOREIGN KEY (testcentre) REFERENCES Test_Centre (placename)
);

create TABLE Recon (
    sin char(9) NOT NULL,
    method VARCHAR(300) NOT NULL,
    placename VARCHAR(150) NOT NULL,
    time TIMESTAMP NOT NULL,

    constraint Recon_pk
        PRIMARY KEY (sin, method, placename, time),
    constraint Recon_fk_person
        FOREIGN KEY (sin) REFERENCES Person(sin),
    constraint Recon_fk_method
        FOREIGN KEY (method) REFERENCES Method(Method),
    constraint Recon_fk_place
        FOREIGN KEY (placename) REFERENCES Place(placename),
    constraint Recon_fk_TimeSlot
        FOREIGN KEY (time) REFERENCES Time_Slot(time)
);

-- Done entities. Now relations
create TABLE Offer (
    testtype VARCHAR(300) NOT NULL,
    testcentre VARCHAR(30) NOT NULL,

    constraint Offer_pk
        PRIMARY KEY (testtype, testcentre),
    constraint Offer_fk_testtype
        FOREIGN KEY (testtype) REFERENCES Test_Type(testtype),
    constraint Offer_fk_TestCentre
        FOREIGN KEY (testcentre) REFERENCES Test_Centre(placename)
);

create Table Bubble (
    psin1 char(9) NOT NULL,
    psin2 char(9) NOT NULL,

    constraint Bubble_pk
        PRIMARY KEY (psin1, psin2),
    constraint Bubble_fk_person1
        FOREIGN KEY (psin1) REFERENCES Person(sin),
    constraint Bubble_fk_person2
        FOREIGN KEY (psin2) REFERENCES Person(sin)
);

