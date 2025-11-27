// db.js
const { MongoClient, ServerApiVersion } = require('mongodb');

const uri = "mongodb+srv://dhanvarshinii:<db_password>@beauty-backend.qmgohpa.mongodb.net/?retryWrites=true&w=majority&appName=beauty-backend";

const client = new MongoClient(uri, {
  serverApi: {
    version: ServerApiVersion.v1,
    strict: true,
    deprecationErrors: true,
  }
});

async function connectDB() {
  try {
    await client.connect();
    console.log("Connected to MongoDB successfully");
    return client.db('test'); // replace with your actual DB name
  } catch (err) {
    console.error("MongoDB connection error:", err);
    throw err;
  }
}

module.exports = { connectDB };
