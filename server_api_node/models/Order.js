const mongoose = require("mongoose");

const orderSchema = new mongoose.Schema(
    {
      notes: {
        type: String
      },
      listProducts: {
        type: Array,
        
      },
      total:{
        type: Number,
       
      }
    },
    { timestamps: true }
  );

module.exports = orderSchema

