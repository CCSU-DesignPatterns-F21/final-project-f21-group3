/**
 * 
 */
package com.group3.racingbot;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

/**
 * @author Nick Sabia
 *
 */
public class DriverCodec implements Codec<Driver> {

	@Override
	public void encode(BsonWriter writer, Driver value, EncoderContext encoderContext) {
		// TODO Auto-generated method stub
		Document doc = new Document();

	    //doc.put("child", parent.getChild());

	    //documentCodec.encode(writer, doc, encoderContext);
	}

	@Override
	public Class<Driver> getEncoderClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Driver decode(BsonReader reader, DecoderContext decoderContext) {
		// TODO Auto-generated method stub
		return null;
	}

}
