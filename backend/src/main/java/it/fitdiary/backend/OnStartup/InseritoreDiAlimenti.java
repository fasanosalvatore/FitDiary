package it.fitdiary.backend.OnStartup;

import it.fitdiary.backend.entity.Alimento;
import it.fitdiary.backend.gestionealimento.repository.AlimentoRepository;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class InseritoreDiAlimenti implements ApplicationListener<ApplicationReadyEvent> {

  private final AlimentoRepository alimentoRepository;

  private static final String DATASET_NAME = "datasetAlimenti.csv";

  @Autowired
  public InseritoreDiAlimenti(
      AlimentoRepository alimentoRepository) {
    this.alimentoRepository = alimentoRepository;
  }

  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {
    File file=new File(
        getClass().getClassLoader().getResource(DATASET_NAME).getFile());
    CSVFormat csvFormat = CSVFormat.Builder.create().setHeader(
                "id", "name", "calories", "total_fat",
                "protein", "carbohydrate")
            .setDelimiter(';').build();
    Iterable<CSVRecord> records;
    try {
      records =
          csvFormat.parse(new FileReader(file));
    } catch (IOException e) {
      throw new IllegalStateException("impossibile leggere il file " + DATASET_NAME );
    }

    List<Alimento> alimenti = new ArrayList<>();
    Iterator<CSVRecord> recordIterator  = records.iterator();
    recordIterator.next();
    int i = 0;
    while (recordIterator.hasNext() && i < 100) {
      CSVRecord record = recordIterator.next();
      Alimento alimento = new Alimento();
      System.out.println(record.get("id"));
      alimento.setId(Long.valueOf(record.get("id")));
      alimento.setNome(record.get("name"));
      alimento.setKcal(Float.valueOf(record.get("calories")));
      alimento.setProteine(Float.valueOf(record.get("protein")));
      alimento.setCarboidrati(Float.valueOf(record.get("carbohydrate")));
      alimento.setGrassi(Float.valueOf(record.get("total_fat")));
      alimento.setPathFoto("Alimenti/" + alimento.getId() + ".jpg");
      System.out.println(alimento);
      alimenti.add(alimento);
      i++;

    }
    alimentoRepository.saveAll(alimenti);


  }
}
