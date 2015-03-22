package com.sj.tagger;

import java.net.URL;

import com.gravity.goose.Article;
import com.gravity.goose.Configuration;
import com.gravity.goose.Goose;

public class GooseParser implements HtmlTextParser {

	private Goose goose;

	public GooseParser() {
		goose = new Goose(new Configuration());
	}

	// Doesn't return entire body... but generally good at parsing out the main paragraph!
	@Override
	public ArticleData parse(URL url) {
		Article article = goose.extractContent(url.toString());
		return new ArticleData().setText(article.cleanedArticleText())
				.setTitle(article.title()).setUrl(url.toString());

	}

}
