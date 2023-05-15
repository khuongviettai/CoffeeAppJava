const Product = require("../models/Product");
const asyncHandler = require("express-async-handler");

const productController = {

  // ADD PRODUCT
  addProduct: async (req, res) => {
    const Data = req.body;
    Product.create(Data, (err, data) => {
      if (err) {
        res.status(500).send(err);
      } else {
        res.status(200).send(data);
        // res.status(200).json(req.data);
      }
    });
  },

  // QUERY ALL PRODUCT
  viewAllProducts: asyncHandler(async (req, res) => {
    const products = await Product.find({});
    res.json(products);
  }),

  // QUERY PRODUCT BY ID
  viewSingleProduct: asyncHandler(async (req, res) => {
    const product = await Product.findById(req.params.id);
    if (product) {
      res.status(200).json(product);
    } else {
      res.status(404);
    }
  }),

  // UPDATE PRODUCT
  updateProduct: async (req, res) => {
    try {
      const { id } = req.params;
      const updateData = req.body;
      const updatedProduct = await Product.findByIdAndUpdate(
        id,
        updateData,
        { new: true }
      );
      res.status(200).json(updatedProduct);
    } catch (error) {
      res.status(500).json({ message: error.message });
    }
  },

  //  DELETE PRODUCT
  deleteProduct: asyncHandler(async (req, res) => {
    const productId = req.params.id;
    const product = await Product.findByIdAndRemove(productId);
    if (product) {
      res.status(200).json({
        message: "Product deleted successfully",
        deletedProduct: product,
      });
    } else {
      res.status(404).json({ message: "Product not found" });
    }
  }),
};

module.exports = productController;
