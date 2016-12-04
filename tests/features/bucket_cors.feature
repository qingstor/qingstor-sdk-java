@bucket_cors
Feature: the bucket CORS feature

  # PUT Bucket CORS
  Scenario: set the bucket CORS
    When put bucket CORS:
    """
    {
      "cors_rules": [
        {
          "allowed_origin": "http://*.qingcloud.com",
          "allowed_methods": [
            "PUT",
            "GET",
            "DELETE",
            "POST"
          ],
          "allowed_headers": [
            "X-QS-Date",
            "Content-Type",
            "Content-MD5",
            "Authorization"
          ],
          "max_age_seconds": 200,
          "expose_headers": [
             "X-QS-Date"
          ]
        },
        {
          "allowed_origin": "http://*.example.com",
          "allowed_methods": [
            "PUT",
            "GET",
            "DELETE",
            "POST"
          ],
          "allowed_headers": [
            "*"
          ],
          "max_age_seconds": 400
        }
      ]
    }
    """
    Then put bucket CORS status code is 200

  # GET Bucket CORS
  Scenario: get CORS of the bucket
    When get bucket CORS
    Then get bucket CORS status code is 200
    And get bucket CORS should have allowed origin "http://*.qingcloud.com"

  # DELETE Bucket CORS
  Scenario: delete CORS of the bucket
    When delete bucket CORS
    Then delete bucket CORS status code is 204
