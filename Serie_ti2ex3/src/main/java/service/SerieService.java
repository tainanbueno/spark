package service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import dao.SerieDAO;
import model.Serie;
import spark.Request;
import spark.Response;


public class SerieService {

	private SerieDAO serieDAO;

	public SerieService() {
		try {
			serieDAO = new SerieDAO("serie.dat");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public Object add(Request request, Response response) {
		String nome = request.queryParams("nome");
		String sinopse = request.queryParams("sinopse");
		String genero = request.queryParams("genero");
		String lancamento = request.queryParams("lancamento");
		int temporadas = Integer.parseInt(request.queryParams("temporadas"));
		int episodios = Integer.parseInt(request.queryParams("episodios"));

		int id = serieDAO.getMaxId() + 1;

		Serie serie = new Serie(id, nome, sinopse, genero, lancamento, temporadas, episodios);

		serieDAO.add(serie);

		response.status(201); // 201 Created
		return id;
	}

	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));
		
		Serie serie = (Serie) serieDAO.get(id);
		
		if (serie != null) {
    	    response.header("Content-Type", "application/xml");
    	    response.header("Content-Encoding", "UTF-8");

            return "<serie>\n" + 
            		"\t<id>" + serie.getId() + "</id>\n" +
            		"\t<nome>" + serie.getNome() + "</nome>\n" +
            		"\t<sinopse>" + serie.getSinopse() + "</sinopse>\n" +
            		"\t<genero>" + serie.getGenero() + "</genero>\n" +
            		"\t<lancamento>" + serie.getLancamento() + "</lancamento>\n" +
            		"\t<temporadas>" + serie.getTemporadas() + "</temporadas>\n" +
            		"\t<episodios>" + serie.getEpisodios() + "</episodios>\n" +
            		"</serie>\n";
        } else {
            response.status(404); // 404 Not found
            return "Série " + id + " não encontrado.";
        }

	}

	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        
		Serie serie = (Serie) serieDAO.get(id);

        if (serie != null) {
        	serie.setNome(request.queryParams("nome"));
        	serie.setSinopse(request.queryParams("sinopse"));
        	serie.setGenero(request.queryParams("genero"));
        	serie.setLancamento(request.queryParams("lancamento"));
        	serie.setTemporadas(Integer.parseInt(request.queryParams("temporadas")));
        	serie.setEpisodios(Integer.parseInt(request.queryParams("episodios")));

        	serieDAO.update(serie);
        	
            return id;
        } else {
            response.status(404); // 404 Not found
            return "Série não encontrado.";
        }

	}

	public Object remove(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));

        Serie serie = (Serie) serieDAO.get(id);

        if (serie != null) {

            serieDAO.remove(serie);

            response.status(200); // success
        	return id;
        } else {
            response.status(404); // 404 Not found
            return "Série não encontrado.";
        }
	}

	public Object getAll(Request request, Response response) {
		StringBuffer returnValue = new StringBuffer("<serie type=\"array\">");
		for (Serie serie : serieDAO.getAll()) {
			returnValue.append("\n<serie>\n" + 
					"\t<id>" + serie.getId() + "</id>\n" +
		    		"\t<nome>" + serie.getNome() + "</nome>\n" +
		    		"\t<sinopse>" + serie.getSinopse() + "</sinopse>\n" +
		    		"\t<genero>" + serie.getGenero() + "</genero>\n" +
		    		"\t<lancamento>" + serie.getLancamento() + "</lancamento>\n" +
		    		"\t<temporadas>" + serie.getTemporadas() + "</temporadas>\n" +
		    		"\t<episodios>" + serie.getEpisodios() + "</episodios>\n" +
		    		"</serie>\n");
		}
		returnValue.append("</serie>");
	    response.header("Content-Type", "application/xml");
	    response.header("Content-Encoding", "UTF-8");
		return returnValue.toString();
	}
}