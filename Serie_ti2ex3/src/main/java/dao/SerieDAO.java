package dao;

import model.Serie;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class SerieDAO {
	private List<Serie> series;
	private int maxId = 0;

	private File file;
	private FileOutputStream fos;
	private ObjectOutputStream outputFile;

	public int getMaxId() {
		return maxId;
	}

	public SerieDAO(String filename) throws IOException {
		file = new File(filename);
		series = new ArrayList<Serie>();
		if (file.exists()) {
			readFromFile();
		}

	}

	public void add(Serie serie) {
		try {
			series.add(serie);
			this.maxId = (serie.getId() > this.maxId) ? serie.getId() : this.maxId;
			this.saveToFile();
		} catch (Exception e) {
			System.out.println("ERRO ao gravar a série '" + serie.getNome() + "' no disco!");
		}
	}

	public Serie get(int id) {
		for (Serie serie : series) {
			if (id == serie.getId()) {
				return serie;
			}
		}
		return null;
	}

	public void update(Serie s) {
		int index = series.indexOf(s);
		if (index != -1) {
			series.set(index, s);
			this.saveToFile();
		}
	}

	public void remove(Serie s) {
		int index = series.indexOf(s);
		if (index != -1) {
			series.remove(index);
			this.saveToFile();
		}
	}

	public List<Serie> getAll() {
		return series;
	}

	private List<Serie> readFromFile() {
		series.clear();
		Serie serie = null;
		
		try (FileInputStream fis = new FileInputStream(file);
				ObjectInputStream inputFile = new ObjectInputStream(fis)) {

			while (fis.available() > 0) {
				serie = (Serie) inputFile.readObject();
				series.add(serie);
				maxId = (serie.getId() > maxId) ? serie.getId() : maxId;
			}
		} catch (Exception e) {
			System.out.println("ERRO ao gravar série no disco!");
			e.printStackTrace();
		}
		return series;
	}

	private void saveToFile() {
		try {
			fos = new FileOutputStream(file, false);
			outputFile = new ObjectOutputStream(fos);

			for (Serie serie : series) {
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