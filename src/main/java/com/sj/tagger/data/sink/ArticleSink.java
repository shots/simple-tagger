package com.sj.tagger.data.sink;

import com.sj.tagger.ArticleData;

public interface ArticleSink {
	void flush(ArticleData taggedArticle);
}
