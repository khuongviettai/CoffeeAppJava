const mongoose = require("mongoose");
const orderSchema = require("./Order");


const userSchema = new mongoose.Schema({
  name: {
    type: String,
    required: true
  },
  phone: {
    type: String,
    unique: true,
    required: true
  },
  password: {
    type: String,
    required: true
  },
  admin: {
    type: Boolean,
    default: false
  },
  address: {
    type: String
  },
  orders: [
   orderSchema
  ]
});

module.exports = mongoose.model("User", userSchema);
