const nodemailer = require("nodemailer");
const path = require("path");
const fs = require("fs");

const transporter = nodemailer.createTransport({
  service: "gmail",
  auth: {
    user: "email_address",
    pass: "password", // use App-password instead of actual password
  },
});

const sendMail = (req, res) => {
  const { to, subject, text, attachments } = req.body;

  // Check for required fields
  if (!to || !subject || !text) {
    return res.status(400).json({
      message: "Error: Missing required fields (to, subject, or text)",
    });
  }

  const attachmentPaths = attachments
    ? attachments.map((att) => ({
        filename: path.basename(att.path),
        path: path.resolve(att.path),
      }))
    : [];

  attachmentPaths.forEach((attachment) => {
    console.log(`Checking file: ${attachment.path}`);
    fs.access(attachment.path, fs.constants.F_OK, (err) => {
      if (err) {
        console.log(`File not found: ${attachment.path}`);
      } else {
        console.log(`File found: ${attachment.path}`);
      }
    });
  });

  const mailOptions = {
    from: "jpdhinesh2002@gmail.com",
    to,
    subject,
    text,
    attachments: attachmentPaths,
  };

  transporter.sendMail(mailOptions, (error, info) => {
    if (error) {
      return res.status(500).json({ message: `Error: ${error.message}` });
    }
    res.status(200).json({ message: `Email sent: ${info.response}` });
  });
};

module.exports = { sendMail };
