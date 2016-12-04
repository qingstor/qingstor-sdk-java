@bucket_acl
Feature: the bucket ACL feature

  # PUT Bucket ACL
  Scenario: set the bucket ACL
    When put bucket ACL:
    """
    {
      "acl": [
        {
          "grantee": {
            "type": "group",
            "name": "QS_ALL_USERS"
          },
          "permission": "FULL_CONTROL"
        }
      ]
    }
    """
    Then put bucket ACL status code is 200

  # GET Bucket ACL
  Scenario: get ACL of the bucket
    When get bucket ACL
    Then get bucket ACL status code is 200
    And get bucket ACL should have grantee name "QS_ALL_USERS"
