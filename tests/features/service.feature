# Read the Gherkin grammar here: https://cucumber.io/docs/reference

@service
Feature: the QingStor service
  The QingStor service should be available
  As a QingStor user
  I can list all my buckets

  Scenario: need to use QingStor service
    When initialize QingStor service
    Then the QingStor service is initialized

  # GET Service (List Buckets)
  Scenario: list all buckets
    When list buckets
    Then list buckets status code is 200
