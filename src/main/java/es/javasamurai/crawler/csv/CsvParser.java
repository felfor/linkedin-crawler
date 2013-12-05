package es.javasamurai.crawler.csv;

import au.com.bytecode.opencsv.CSVReader;
import com.google.common.collect.Lists;
import es.javasamurai.crawler.data.SurnameData;
import es.javasamurai.crawler.util.StringUtil;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CsvParser {
	private CSVReader reader;
	private boolean trace;
	private static Logger log = LoggerFactory.getLogger(CsvParser.class);

	public CsvParser(String nameCsvFile, boolean trace) throws FileNotFoundException {
		this.trace = trace;
		this.reader = new CSVReader(new FileReader(nameCsvFile), ';');
	}

	public List<SurnameData> loader() throws IOException {
		
		reader.readNext();
		
		if (this.trace) {
			log.info("Descartando lineas de cabeceras de columnas...");
		}
		List<SurnameData> surnameDataList = transformToData(this.reader.readAll());

		reader.close();
		if (this.trace) {
			log.info("Finalizado la carga del fichero de datos...");
		}
		
		return surnameDataList;
	}

	private List<SurnameData> transformToData(List<String[]> columnsList) {
		
		ArrayList<SurnameData> dataList = Lists.newArrayList();
		
		for (String[] columns : columnsList) {
			
			SurnameData data = new SurnameData();
			data.setId(Integer.parseInt(StringUtil.clean(columns[0])));
			data.setSurname(columns[1]);
			data.setFreqFirstSurname(Long.parseLong(StringUtil.clean(columns[2])));
			data.setFreqSecondSurname(Long.parseLong(StringUtil.clean(columns[3])));
			data.setFrequency(Long.parseLong(StringUtil.clean(columns[4])));
			
			if (this.trace) {
				log.info("Id: {} - Surname: {} - FreqFirst: {} - FreqSecond: {} - Frecuency: {}\n",
						new Object[] { Integer.valueOf(data.getId()), data.getSurname(), Long.valueOf(data.getFreqFirstSurname()),
								Long.valueOf(data.getFreqSecondSurname()), Long.valueOf(data.getFrequency()) });
			}
			
			dataList.add(data);
			
		}
		
		return dataList;
	}
	
}
