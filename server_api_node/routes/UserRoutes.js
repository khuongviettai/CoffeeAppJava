const userController = require("../controller/UserController");

const router = require("express").Router();

router.post("/add", userController.addUser);
router.get("/", userController.viewAllUser);
router.get("/:id", userController.viewSingleUser);
router.put("/put/:id", userController.editSingleUser);
router.post('/add/order/:id', userController.userOrder);

module.exports = router;
