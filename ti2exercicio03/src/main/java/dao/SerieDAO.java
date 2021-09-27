package dao;

import model.Produto;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class SerieDAO {
	private List<Series> serie;
	private int maxId = 0;

	private File file;
	private FileOutputStream fos;
	private ObjectOutputStream outputFile;

	public int getMaxId() {
		return maxId;
	}

	public SerieDAO(String filename) throws IOException {
		file = new File(filename);
		serie = new ArrayList<Series>();
		if (file.exists()) {
			readFromFile();
		}

	}

	public void add(Series serie) {
		try {
			serie.add(serie);
			this.maxId = (serie.getId() > this.maxId) ? serie.getId() : this.maxId;
			this.saveToFile();
		} catch (Exception e) {
			System.out.println("ERRO ao gravar a série '" + serie.getNome() + "' no disco!");
		}
	}

	public Series get(int id) {
		for (Series serie : serie) {
			if (id == serie.getId()) {
				return serie;
			}
		}
		return null;
	}

	public void update(Serie s) {
		int index = serie.indexOf(s);
		if (index != -1) {
			serie.set(index, s);
			this.saveToFile();
		}
	}

	public void remove(Series s) {
		int index = serie.indexOf(s);
		if (index != -1) {
			serie.remove(index);
			this.saveToFile();
		}
	}

	public List<Series> getAll() {
		return serie;
	}

	private List<Series> readFromFile() {
		serie.clear();
		Series serie = null;
		try (FileInputStream fis = new FileInputStream(file);
				ObjectInputStream inputFile = new ObjectInputStream(fis)) {

			while (fis.available() > 0) {
				serie = (Series) inputFile.readObject();
				serie.add(serie);
				maxId = (serie.getId() > maxId) ? serie.getId() : maxId;
			}
		} catch (Exception e) {
			System.out.println("ERRO ao gravar série no disco!");
			e.printStackTrace();
		}
		return serie;
	}

	private void saveToFile() {
		try {
			fos = new FileOutputStream(file, false);
			outputFile = new ObjectOutputStream(fos);

			for (Series serie : serie) {
				outputFile.writeObject(serie);
			}
			outputFile.flush();
			this.close();
		} catch (Exception e) {
			System.out.println("ERRO ao gravar série no disco!");
			e.printStackTrace();
		}
	}

	private void close() throws IOException {
		outputFile.close();
		fos.close();
	}

	@Override
	protected void finalize() throws Throwable {
		try {
			this.saveToFile();
			this.close();
		} catch (Exception e) {
			System.out.println("ERRO ao salvar a base de dados no disco!");
			e.printStackTrace();
		}
	}
}