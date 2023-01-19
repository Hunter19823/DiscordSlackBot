package pie.ilikepiefoo2.bot.api;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.utils.data.DataArray;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import pie.ilikepiefoo2.bot.api.discord.ClassChannels;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.Properties;

public class Config {
	private static final Logger LOG = LogManager.getLogger();
	private static final String configLocation = "config.json";
	public final Document config;
	private static Config instance = null;

	public Config(Document document) {
		config = document;
		instance = this;
	}

	public Element getGuildConfig(Guild guild) {
		Element element = config.getElementById(guild.getId());
		if(element == null) {
			element = config.createElement("guild");
			element.setAttribute("id",guild.getId());
			config.getDocumentElement().appendChild(element);
		}
		return element;
	}

	private void init() {
		Element el = config.createElement("config");
		config.appendChild(el);
		Element classAbbreviations = config.createElement("class_abbreviations");
		el.appendChild(classAbbreviations);
		ClassChannels.addChoice("SER", "Software Engineering");
		ClassChannels.addChoice("CSE", "Computer Science and Engineering");
		ClassChannels.addChoice("HSE", "Human Systems Engineering");
		ClassChannels.addChoice("MAT", "Mathematics");
		ClassChannels.addChoice("HST", "History");
		ClassChannels.addChoice("PHY", "Physics");
		ClassChannels.addChoice("CHM", "Chemistry");
		ClassChannels.addChoice("BIO", "Biology");
		ClassChannels.addChoice("ENG", "English");
		ClassChannels.addChoice("GER", "German");
		ClassChannels.addChoice("EGR", "Engineering");
		ClassChannels.addChoice("FSE", "Fulton School of Engineering");
		ClassChannels.addChoice("SFS", "Sustainable Food Systems");
		ClassChannels.addChoice("ASU", "Arizona State University");
		ClassChannels.addChoice("SWU", "Social Work (Undergraduate)");
		ClassChannels.addChoice("SWG", "Social Work (Graduate)");
		ClassChannels.addChoice("AES", "Aerospace Engineering");
		ClassChannels.addChoice("IFT", "Information Technology");
		ClassChannels.addChoice("GIT", "Graphic Information Technology");
		ClassChannels.addChoice("CIS", "Computer Information Systems");
	}

	public static Config getInstance() {
		if(instance == null)
			return loadFromFile();
		return instance;
	}

	public static Config loadFromFile() {
		// Read from xml config file.
		DocumentBuilder builder;
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (Exception e) {
			LOG.error("Failed to create Document Builder!", e);
			throw new RuntimeException(e);
		}

		try {
			new Config(builder.parse(configLocation));
		} catch (Exception e) {
			LOG.error("Unable to parse config file, creating a new one in it's place.", e);
			new Config(builder.newDocument());
			instance.init();
			instance.saveToFile();
		}
		return instance;
	}

	public void saveToFile() {
		// Write to xml config file.
		Transformer transformer;
		try{
			transformer = TransformerFactory.newInstance().newTransformer();
			transformer.setOutputProperties(new Properties() {{
				put("indent", "yes");
				put("encoding", "UTF-8");
			}});
		} catch (Exception e) {
			LOG.error("Failed to create Transformer!", e);
			throw new RuntimeException(e);
		}
		try {
			transformer.transform(new DOMSource(config), new StreamResult(configLocation));
		} catch (Exception e) {
			LOG.error("Failed to save config file!", e);
			throw new RuntimeException(e);
		}
	}

}
