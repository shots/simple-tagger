package com.sj.tagger.data.source;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.TreeSet;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FilebasedDataSource implements ArticleSource {

	private static Logger log = LogManager.getLogger(FilebasedDataSource.class);

	private Queue<URL> seedSet;

	public FilebasedDataSource(String seedFile) throws IOException {
		seedSet = new LinkedList<URL>();

		@SuppressWarnings("unchecked")
		List<String> urls = null;
		InputStream ipStream = null;

		log.debug("Seed File : " + seedFile);
		try {
			log.debug("Looking for seed file " + seedFile + " in classpath.");
			ipStream = getClass().getClassLoader()
					.getResourceAsStream(seedFile);
			if(ipStream != null) {
				urls = IOUtils.readLines(ipStream);
			} else {
				log.debug("Looking for seed file " + seedFile + "in the specified path");
				urls = FileUtils.readLines(new File(seedFile));				
			}
		} catch (IOException e1) {
			urls = FileUtils.readLines(new File(seedFile));		
		} finally {
			if (ipStream != null) {
				ipStream.close();
			}
		}

		if (CollectionUtils.isNotEmpty(urls)) {
			for (String u : urls) {
				try {
					URL url = new URL(u);
					seedSet.add(url);
				} catch (MalformedURLException e) {
					log.debug(u
							+ ": Bad URL, not adding to the seedSet, exception : "
							+ e.getMessage());
				}

			}
		}
	}

	@Override
	public URL getNext() {
		return seedSet.poll();
	}

	public static void main(String[] args) {

	}

}
