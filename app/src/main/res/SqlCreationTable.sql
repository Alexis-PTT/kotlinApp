DROP TABLE IF EXISTS person;
DROP TABLE IF EXISTS sport_session;
DROP TABLE IF EXISTS record_session;
DROP TABLE IF EXISTS super_set2;
DROP TABLE IF EXISTS super_set3;
DROP TABLE IF EXISTS super_set4;
DROP TABLE IF EXISTS exercice;
DROP TABLE IF EXISTS record_drop_set;
DROP TABLE IF EXISTS record_pyramid_set;
DROP TABLE IF EXISTS record_normal;

CREATE TABLE person (
                        user_id INTEGER PRIMARY KEY AUTOINCREMENT,
                        username VARCHAR(50) NOT NULL
);

CREATE TABLE sport_session (
                               session_id INTEGER PRIMARY KEY AUTOINCREMENT,
                               name_session TEXT NOT NULL,
                               user_id INTEGER NOT NULL,
                               FOREIGN KEY (user_id) REFERENCES person(user_id)

);

CREATE TABLE exercice (
                          id_ex INTEGER PRIMARY KEY AUTOINCREMENT,
                          id_session INTEGER NOT NULL,
                          name_ex TEXT NOT NULL UNIQUE,
                          exercice_type TEXT NOT NULL,
                          FOREIGN KEY (id_session) REFERENCES sport_session(session_id)
);

CREATE TABLE record_sport_session (
                                      session_id INTEGER NOT NULL,
                                      start_session DATETIME NOT NULL,
                                      end_session DATETIME NOT NULL,
                                      PRIMARY KEY (session_id, end_session),
                                      FOREIGN KEY (session_id) REFERENCES sport_session(session_id)

);

CREATE TABLE record_normal (
                               id_ex INTEGER,
                               creation_date DATETIME NOT NULL,
                               weight REAL NOT NULL,
                               repetition INTEGER NOT NULL,
                               set_count INTEGER NOT NULL,
                               PRIMARY KEY (id_ex, creation_date),
                               FOREIGN KEY (id_ex) REFERENCES exercice(id_ex)
);

CREATE TABLE record_drop_set (
                                 id_ex INTEGER,
                                 creation_date DATETIME NOT NULL,
                                 first_weight REAL NOT NULL,
                                 first_repetition INTEGER NOT NULL,
                                 second_weight REAL NOT NULL,
                                 second_repetition INTEGER NOT NULL,
                                 set_count INTEGER NOT NULL,
                                 PRIMARY KEY (id_ex, creation_date),
                                 FOREIGN KEY (id_ex) REFERENCES exercice(id_ex)
);

CREATE TABLE record_pyramid_set (
                            id_ex INTEGER,
                            creation_date DATETIME NOT NULL,
                            first_weight REAL NOT NULL,
                            first_repetition INTEGER NOT NULL,
                            last_weight REAL NOT NULL,
                            last_repetition INTEGER NOT NULL,
                            set_count INTEGER NOT NULL,
                            PRIMARY KEY (id_ex, creation_date),
                            FOREIGN KEY (id_ex) REFERENCES exercice(id_ex)
);

CREATE TABLE super_set3 (
                            id_superset INTEGER PRIMARY KEY AUTOINCREMENT,
                            id_ex1 INTEGER,
                            id_ex2 INTEGER,
                            id_ex3 INTEGER,
                            FOREIGN KEY (id_ex1) REFERENCES exercice(id_ex),
                            FOREIGN KEY (id_ex2) REFERENCES exercice(id_ex),
                            FOREIGN KEY (id_ex3) REFERENCES exercice(id_ex)
);

CREATE TABLE super_set2 (
                            id_superset INTEGER PRIMARY KEY AUTOINCREMENT,
                            id_ex1 INTEGER,
                            id_ex2 INTEGER,
                            FOREIGN KEY (id_ex1) REFERENCES exercice(id_ex),
                            FOREIGN KEY (id_ex2) REFERENCES exercice(id_ex)
);

INSERT INTO person VALUES (NULL,"test")