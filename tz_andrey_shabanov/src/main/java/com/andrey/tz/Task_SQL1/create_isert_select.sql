-- Create HPPersonGeneric table
CREATE TABLE HPPersonGeneric (
                                 sysId INT PRIMARY KEY,
                                 personId VARCHAR(50),
                                 sysVersion INT,
                                 photoFkSysId INT,
                                 sysExtension VARCHAR(50),
                                 salutation VARCHAR(50),
                                 familyName VARCHAR(50),
                                 givenName VARCHAR(50),
                                 middleName VARCHAR(50),
                                 name VARCHAR(100),
                                 nameTranslationSysId INT,
                                 altFamilyName VARCHAR(50),
                                 altGivenName VARCHAR(50),
                                 altMiddleName VARCHAR(50),
                                 birthDate DATE,
                                 gender VARCHAR(10),
                                 maritalStatus VARCHAR(20),
                                 primaryLanguage VARCHAR(20),
                                 citizenship VARCHAR(20),
                                 residence VARCHAR(20),
                                 ethnicity VARCHAR(20),
                                 religion VARCHAR(20),
                                 sysTenant INT,
                                 createdBy INT,
                                 creationTime TIMESTAMP,
                                 sysChangeUser INT,
                                 sysChangeTime TIMESTAMP,
                                 sysParentId INT,
                                 sysDateTo DATE,
                                 sysDateFrom DATE
);

-- Create HPPersonDependant table
CREATE TABLE HPPersonDependant (
                                   sysId INT PRIMARY KEY,
                                   sysVersion INT,
                                   HPPersonGenericSysId INT,
                                   HPRelatedPersonSysId INT,
                                   attachmentFkSysId INT,
                                   sysExtension VARCHAR(50),
                                   contactRelationship VARCHAR(50),
                                   id INT,
                                   sysTenant INT,
                                   createdBy INT,
                                   creationTime TIMESTAMP,
                                   sysChangeUser INT,
                                   sysChangeTime TIMESTAMP,
                                   sysParentId INT,
                                   sysDateTo DATE,
                                   sysDateFrom DATE,
                                   FOREIGN KEY (HPPersonGenericSysId) REFERENCES HPPersonGeneric(sysId),
                                   FOREIGN KEY (HPRelatedPersonSysId) REFERENCES HPPersonGeneric(sysId)
);

INSERT INTO HPPersonGeneric ( sysId, personId, familyName, givenName, middleName, birthDate)
VALUES
    (1, 'test', 'Doe', 'John', 'Michael', '1990-01-01'),
    (2, 'relative1', 'Smith', 'Jane', 'Elizabeth', '1992-02-02'),
    (3, 'relative2', 'Johnson', 'Bob', 'Robert', '1995-03-03'),
    (4, 'relative3', 'Williams', 'Alice', 'Mary', '1998-04-04'),
    (5, 'relative4', 'Brown', 'Charlie', 'David', '2001-05-05');

-- Populate HPPersonDependant table with sample data
INSERT INTO HPPersonDependant (sysId, HPPersonGenericSysId, HPRelatedPersonSysId, contactRelationship)
VALUES
    (1, 1, 2, 'Spouse'),
    (2, 1, 3, 'Child'),
    (3, 1, 4, 'Sibling'),
    (4, 1, 5, 'Parent');

SELECT
    hp2.familyName,
    hp2.givenName,
    hp2.middleName,
    hp2.birthDate,
    hpd.contactRelationship
FROM
    HPPersonDependant hpd
        INNER JOIN HPPersonGeneric hp2 ON hpd.HPRelatedPersonSysId = hp2.sysId
WHERE
        hpd.HPPersonGenericSysId = (
        SELECT sysId FROM HPPersonGeneric WHERE personId = 'test');
