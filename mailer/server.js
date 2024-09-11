const express = require("express");
const bodyParser = require("body-parser");
const { sendMail } = require("./mailHandler"); // Import correctly

const app = express();
app.use(bodyParser.json());
app.post("/mail", sendMail);

app.listen(3000, () => {
  console.log("Server is running on http://localhost:3000");
});
