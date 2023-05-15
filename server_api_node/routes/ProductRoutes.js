const productController = require("../controller/ProductController");

const router = require("express").Router();

// QUERY PRODUCT FROM DATA
router.get("/", productController.viewAllProducts);
router.get("/:id", productController.viewSingleProduct);

// ADD PRODUCT
router.post("/add", productController.addProduct);

// UPDATE PRODUCT
router.put("/:id", productController.updateProduct);

// DELETE PRODUCT
router.delete("/:id", productController.deleteProduct);

module.exports = router;
