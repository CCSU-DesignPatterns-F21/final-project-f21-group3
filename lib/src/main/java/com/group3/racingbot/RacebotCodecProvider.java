/**
 * 
 */
package com.group3.racingbot;

import org.bson.Document;
import org.bson.codecs.BsonTypeClassMap;
import org.bson.codecs.Codec;
import org.bson.codecs.DocumentCodec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

/**
 * @author Nick Sabia
 *
 */
public class RacebotCodecProvider implements CodecProvider {

	private final BsonTypeClassMap bsonTypeClassMap;
	
	public RacebotCodecProvider(final BsonTypeClassMap bsonTypeClassMap) {
	    this.bsonTypeClassMap = bsonTypeClassMap;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> Codec<T> get(final Class<T> clazz, final CodecRegistry registry) {
	    if (clazz == Document.class) {
	        // construct DocumentCodec with a CodecRegistry and a BsonTypeClassMap
	        return (Codec<T>) new DocumentCodec(registry, bsonTypeClassMap);
	    }
	    /*else if (clazz == Order.class) {
	        return (Codec<T>) new OrderCodec(registry);
	    }
	    else if (clazz == Item.class) {
	        return (Codec<T>) new ItemCodec(registry);
	    }*/
	
	    return null;
	}
}
