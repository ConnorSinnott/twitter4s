package com.danielasfregola.twitter4s.http.clients.rest.v2.tweets

import akka.http.scaladsl.model.HttpMethods
import com.danielasfregola.twitter4s.entities.RatedData
import com.danielasfregola.twitter4s.entities.v2.enums.Expansions
import com.danielasfregola.twitter4s.entities.v2.enums.fields.{MediaFields, PlaceFields, PollFields, TweetFields, UserFields}
import com.danielasfregola.twitter4s.entities.v2.responses.TweetsResponse
import com.danielasfregola.twitter4s.helpers.ClientSpec

class TwitterV2TweetLookupClientSpec extends ClientSpec {

  class TwitterV2TweetLookupClientSpecContext extends RestClientSpecContext with TwitterV2TweetLookupClient

  "Twitter Tweet Lookup Client" should {

    "lookup tweets" in new TwitterV2TweetLookupClientSpecContext {
      val result: RatedData[TweetsResponse] = when(lookupTweets(Seq("123", "456")))
        .expectRequest { request =>
          request.method === HttpMethods.GET
          request.uri.endpoint === "https://api.twitter.com/2/tweets"
          request.uri.rawQueryString === Some("ids=123%2C456")
        }
        .respondWithRated("/twitter/rest/v2/tweets/tweetlookup/tweets.json")
        .await
      result.rate_limit === rateLimit
      result.data === loadJsonAs[TweetsResponse]("/twitter/rest/v2/tweets/tweetlookup/tweets.json") // TODO setup fixtures
    }

    "lookup tweets with expansions" in new TwitterV2TweetLookupClientSpecContext {
      val tweetIds = Seq("123","456")
      val expansions = Seq(
        Expansions.`Attachments.PollIds`,
        Expansions.`Attachments.MediaKeys`,
        Expansions.AuthorId,
        Expansions.`Entities.Mentions.Username`,
        Expansions.`Geo.PlaceId`,
        Expansions.InReplyToUser,
        Expansions.`ReferencedTweets.Id`,
        Expansions.`ReferencedTweets.Id.AuthorId`
      )

      val result: RatedData[TweetsResponse] = when(lookupTweets(
        ids = tweetIds,
        expansions = expansions
      ))
        .expectRequest { request =>
          request.method === HttpMethods.GET
          request.uri.endpoint === "https://api.twitter.com/2/tweets"
          request.uri.rawQueryString === Some(
            "expansions=attachments.poll_ids%2Cattachments.media_keys%2Cauthor_id%2Centities.mentions.username%2Cgeo.place_id%2Cin_reply_to_user_id%2Creferenced_tweets.id%2Creferenced_tweets.id.author_id&ids=123%2C456"
          )
        }
        .respondWithRated("/twitter/rest/v2/tweets/tweetlookup/tweets.json")
        .await
      result.rate_limit === rateLimit
      result.data === loadJsonAs[TweetsResponse]("/twitter/rest/v2/tweets/tweetlookup/tweets.json") // TODO setup fixtures
    }

    "lookup tweets with media fields" in new TwitterV2TweetLookupClientSpecContext {
      val tweetIds = Seq("123","456")
      val mediaFields = Seq(
        MediaFields.DurationMs,
        MediaFields.Height,
        MediaFields.MediaKey,
        MediaFields.PreviewImageUrl,
        MediaFields.Type,
        MediaFields.Url,
        MediaFields.Width,
        MediaFields.PublicMetrics,
        MediaFields.NonPublicMetrics,
        MediaFields.OrganicMetrics,
        MediaFields.PromotedMetrics
      )

      val result: RatedData[TweetsResponse] = when(lookupTweets(
        ids = tweetIds,
        mediaFields = mediaFields
      ))
        .expectRequest { request =>
          request.method === HttpMethods.GET
          request.uri.endpoint === "https://api.twitter.com/2/tweets"
          request.uri.rawQueryString === Some(
            "ids=123%2C456&media%2Efields=duration_ms%2Cheight%2Cmedia_key%2Cpreview_image_url%2Ctype%2Curl%2Cwidth%2Cpublic_metrics%2Cnon_public_metrics%2Corganic_metrics%2Cpromoted_metrics"
          )
        }
        .respondWithRated("/twitter/rest/v2/tweets/tweetlookup/tweets.json")
        .await
      result.rate_limit === rateLimit
      result.data === loadJsonAs[TweetsResponse]("/twitter/rest/v2/tweets/tweetlookup/tweets.json") // TODO setup fixtures
    }

    "lookup tweets with place fields" in new TwitterV2TweetLookupClientSpecContext {
      val tweetIds = Seq("123","456")
      val placeFields = Seq(
        PlaceFields.ContainedWidth,
        PlaceFields.Country,
        PlaceFields.CountryCode,
        PlaceFields.FullName,
        PlaceFields.Geo,
        PlaceFields.Id,
        PlaceFields.Name,
        PlaceFields.PlaceType
      )

      val result: RatedData[TweetsResponse] = when(lookupTweets(
        ids = tweetIds,
        placeFields = placeFields
      ))
        .expectRequest { request =>
          request.method === HttpMethods.GET
          request.uri.endpoint === "https://api.twitter.com/2/tweets"
          request.uri.rawQueryString === Some(
            "ids=123%2C456&place%2Efields=contained_within%2Ccountry%2Ccountry_code%2Cfull_name%2Cgeo%2Cid%2Cname%2Cplace_type"
          )
        }
        .respondWithRated("/twitter/rest/v2/tweets/tweetlookup/tweets.json")
        .await
      result.rate_limit === rateLimit
      result.data === loadJsonAs[TweetsResponse]("/twitter/rest/v2/tweets/tweetlookup/tweets.json") // TODO setup fixtures
    }

    "lookup tweets with poll fields" in new TwitterV2TweetLookupClientSpecContext {
      val tweetIds = Seq("123","456")
      val pollFields = Seq(
        PollFields.DurationMinutes,
        PollFields.EndDatetime,
        PollFields.Id,
        PollFields.Options,
        PollFields.VotingStatus,
      )

      val result: RatedData[TweetsResponse] = when(lookupTweets(
        ids = tweetIds,
        pollFields = pollFields
      ))
        .expectRequest { request =>
          request.method === HttpMethods.GET
          request.uri.endpoint === "https://api.twitter.com/2/tweets"
          request.uri.rawQueryString === Some(
            "ids=123%2C456&poll%2Efields=duration_minutes%2Cend_datetime%2Cid%2Coptions%2Cvoting_status"
          )
        }
        .respondWithRated("/twitter/rest/v2/tweets/tweetlookup/tweets.json")
        .await
      result.rate_limit === rateLimit
      result.data === loadJsonAs[TweetsResponse]("/twitter/rest/v2/tweets/tweetlookup/tweets.json") // TODO setup fixtures
    }

    "lookup tweets with tweet fields" in new TwitterV2TweetLookupClientSpecContext {
      val tweetIds = Seq("123","456")
      val tweetFields = Seq(
        TweetFields.Attachments,
        TweetFields.AuthorId,
        TweetFields.ContextAnnotations,
        TweetFields.ConversationId,
        TweetFields.CreatedAt,
        TweetFields.Entities,
        TweetFields.Geo,
        TweetFields.Id,
        TweetFields.InReplyToUserId,
        TweetFields.Lang,
        TweetFields.NonPublicMetrics,
        TweetFields.PublicMetrics,
        TweetFields.OrganicMetrics,
        TweetFields.PromotedMetrics,
        TweetFields.PossiblySensitive,
        TweetFields.ReferencedTweets,
        TweetFields.ReplySettings,
        TweetFields.Source,
        TweetFields.Text,
        TweetFields.Withheld
      )

      val result: RatedData[TweetsResponse] = when(lookupTweets(
        ids = tweetIds,
        tweetFields = tweetFields
      ))
        .expectRequest { request =>
          request.method === HttpMethods.GET
          request.uri.endpoint === "https://api.twitter.com/2/tweets"
          request.uri.rawQueryString === Some(
            "ids=123%2C456&tweet%2Efields=attachments%2Cauthor_id%2Ccontext_annotations%2Cconversation_id%2Ccreated_at%2Centities%2Cgeo%2Cid%2Cin_reply_to_user_id%2Clang%2Cnon_public_metrics%2Cpublic_metrics%2Corganic_metrics%2Cpromoted_metrics%2Cpossibly_sensitive%2Creferenced_tweets%2Creply_settings%2Csource%2Ctext%2Cwithheld"
          )
        }
        .respondWithRated("/twitter/rest/v2/tweets/tweetlookup/tweets.json")
        .await
      result.rate_limit === rateLimit
      result.data === loadJsonAs[TweetsResponse]("/twitter/rest/v2/tweets/tweetlookup/tweets.json") // TODO setup fixtures
    }

    "lookup tweets with user fields" in new TwitterV2TweetLookupClientSpecContext {
      val tweetIds = Seq("123","456")
      val userFields = Seq(
        UserFields.CreatedAt,
        UserFields.Description,
        UserFields.Entities,
        UserFields.Id,
        UserFields.Location,
        UserFields.Name,
        UserFields.PinnedTweetId,
        UserFields.ProfileImageUrl,
        UserFields.Protected,
        UserFields.PublicMetrics,
        UserFields.Url,
        UserFields.Username,
        UserFields.Verified,
        UserFields.Withheld
      )

      val result: RatedData[TweetsResponse] = when(lookupTweets(
        ids = tweetIds,
        userFields = userFields
      ))
        .expectRequest { request =>
          request.method === HttpMethods.GET
          request.uri.endpoint === "https://api.twitter.com/2/tweets"
          request.uri.rawQueryString === Some(
            "ids=123%2C456&user%2Efields=created_at%2Cdescription%2Centities%2Cid%2Clocation%2Cname%2Cpinned_tweet_id%2Cprofile_image_url%2Cprotected%2Cpublic_metrics%2Curl%2Cusername%2Cverified%2Cwithheld"
          )
        }
        .respondWithRated("/twitter/rest/v2/tweets/tweetlookup/tweets.json")
        .await
      result.rate_limit === rateLimit
      result.data === loadJsonAs[TweetsResponse]("/twitter/rest/v2/tweets/tweetlookup/tweets.json") // TODO setup fixtures
    }

  }

}
