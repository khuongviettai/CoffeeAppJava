const mongoose = require("mongoose");

const productSchema = new mongoose.Schema({
  name: {
    type: String,
    require: true,
  },
  price: {
    type: Number,
    require: true,
  },
  sale: {
    type: Number,
    default: 0,
  },
  quantity: {
    type: Number,
  },

  image: {
    type: String,
  },
  topping: {
    type: Array,
  },
  size: {
    type: Array,
    require: true,
  },
  description: {
    type: String,
    require: true,
  },
  category: {
    type: String,
    require: true,
  },
});

module.exports = mongoose.model("Product", productSchema);
