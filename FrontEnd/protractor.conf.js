// Protractor configuration file, see link for more information
// https://github.com/angular/protractor/blob/master/lib/config.ts
const failFast = require('protractor-fail-fast');
const { SpecReporter } = require('jasmine-spec-reporter');

exports.config = {
  allScriptsTimeout: 11000,
  plugins: [
    failFast.init(),
  ],
  multiCapabilities: [{
    'browserName': 'chrome',
    specs: './e2e/specs/logon-no-permission.e2e-spec.ts'
  }, {
    'browserName': 'chrome',
    specs: './e2e/specs/logon-with-permission.e2e-spec.ts'
  }],
  directConnect: true,
  baseUrl: 'http://localhost:4200/',
  framework: 'jasmine',
  jasmineNodeOpts: {
    showColors: true,
    defaultTimeoutInterval: 30000,
    print: function () {
    }
  },
  rootElement: 'app-root',
  onPrepare() {
    require('ts-node').register({
      project: 'e2e/tsconfig.e2e.json'
    });
    jasmine.getEnv().addReporter(new SpecReporter({spec: {displayStacktrace: true}}));
  },

  afterLaunch: function() {
    failFast.clean(); // Removes the fail file once all test runners have completed.
  }
};
