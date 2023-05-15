const User = require("../models/User");
const asyncHandler = require("express-async-handler");

const userController = {
  addUser: asyncHandler(async (req, res) => {
    const Data = req.body;
    const user = await User.create(Data);
    res.status(200).json(user);
  }),
  viewAllUser: asyncHandler(async (req, res) => {
    const users = await User.find({});
    res.json(users);
  }),
  viewSingleUser: asyncHandler(async (req, res) => {
    const user = await User.findById(req.params.id);
    if (user) {
      res.status(200).json(user);
    } else {
      res.status(404).json({ message: "User not found" });
    }
  }),

  editSingleUser: asyncHandler(async (req, res) => {
    const user = await User.findById(req.params.id);
    if (user) {
      // Lấy dữ liệu cập nhật từ body request
      const userData = req.body;
      // Cập nhật thông tin người dùng
      user.name = userData.name || user.name;
      user.email = userData.email || user.email;

      const updatedUser = await user.save();
      res.status(200).json(updatedUser);   
    } else {
      res.status(404).json({ message: "User not found" });
    }
  }),
  userOrder: asyncHandler(async (req, res) => {
    const user = await User.findById(req.params.id);
    if (user) {
      const userData = req.body;
      user.orders.push(userData); // Sửa lại dòng này để đẩy dữ liệu mới vào mảng user.orders
      const updatedUser = await user.save();
      res.status(200).json(updatedUser);
    } else {
      res.status(404).json({ message: 'Không tìm thấy người dùng' });
    }
  })
  
};

module.exports = userController;
