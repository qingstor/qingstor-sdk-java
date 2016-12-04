# QingStor SDK Test Scenarios

[![License](http://img.shields.io/badge/license-apache%20v2-blue.svg)](https://github.com/yunify/qingstor-sdk-test-scenarios/blob/master/LICENSE)
</span>

Test scenarios for QingStor SDKs.

## Introduction

We need to make sure that our QingStor SDKs are working properly, and to ensure their functional consistency. So we use scenario testing. Scenario testing is a software testing activity that uses scenarios: hypothetical stories to help the tester work through a complex problem or test system.

## Cucumber

[Cucumber](https://github.com/cucumber/cucumber) is a tool that supports Behaviour-Driven Development (BDD), and can be used for our test. It executes executable specifications written in [plain language](https://github.com/cucumber/cucumber/blob/master/docs/gherkin.md) and produces reports indicating whether the software behaves according to the specification or not.

Test scenarios are described in `.feature` files. Every `.feature` file conventionally consists of a single feature, and every feature may have several scenarios.

___Scenario Example:___

``` feature
# PUT Bucket
Scenario: create the bucket
  When put bucket
  Then put bucket status code is 201
```

Given these test scenarios, each SDK could implement a same test flow. Finally, running the test flow against the given features to check whether the SDK is right.

__Running Test in Ruby SDK:__

``` bash
$ cd test
$ cucumber --backtrace --require="./" ./features
...
38 scenarios (38 passed)
84 steps (84 passed)
0m7.934s
```

## Reference Documentations

- [Scenario Testing](https://en.wikipedia.org/wiki/Scenario_testing)
- [Cucumber](https://cucumber.io)

## Contributing

1. Fork it ( https://github.com/yunify/qingstor-sdk-test-scenarios/fork )
2. Create your feature branch (`git checkout -b new-feature`)
3. Commit your changes (`git commit -asm 'Add some feature'`)
4. Push to the branch (`git push origin new-feature`)
5. Create a new Pull Request

## LICENSE

[The Apache License (Version 2.0, January 2004)](http://www.apache.org/licenses/LICENSE-2.0.html).
