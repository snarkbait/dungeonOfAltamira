import javax.xml.stream.*;

public class ReadXML
{

	public  XMLInputFactory factory;
	public  XMLStreamReader reader;


	public ReadXML(String filename) throws XMLStreamException
	{
		filename += ".xml";
		factory = XMLInputFactory.newInstance();
		reader = factory.createXMLStreamReader(ClassLoader.getSystemResourceAsStream(filename));
	}

	public int getEventType()
	{
		int type;
		switch (reader.getEventType())
		{
			case XMLStreamReader.START_ELEMENT:
				type = 0;
				break;
			case XMLStreamReader.CHARACTERS:
				type = 1;
				break;
			case XMLStreamReader.END_ELEMENT:
				type = 2;
				break;
			default:
				type = -1;
		}
		return type;
	}

	public String getTag()
	{
		return reader.getLocalName();
	}

	public String getAttrName()
	{
		return reader.getAttributeLocalName(0);
	}

	public String getAttrValue()
	{
		return reader.getAttributeValue(0);
	}

	public String getAttrValue(int index)
	{
		return reader.getAttributeValue(index);
	}

	public String getText()
	{
		return reader.getText().trim();
	}
}

