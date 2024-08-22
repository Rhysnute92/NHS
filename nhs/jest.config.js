module.exports = {
  moduleDirectories: ["node_modules", "src/main/resources/static/js"],
  testEnvironment: "jest-environment-jsdom",
  reporters: [
    "default",
    [
      "jest-html-reporter",
      {
        pageTitle: "Test Report",
        outputPath: "test-report.html",
        includeFailureMsg: true,
        includeConsoleLog: true,
      },
    ],
  ],
};
