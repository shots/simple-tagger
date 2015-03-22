package com.sj.tagger;

import java.io.IOException;
import java.net.URL;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

import com.sj.tagger.data.sink.ArticleSink;
import com.sj.tagger.data.sink.FilebasedArticleSink;
import com.sj.tagger.data.source.ArticleSource;
import com.sj.tagger.data.source.FilebasedDataSource;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class PageTagger implements Tagger {

	private MaxentTagger textTagger;
	private HtmlTextParser parser = new TikaParser();

	public PageTagger() {
		textTagger = new MaxentTagger(
				"edu/stanford/nlp/models/pos-tagger/english-left3words/english-left3words-distsim.tagger");
	}

	public PageTagger setParser(HtmlTextParser parser) {
		this.parser = parser;
		return this;
	}
	
	// String and not article data since the definition should return String as per the assignment.
	public String tagText(String text) {
		String tagged = "";
		for (String s : text.split("\n")) {
			s = s.trim();
			if (!s.isEmpty()) {
				tagged += textTagger.tagString(s) + "\n";
			}
		}
		return tagged;
	}

	public String getText(URL url) {
		ArticleData data = parser.parse(url);
		return data == null ? "" : data.getText();
	}

	
	public static void main(String[] args) throws IOException, ParseException {
		Options options = new Options();
		Option ipOption = new Option("i", "input", true, "Input file");
		Option opOption = new Option("o", "output", true, "Output Directory");
		opOption.setRequired(true);
		
		options.addOption(ipOption);
		options.addOption(opOption);

		CommandLineParser cliParser = new PosixParser();
		CommandLine cmd = cliParser.parse(options, args);

		String seedFile = cmd.getOptionValue("i", "source.txt");
		String outputDir = cmd.getOptionValue("o");
		ArticleSource source = new FilebasedDataSource(seedFile);
		ArticleSink sink = new FilebasedArticleSink(outputDir);
		Tagger tagger = new PageTagger();
		// .setParser(new GooseParser());

		DataProcessingPipeline pipeline = new DataProcessingPipeline()
				.setSource(source).setSink(sink).setTagger(tagger);
		pipeline.process();
	}
}
