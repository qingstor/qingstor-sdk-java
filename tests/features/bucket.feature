@bucket
Feature: the bucket feature

  Scenario: need to use bucket
    When initialize the bucket
    Then the bucket is initialized

  # PUT Bucket
  Scenario: create the bucket
    When put bucket
    Then put bucket status code is 201

  Scenario: create the same bucket again
    When put same bucket again
    Then put same bucket again status code is 409

  # GET Bucket (List Objects)
  Scenario: list objects in the bucket
    When list objects
    Then list objects status code is 200
    And list objects keys count is 0

  # Head Bucket
  Scenario: head the bucket
    When head bucket
    Then head bucket status code is 200

  # Delete Multiple Objects
  Scenario: delete multiple objects in the bucket
    When delete multiple objects:
    """
    {
      "quiet": false,
      "objects": [
        {
          "key": "object_0"
        },
        {
          "key": "object_1"
        },
        {
          "key": "object_2"
        }
      ]
    }
    """
    Then delete multiple objects code is 200

  # GET Bucket Statistics
  Scenario: get statistics of the bucket
    When get bucket statistics
    Then get bucket statistics status code is 200
    And get bucket statistics status is "active"

    # DELETE Bucket
  Scenario: delete the bucket
    When delete bucket
    Then delete bucket status code is 204
