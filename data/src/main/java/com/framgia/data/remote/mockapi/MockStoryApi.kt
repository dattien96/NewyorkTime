package com.framgia.data.remote.mockapi

import com.framgia.data.model.MultimediaEntity
import com.framgia.data.model.StoryEntity
import com.framgia.data.remote.api.StoryApi
import com.framgia.data.remote.response.StoryWrapperResponse
import io.reactivex.Single

class MockStoryApi : StoryApi {
    override fun getTopStories(type: String): Single<StoryWrapperResponse<StoryEntity>> {
        return Single.create {
            val response = StoryWrapperResponse<StoryEntity>("200", "copyright",
                    "21/10/2018", "4", listOf(
                    StoryEntity(
                            "U.S.",
                            "Politics",
                            "‘Transgender’ Could Be Defined Out of Existence Under Trump Administration",
                            "https://www.nytimes.com/2018/10/21/us/politics/transgender-trump-administration-sex-definition.html",
                            "By ERICA L. GREEN, KATIE BENNER and ROBERT PEAR",
                            "Article", " item type",
                            "2018-10-21T20:15:52-04:00",
                            "2018-10-21T05:00:05-04:00",
                            "2018-10-21T05:00:05-04:00",
                            listOf(
                                    MultimediaEntity("https://static01.nyt.com/images/2018/10/22/us/politics/21dc-trans1-print/21dc-trans1-thumbStandard.jpg", "People protesting the Trump administration’s policies toward gender and gay rights in New York last year."),
                                    MultimediaEntity("https://static01.nyt.com/images/2018/10/22/us/politics/21dc-trans1-print/21dc-trans1-thumbStandard.jpg", "People protesting the Trump administration’s policies toward gender and gay rights in New York last year."),
                                    MultimediaEntity("https://static01.nyt.com/images/2018/10/22/us/politics/21dc-trans1-print/21dc-trans1-thumbStandard.jpg", "People protesting the Trump administration’s policies toward gender and gay rights in New York last year.")
                            ),
                            "https://nyti.ms/2R9W1jB"
                    ),
                    StoryEntity(
                            "U.S.",
                            "Politics",
                            "‘Transgender’ Could Be Defined Out of Existence Under Trump Administration",
                            "https://www.nytimes.com/2018/10/21/us/politics/transgender-trump-administration-sex-definition.html",
                            "By ERICA L. GREEN, KATIE BENNER and ROBERT PEAR",
                            "Article", " item type",
                            "2018-10-21T20:15:52-04:00",
                            "2018-10-21T05:00:05-04:00",
                            "2018-10-21T05:00:05-04:00",
                            listOf(
                                    MultimediaEntity("https://static01.nyt.com/images/2018/10/22/us/politics/21dc-trans1-print/21dc-trans1-thumbStandard.jpg", "People protesting the Trump administration’s policies toward gender and gay rights in New York last year."),
                                    MultimediaEntity("https://static01.nyt.com/images/2018/10/22/us/politics/21dc-trans1-print/21dc-trans1-thumbStandard.jpg", "People protesting the Trump administration’s policies toward gender and gay rights in New York last year."),
                                    MultimediaEntity("https://static01.nyt.com/images/2018/10/22/us/politics/21dc-trans1-print/21dc-trans1-thumbStandard.jpg", "People protesting the Trump administration’s policies toward gender and gay rights in New York last year.")
                            ),
                            "https://nyti.ms/2R9W1jB"
                    ),
                    StoryEntity(
                            "U.S.",
                            "Politics",
                            "‘Transgender’ Could Be Defined Out of Existence Under Trump Administration",
                            "https://www.nytimes.com/2018/10/21/us/politics/transgender-trump-administration-sex-definition.html",
                            "By ERICA L. GREEN, KATIE BENNER and ROBERT PEAR",
                            "Article", " item type",
                            "2018-10-21T20:15:52-04:00",
                            "2018-10-21T05:00:05-04:00",
                            "2018-10-21T05:00:05-04:00",
                            listOf(
                                    MultimediaEntity("https://static01.nyt.com/images/2018/10/22/us/politics/21dc-trans1-print/21dc-trans1-thumbStandard.jpg", "People protesting the Trump administration’s policies toward gender and gay rights in New York last year."),
                                    MultimediaEntity("https://static01.nyt.com/images/2018/10/22/us/politics/21dc-trans1-print/21dc-trans1-thumbStandard.jpg", "People protesting the Trump administration’s policies toward gender and gay rights in New York last year."),
                                    MultimediaEntity("https://static01.nyt.com/images/2018/10/22/us/politics/21dc-trans1-print/21dc-trans1-thumbStandard.jpg", "People protesting the Trump administration’s policies toward gender and gay rights in New York last year.")
                            ),
                            "https://nyti.ms/2R9W1jB"
                    )
            ))
            it.onSuccess(response)
        }

    }

}