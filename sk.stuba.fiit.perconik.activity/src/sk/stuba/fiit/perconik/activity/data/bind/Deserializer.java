package sk.stuba.fiit.perconik.activity.data.bind;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.UntypedObjectDeserializer;

import sk.stuba.fiit.perconik.activity.data.AnyStructuredData;

public final class Deserializer extends UntypedObjectDeserializer
{
	private static final long serialVersionUID = 0L;

	public Deserializer()
	{
	}

	@Override
	protected Object mapObject(JsonParser parser, DeserializationContext context) throws IOException, JsonProcessingException
	{
		return AnyStructuredData.of((Map<String, Object>) super.mapObject(parser, context));
	}
}
