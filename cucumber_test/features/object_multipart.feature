@object_multipart
Feature: the object multipart sub service

  # Initiate Multipart Upload
  Scenario: initiate the object multipart upload
    When initiate multipart upload with key "test_object_multipart"
    Then initiate multipart upload status code is 200

  # Upload Multipart
  Scenario: upload the first part
    When upload the first part
    Then upload the first part status code is 201

  Scenario: upload the second part
    When upload the second part
    Then upload the second part status code is 201

  Scenario: upload the third part
    When upload the third part
    Then upload the third part status code is 201

  # List Multipart
  Scenario: list the uploaded the multipart
    When list multipart
    Then list multipart status code is 200
    And list multipart object parts count is 3

  # Complete Multipart Upload
  Scenario: complete the multipart upload
    When complete multipart upload
    Then complete multipart upload status code is 201

  # Abort Multipart Upload
  Scenario: abort the multipart upload
    When abort multipart upload
    Then abort multipart upload status code is 400

  # DELETE Object
  Scenario: delete the multipart object
    When delete the multipart object
    Then delete the multipart object status code is 204
