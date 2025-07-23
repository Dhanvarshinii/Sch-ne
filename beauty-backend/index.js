const express = require('express');
const cors = require('cors');
const { MongoClient, ServerApiVersion } = require('mongodb');
const bcrypt = require('bcryptjs');
require('dotenv').config();

const app = express();
const port = process.env.PORT || 5001;

// Middleware
app.use(cors());
app.use(express.json());

// MongoDB connection URI
const uri = "mongodb+srv://dhanvarshinii:wVvkIQbPKrFaFGKe@beauty-backend.qmgohpa.mongodb.net/?retryWrites=true&w=majority&appName=beauty-backend";

const client = new MongoClient(uri, {
  serverApi: {
    version: ServerApiVersion.v1,
    strict: true,
    deprecationErrors: true,
  },
});

let usersCollection;

async function startServer() {
  try {
    // Connect once at startup
    await client.connect();
    console.log("✅ Connected to MongoDB");

    const db = client.db("test"); // Make sure this matches your actual DB name
    usersCollection = db.collection("users");

    // Root route
    app.get('/', (req, res) => {
      res.send('Server is running!');
    });

    // REGISTER endpoint
    app.post('/api/register', async (req, res) => {
      try {
        const { name, username, email, password, country, dob, gender } = req.body;

        if (!name || !username || !email || !password || !country || !dob || !gender) {
          return res.status(400).json({ success: false, message: "All fields are required." });
        }

        const existingUser = await usersCollection.findOne({ $or: [{ username }, { email }] });
        if (existingUser) {
          return res.status(409).json({ success: false, message: "Username or email already exists." });
        }

        const hashedPassword = await bcrypt.hash(password.trim(), 10);
        console.log("🔐 Hashed password being stored:", hashedPassword);

        const newUser = {
          name,
          username,
          email,
          password: hashedPassword,
          country,
          dob,
          gender
        };

        await usersCollection.insertOne(newUser);
        res.status(201).json({ success: true, message: "User registered successfully." });

      } catch (err) {
        console.error("❌ Registration error:", err);
        res.status(500).json({ success: false, message: "Registration failed." });
      }
    });

    // LOGIN endpoint
    app.post('/login', async (req, res) => {
      try {
        console.log("📥 Login request body:", req.body);
        const { username, password } = req.body;
    
        if (!username || !password) {
          return res.status(400).json({ success: false, message: 'Username and password required.' });
        }
    
        const user = await usersCollection.findOne({ username });
        if (!user) {
          console.log("❌ User not found");
          return res.status(401).json({ success: false, message: "Invalid username or password." });
        }
    
        console.log("🔒 Comparing password:", `"${password}"`);
        const isPasswordValid = await bcrypt.compare(password.trim(), user.password);
        console.log("✅ Password valid:", isPasswordValid);
    
        if (!isPasswordValid) {
          return res.status(401).json({ success: false, message: 'Invalid username or password.' });
        }
    
        // Respond with user details (omit password!)
        const userResponse = {
          displayName: user.name,
          gender: user.gender,
          username: user.username,
          email: user.email,
          country: user.country,
          dob: user.dob
        };
    
        res.json({ success: true, message: 'Login successful!', user: userResponse });
    
      } catch (error) {
        console.error("💥 Login error:", error);
        res.status(500).json({ success: false, message: 'Login failed due to server error.' });
      }
    });
    

    // Start server
    app.listen(port, () => {
      console.log(`🚀 Server is running on http://localhost:${port}`);
    });

  } catch (err) {
    console.error("❌ Could not connect to MongoDB:", err);
    process.exit(1);
  }
}

startServer();
