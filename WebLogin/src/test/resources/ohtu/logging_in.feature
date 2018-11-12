Feature: As a registered user can log in with valid username/password-combination

Scenario: user can login with correct password
Given login is selected
When correct username "jukka" and password "akkuj" are given
Then user is logged in

Scenario: user can not login with incorrect password
Given login is selected
When correct username "jukka" and incorrect password "wrong" are given
Then user is not logged in and error message is given

Scenario: nonexistent user can not login to 
    Given login is selected
    When  incorrect username "nursultan" and password "testi" are given
    Then  user is not logged in and error message is given

Scenario: creation is successful with valid username and password
        Given command new user is selected
        When  a valid username "liisa" and password "salainen1" and matching password confirmation are entered
        Then  a new user is created

    Scenario: creation fails with too short username and valid password
        Given command new user is selected
        When  an invalid username "aa" and password "salainen1" are entered
        Then user is not created and error "username should have at least 3 characters" is reported   

    Scenario: creation fails with correct username and too short password
        Given command new user is selected
        When  a valid username "mikko" and an invalid password "testi" are entered 
        Then user is not created and error "password should have at least 8 characters" is reported   

    Scenario: creation fails when password and password confirmation do not match
        Given command new user is selected
        When  a valid username "mikko" and a valid password "salainen1" are entered and password confirmation does not match
        Then user is not created and error "password and password confirmation do not match" is reported   