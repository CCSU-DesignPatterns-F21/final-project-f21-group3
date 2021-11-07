/**
 * 
 */
package com.group3.racingbot;

import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;

import com.group3.racingbot.driverstate.DriverState;

/**
 * @author Nick Sabia
 *
 */
public class DriverStateCodec implements Codec<DriverState>{

	@Override
	public void encode(BsonWriter writer, DriverState value, EncoderContext encoderContext) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Class<DriverState> getEncoderClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DriverState decode(BsonReader reader, DecoderContext decoderContext) {
		// TODO Auto-generated method stub
		return null;
	}

}
