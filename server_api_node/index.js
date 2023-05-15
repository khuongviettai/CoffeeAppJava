const express = require("express");
const mongoose = require("mongoose");
const dotenv = require("dotenv");
const cors = require("cors");
const app = express();
const product = require("./routes/ProductRoutes");
const user = require('./routes/UserRoutes')

dotenv.config();
app.use(cors());
app.use(express.json());
app.use(express.urlencoded({ extended: true }));
// Connect to MongoDB
mongoose.set("strictQuery", false);
mongoose.connect(process.env.MONGODB_LINK, () => {
  console.log("Connect to mongodb...");
});

// get product
app.use("/api/product", product);
app.use("/api/user", user);




const PORT = process.env.PORT;
app.listen(PORT, () => {
  console.log(`Server is running on port ${PORT}...`);
});
