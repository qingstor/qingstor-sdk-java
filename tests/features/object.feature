@object
Feature: the object feature

  # PUT Object
  Scenario: create the object
    When put object with key "test_object"
    Then put object status code is 201

  # Copy Object
  Scenario: copy the object
    When copy object with key "test_object_copy"
    Then copy object status code is 201

  # Move Object
  Scenario: move the copy object
    When move object with key "test_object_move"
    Then move object status code is 201

  # GET Object
  Scenario: get the object
    When get object
    Then get object status code is 200
    And get object content length is 1048576

  # GET Object with Query Signature
  Scenario: get the object with query signature
    When get object with query signature
    Then get object with query signature content length is 1048576

  # Head Object
  Scenario: head the object
    When head object
    Then head object status code is 200

  # Options Object
  Scenario: options the object
    When options object with method "GET" and origin "qingcloud.com"
    Then options object status code is 200

  # DELETE Object
  Scenario: delete the object
    When delete object
    Then delete object status code is 204

  Scenario: delete the move object
    When delete the move object
    Then delete the move object status code is 204
