var express    = require('express');

const { stringify } = require('querystring');
const { response }  = require('express');
const sqlite3       = require('sqlite3').verbose();

//SEQUELIZE Utilizado pra fazer consultas em tabelas relacionais
const { Sequelize, Model, DataTypes } = require('sequelize');
const { json } = require('express/lib/response');
const sequelize = new Sequelize({
  dialect: 'sqlite',
  storage: './bahamas.db'
});

class Clients extends Model {}
Clients.init({
  client_id: { type: DataTypes.INTEGER, primaryKey: true},
  name:     DataTypes.STRING,
  email:     DataTypes.STRING,
}, { paranoid: true, underscored: true, timestamps: false, sequelize, modelName: 'clients' });

class Invoices extends Model {}
Invoices.init({
  invoice_id: { type: DataTypes.INTEGER, primaryKey: true},
  fiscal_id:   DataTypes.INTEGER,
}, { paranoid: true, underscored: true, timestamps: false, sequelize, modelName: 'invoices' });

class Invoice_Detail extends Model {}
Invoice_Detail.init({
  id: { type: DataTypes.INTEGER, primaryKey: true },
  invoice_id: {type: DataTypes.INTEGER, references: {model: 'invoices_detail', key: 'invoice_id'}
},
  client_id: {type: DataTypes.INTEGER, allowNull: false, references: {model: 'clients', key: 'client_id'}},
  //name: {type: DataTypes.STRING, allowNull: false, references: {model: 'clients', key: 'name'}},
  //email: {type: DataTypes.STRING, allowNull: false, references: {model: 'clients', key: 'email'}}
}, {tableName: 'invoice_detail', paranoid: true, freezeTableName: true, timestamps: false, sequelize, modelName: 'invoice_detail' });

Invoices.hasMany(Invoice_Detail, {
  as: 'invoice_detail',
  foreignKey: 'invoice_id'
});

Invoice_Detail.belongsTo(Invoices, {foreignKey: 'invoice_id', as: 'invoice'});

module.exports = {
  Clients, Invoices, Invoice_Detail
};

const run = async () => {};
sequelize.sync().then(() => {
  console.log("Drop and re-sync db.");
  run();
});

var db = new sqlite3.Database('./bahamas.db', sqlite3.OPEN_READWRITE, (err) => {
  if(err){
    console.error(err.message);
  }else{
    console.log('Connected to the chinook database.');
  }
});

var app = express();


/*************Clients**************/
app.get('/clients/', function(req, res) {
  
  try {
    
    db.all("SELECT * FROM Clients", function(err, data) {
      if(err){
        res.send(err);
        console.log(err);
      }else{
        res.status(200).json(data);
      }
    });
  } catch {
    console.log(error);
  }
});

app.get('/retrieve-bahamas-client/:id', function(req, res) {
  let id = req.params.id;
  try {
    Clients.findAll({
      where: {client_id: id}
    }).then((client) => {
      res.status(200).json(client);
    });
  } catch (error) {
    console.log(error);
  }
});

app.get('/findClientByName/:name', function(req, res) {
  let name = req.params.name;
  try {
    Clients.findAll({
      where: {name: name}
    }).then((client) => {
      res.status(200).json(client);
    });
  } catch (error) {
    console.log(error);
  }
});

