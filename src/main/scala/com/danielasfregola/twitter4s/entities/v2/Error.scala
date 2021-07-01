package com.danielasfregola.twitter4s.entities.v2

// https://developer.twitter.com/en/support/twitter-api/error-troubleshooting
final case class Error(detail: String,
                       field: Option[String],
                       parameter: String,
                       resource_id: String,
                       resource_type: String,
                       section: Option[String],
                       title: String,
                       `type`: Option[String],
                       value: Option[String])
