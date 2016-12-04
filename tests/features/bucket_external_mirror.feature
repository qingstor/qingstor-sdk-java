@bucket_external_mirror
Feature: the bucket external mirror feature

  # PUT Bucket External Mirror
  Scenario: set the bucket external mirror
    When put bucket external mirror:
    """
    {
      "source_site": "https://example.com/something/"
    }
    """
    Then put bucket external mirror status code is 200

  # GET Bucket External Mirror
  Scenario: get external mirror of the bucket
    When get bucket external mirror
    Then get bucket external mirror status code is 200
    And get bucket external mirror should have source_site "https://example.com/something/"

  # DELETE Bucket External Mirror
  Scenario: delete external mirror of the bucket
    When delete bucket external mirror
    Then delete bucket external mirror status code is 204