app.post('/store-bahamas-client/:invoice_id', function(req, res) {

  let invoice_id = req.params.invoice_id;
  
  let fiscal_id    = parseInt(req.query.fiscal_id);
  let name         = req.query.name;
  let email        = req.query.email;
  let sqlData      = [invoice_id, fiscal_id];
  let sqlInvoice = `INSERT INTO invoices(invoice_id, fiscal_id)
  VALUES (?, ?)`;

  let sqlInvoiceDetail = `INSERT INTO invoice_detail(invoice_id, client_id)
  VALUES (?, ?)`;
  try {
    Clients.findOne({
      where: {name: name}
    }).then((client) => {
      if(null != client && undefined != client){
        Invoices.findOne({
          where: {invoice_id: invoice_id},
          include: [
            {model: Invoice_Detail, as: 'invoice_detail'}],
        }).then((invoices) => {
          if(null != invoices && undefined != invoices){
            let sqlDataDetail      = [invoice_id, client.dataValues.client_id];
                db.run(sqlInvoiceDetail, sqlDataDetail, function(err) {
                  if (err) {
                    console.error(err.message);
                    res.status(500);
                    res.send("Error recording invoice details.");
                  }else{  
                    Invoices.findOne({
                      where: {invoice_id: invoice_id},
                      include: [
                        {model: Invoice_Detail, as: 'invoice_detail'}],
                    }).then((invoices) => {
                      res.status(200).json(invoices);
                    });
                  }
                });
          }else{
            db.run(sqlInvoice, sqlData, function(err) {
              if (err) {
                console.error(err.message);
                res.status(500);
                res.send("Error recording invoice");
              }else{
                let sqlDataDetail      = [invoice_id, client.dataValues.client_id];
                db.run(sqlInvoiceDetail, sqlDataDetail, function(err) {
                  if (err) {
                    console.error(err.message);
                    res.status(500);
                    res.send("Error recording invoice details.");
                  }else{  
                    Invoices.findOne({
                      where: {invoice_id: invoice_id},
                      include: [
                        {model: Invoice_Detail, as: 'invoice_detail'}],
                    }).then((invoices) => {
                      res.status(200).json(invoices);
                    });
                  }
                });
              }
            });
          }
        });        
      }else{
        Invoices.findOne({
          where: {invoice_id: invoice_id},
          include: [
            {model: Invoice_Detail, as: 'invoice_detail'}],
        }).then((invoices) => {
          if(null != invoices && undefined != invoices ){
            Clients.create({ name: name, email: email });
            Clients.findOne({
              where: {name: name}
            }).then((client) => {
              let sqlDataDetail      = [invoice_id, client.dataValues.client_id];
              db.run(sqlInvoiceDetail, sqlDataDetail, function(err) {
                if (err) {
                  console.error(err.message);
                  res.status(500);
                  res.send("Error recording invoice details.");
                }else{              
                  Invoices.findAll({
                    where: {invoice_id: invoice_id},
                    include: [
                      {model: Invoice_Detail, as: 'invoice_detail'}],
                  }).then((invoices) => {
                    res.status(200).json(invoices);
                  });
                }
              });
            })
          }else{
            Clients.create({ name: name, email: email });
            db.run(sqlInvoice, sqlData, function(err) {
              if (err) {
                console.error(err.message);
                res.status(500);
                res.send("Error recording invoice");
              }else{
                Clients.findOne({
                  where: {name: name}
                }).then((client) => {
                  let sqlDataDetail      = [invoice_id, client.dataValues.client_id];
                  db.run(sqlInvoiceDetail, sqlDataDetail, function(err) {
                    if (err) {
                      console.error(err.message);
                      res.status(500);
                      res.send("Error recording invoice details.");
                    }else{              
                      Invoices.findAll({
                        where: {invoice_id: invoice_id},
                        include: [
                          {model: Invoice_Detail, as: 'invoice_detail'}],
                      }).then((invoices) => {
                        res.status(200).json(invoices);
                      });
                    }
                  });
                })
              }
            });
          }
        })
      }
    });
  } catch (error) {
    console.log(error);
  }

});

/*************Invoices**************/
app.get('/invoices', function(req, res) {

  try {
    Invoices.findAll({
      include: [
        {model: Invoice_Detail, as: 'invoice_detail'}],
    }).then((invoices) => {
      res.status(200).json(invoices);
    });
  } catch (error) {
    console.log(error);
    res.status(400).json(error);
  }

});

let port = process.env.PORT || 3000;
app.listen(port, function () {
  //TODO Pensar em uma frase mais apropriada
    return console.log("Started Bifrost. Heimdall is listenning on port " + port);
});