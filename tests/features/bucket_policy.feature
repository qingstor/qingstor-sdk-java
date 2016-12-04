@bucket_policy
Feature: the bucket policy feature

  # PUT Bucket policy
  Scenario: set the bucket policy
    # Notice: Please set statement resource manually
    When put bucket policy:
    """
    {
      "statement": [
        {
          "id": "allow certain site to get objects",
          "user": [
            "*"
          ],
          "action": [
            "get_object"
          ],
          "effect": "allow",
          "resource": [],
          "condition": {
            "string_like": {
              "Referer": [
                "*.example1.com",
                "*.example2.com"
              ]
            }
          }
        }
      ]
    }
    """
    Then put bucket policy status code is 200

  # GET Bucket policy
  Scenario: get policy of the bucket
    When get bucket policy
    Then get bucket policy status code is 200
    And get bucket policy should have Referer "*.example1.com"

  # DELETE Bucket policy
  Scenario: delete policy of the bucket
    When delete bucket policy
    Then delete bucket policy status code is 204
