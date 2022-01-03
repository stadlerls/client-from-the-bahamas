var sqlite = require('sqlite3');

var db = new sqlite.Database("users.db");

db.run(`CREATE TABLE users(
  id INTEGER PRIMARY KEY,
  username TEXT NOT NULL,
  password TEXT NOT NULL
)`);

db.close();
