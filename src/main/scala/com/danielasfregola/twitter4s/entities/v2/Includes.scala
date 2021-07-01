package com.danielasfregola.twitter4s.entities.v2

final case class Includes(tweets: Seq[Tweet],
                          users: Seq[User],
                          places: Seq[Place],
                          media: Seq[Media],
                          polls: Seq[Polls])
