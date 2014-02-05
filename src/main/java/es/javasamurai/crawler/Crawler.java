package es.javasamurai.crawler;

import java.io.IOException;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Crawler {
	private static final String USO = "java -jar Crawler.jar -i <fileName> [-v | --verbose]\n";
	private static final String CABECERA = "Obtiene perfiles de linekdin a partir de los apellidos del fichero csv\n\n\n";
	private static final String PIE = "\njcorredera 2013 - www.java-samurai.es";
	
	private static Logger log = LoggerFactory.getLogger(Crawler.class);

	public static void main(String[] args) throws IOException {
		LinkedinEngine engine = null;

		CommandLineParser cmdParser = new BasicParser();

		Options options = new Options();

		OptionBuilder.withLongOpt("csv-input");
		OptionBuilder.withDescription("Indica el nombre de entrada del fichero de datos");
		OptionBuilder.isRequired();
		OptionBuilder.hasArg();
		OptionBuilder.withArgName("filename");
		
		options.addOption(OptionBuilder.create("i"));

		OptionBuilder.withLongOpt("verbose");
		OptionBuilder.withDescription("Muestra informacion relevante del proceso");
		
		options.addOption(OptionBuilder.create("v"));

		OptionBuilder.withLongOpt("help");
		
		options.addOption(OptionBuilder.create('h'));
		
		try {
			CommandLine cmdLine = cmdParser.parse(options, args);
			
			if (args.length > 0) {
				
				if ((cmdLine.hasOption("h")) || (cmdLine.hasOption("help"))) {
					printUsage(options);
					System.exit(-1);
				}
				
				if ((cmdLine.hasOption("i")) || (cmdLine.hasOption("csv-input"))) {
					String fileInput = cmdLine.getOptionValue("i");
					log.info("parametro de entradad -i: {}", fileInput);
					if ((cmdLine.hasOption("v")) || (cmdLine.hasOption("verbose"))) {
						log.info("activando trazas....");
						engine = new LinkedinEngine(fileInput, true);
					}
					else {
						log.info("desactivando trazas....");
						engine = new LinkedinEngine(fileInput, false);
					}
					log.info("iniciando proceso de recoleccion....");
					engine.start();
				}
				
			}
			
		}
		catch (MissingOptionException e) {
			if (args.length == 0) {
				log.error("Numero de argumentos de programa incorrectos - {}", Integer.valueOf(args.length));
			}
			printUsage(options);
		}
		catch (ParseException e) {
			log.error("Problema en el parseo de parametros: {}", e.getMessage());
			printUsage(options);
		}
		System.exit(0);
	}

	private static void printUsage(Options options) {
		HelpFormatter formatter = new HelpFormatter();
		formatter.setWidth(120);
		formatter.printHelp(USO,CABECERA, options,PIE, false);
	}
	
}
