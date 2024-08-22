module.exports = {
  moduleDirectories: ["node_modules", "src/main/resources/static/js"],
  testEnvironment: "jest-environment-jsdom",
  transform: {
    "^.+\\.js$": "babel-jest",
  },
  reporters: [
    "default",
    [
      "jest-html-reporter",
      {
        pageTitle: "Test Report",
        outputPath: "test-report.html",
        includeFailureMsg: true,
        includeConsoleLog: false,
      },
    ],
  ],
  testMatch: [
    "<rootDir>/src/test/java/uk/ac/cf/spring/nhs/js/__tests__/**/*.test.js",
  ],
  testPathIgnorePatterns: ["/node_modules/", "/build/"],
};
